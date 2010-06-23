package edu.stanford.smi.protege.storage.database.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.stanford.smi.protege.util.ApplicationProperties;
import edu.stanford.smi.protege.util.Log;
import edu.stanford.smi.protege.util.SystemUtilities;

/*
 * for now I am only worrying about race conditions between the caller and the cleanup thread.
 */


public class ConnectionPool {
    private static Logger log = Log.getLogger(ConnectionPool.class);
    private static Map<DatabaseParam, ConnectionPool> connectionPoolMap = new HashMap<DatabaseParam, ConnectionPool>();
    
    public static final String PROPERTY_REFRESH_CONNECTIONS_TIME="Database.refresh.connections.interval";
    public static final String PROPERTY_MAX_DB_CONNECTIONS = "Database.max.connections";
    
    private static long connectionRefreshInterval;
    static {
        int minutes = ApplicationProperties.getIntegerProperty(ConnectionPool.PROPERTY_REFRESH_CONNECTIONS_TIME, 5);
        connectionRefreshInterval = minutes * 60 * 1000;
    }
    private static int maxOpenConnections = ApplicationProperties.getIntegerProperty(ConnectionPool.PROPERTY_MAX_DB_CONNECTIONS, 3);
    
    private String driver;
    private String url;
    private String username;
    private String password;
    private int referenceCount = 0;
    
    private Set<Connection> idleConnections = new HashSet<Connection>();
    private Map<Connection, ConnectionInfo> connectionInfoMap = new HashMap<Connection, ConnectionInfo>();
    
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, new ThreadFactory() {
        public Thread newThread(Runnable r) {
            Thread th = new Thread(r, "Protege Connection Reaper");
            th.setDaemon(true);
            return th;
        }
    });
    
    public static ConnectionPool getConnectionPool(String driver, String url, String username, String password) {
        ConnectionPool pool;
        synchronized (connectionPoolMap) {
            DatabaseParam dp = new DatabaseParam(driver, url, username, password);
            pool = connectionPoolMap.get(dp);
            if (pool == null) {
                pool = new ConnectionPool(driver, url, username, password);
                connectionPoolMap.put(dp, pool);
            }
        }
        pool.reference();
        return pool;
    }

    @SuppressWarnings("unchecked")
    private ConnectionPool(String driver, String url, String username, String password) {
        Class clas = SystemUtilities.forName(driver);
        if (clas == null) {
            throw new RuntimeException("class not found: " + driver);
        }
        this.driver   = driver;
        this.url      = url;
        this.username = username;
        this.password = password;
        executor.scheduleAtFixedRate(new Runnable() {
            public void run() {
                cleanup();
                setReaperThreadName();
            }
        }, 60, 60, TimeUnit.SECONDS);
    }
    
    public int getId(Connection connection) {
        ConnectionInfo ci = connectionInfoMap.get(connection);
        return ci != null ? ci.getId() : -1;
    }
    
    public Connection getConnection() throws SQLException {
        Connection connection = null;
        synchronized (this) {
            if (!idleConnections.isEmpty()) {
                connection = idleConnections.iterator().next();
                idleConnections.remove(connection);
            }
        }
        if (connection == null) {
            connection = DriverManager.getConnection(url, username, password);
            ConnectionInfo ci = new ConnectionInfo(connection);
            synchronized (this) {
                connectionInfoMap.put(connection, ci);
            }
        }
        if (log.isLoggable(Level.FINE)) {
            ConnectionInfo ci = connectionInfoMap.get(connection);
            log.fine("Thread " + Thread.currentThread() + " caller allocated Connection with id = " + ci.getId());
        }
        return connection;
    }
    
    public void ungetConnection(Connection connection) {
        ConnectionInfo ci;
        synchronized (this) {
            ci = connectionInfoMap.get(connection);
            if (ci == null) {
                throw new IllegalStateException("Returning connection to the wrong pool");
            }
            idleConnections.add(connection);
        }
        if (log.isLoggable(Level.FINE)) {
            log.fine("Thread " + Thread.currentThread() +" deallocated connection with id = " + ci.getId());
        }
        ci.touch();
        cleanup();
    }
    
    public Statement getStatement(Connection connection) throws SQLException {
        ConnectionInfo ci;
        synchronized (this) {
            ci = connectionInfoMap.get(connection);
            if (ci == null) {
                throw new IllegalStateException("Connection not managed by this pool");
            }
        }
        return ci.getStatement();
    }
    
    public PreparedStatement getPreparedStatement(Connection connection, String text) throws SQLException {
        ConnectionInfo ci;
        synchronized (this) {
            ci = connectionInfoMap.get(connection);
            if (ci == null) {
                throw new IllegalStateException("Connection not managed by this pool");
            }
        }
        return ci.getPreparedStatement(text);
    }

    public void reference() {
        referenceCount++;
    }
    
    public void dereference() throws SQLException {
        referenceCount--;
        if (referenceCount == 0) {
            synchronized (connectionPoolMap) {
                DatabaseParam dp = new DatabaseParam(driver, url, username, password);
                connectionPoolMap.remove(dp);
            }
            executor.shutdownNow(); // no more multi-threaded  access...
            for (ConnectionInfo ci : connectionInfoMap.values()) {
                ci.close();
            }
        }
    }
    
    public void closeStatements(Connection connection) throws SQLException {
        ConnectionInfo ci;
        synchronized (this) {
            ci = connectionInfoMap.get(connection);
            if (ci == null) {
                throw new IllegalStateException("Connection not managed by this pool");
            }
        }
        ci.closeStatements();
    }
    
    public void reportProblem(Connection connection) throws SQLException {
        ConnectionInfo ci;
        synchronized (this) {
            ci = connectionInfoMap.remove(connection);
            if (ci == null) {
                throw new IllegalStateException("Connection not managed by this pool");
            }
            idleConnections.remove(connection);
        }
        ci.close();
    }
    

    private  void cleanup() {
        long now = System.currentTimeMillis();
        List<ConnectionInfo> connections = new ArrayList<ConnectionInfo>();

        synchronized (this) {
            for (Connection connection : idleConnections) {
                connections.add(connectionInfoMap.get(connection));
            }
            Collections.sort(connections, new Comparator<ConnectionInfo>() {
                public int compare(ConnectionInfo o1, ConnectionInfo o2) {
                    return (int) (o1.getLastAccessTime() - o2.getLastAccessTime());
                }
            });
        }
        for (ConnectionInfo ci : connections) {
            synchronized (this) {
                if (now - ci.getLastAccessTime() <= connectionRefreshInterval) {
                    break;
                }
                else {
                    idleConnections.remove(ci.getConnection());
                    connectionInfoMap.remove(ci.getConnection());
                }
            }
            try {
                ci.close();
            }
            catch (Throwable t) {
                if (log.isLoggable(Level.WARNING)) {
                    log.log(Level.WARNING, "Exception caught closing connection during cleanup", t);
                }
            }
        }

        for (ConnectionInfo ci : connections) {
            synchronized(this) {
                if (idleConnections.size() <= maxOpenConnections) {
                    break;
                }
                else {
                    idleConnections.remove(ci.getConnection());
                    connectionInfoMap.remove(ci.getConnection());
                }
            }
            try {
                ci.close();
            }
            catch (Throwable t) {
                if (log.isLoggable(Level.WARNING)) {
                    log.log(Level.WARNING, "Exception caught closing connection during cleanup", t);
                }
            }
        }
    }
    
    private void setReaperThreadName() {
        int connectionCount;
        int idleCount;
        synchronized (this) {
            connectionCount  = connectionInfoMap.size();
            idleCount = idleConnections.size();
        }
        Thread.currentThread().setName("Connection Reaper [" + connectionCount + ", " + idleCount + "]");
    }
}
