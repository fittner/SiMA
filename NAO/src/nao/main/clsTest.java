package nao.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import NAOProxyClient.NAOProxyClient;

public class clsTest {
	Thread clsNAOThread;
	private String url;
	private String port;
	private static final String _filename = "last.txt";	
	
	private void readUrlPort() {
		File file = new File(_filename);
		
		url = NAOProxyClient._DEFAULT_URL;
		port = String.valueOf(NAOProxyClient._DEFAULT_PORT);
		
		if (file.exists()) {
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(file));
				String line;
				try {
					if ((line = reader.readLine()) != null) {
						String[] values = line.split(":");
						url = values[0];
						port = values[1];
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args){
		clsTest test = new clsTest();
		test.run();
	}
	
	public void run() {
		readUrlPort();
		
		try{
			
			clsNAORun oNAORun= new clsNAORun();
			oNAORun.setMoNAOURL(url);
			oNAORun.setMoNAOPort(Integer.parseInt(port));
			
			clsNAOThread = new Thread( oNAORun );
			clsNAOThread.start();
	
		} catch(Exception e) {
			System.out.println(getCustomStackTrace(e));
		    System.exit(0);	    	      
		}
	}
	
    /**
     * Utility to get a well formed stack trace for displaying it in console
     * @param aThrowable
     * @return
     */
    public static String getCustomStackTrace(Throwable aThrowable) {
        //add the class name and any message passed to constructor
        final StringBuilder result = new StringBuilder( "ARS-EX: " );
        result.append(aThrowable.toString());
        final String NEW_LINE = System.getProperty("line.separator");
        result.append(NEW_LINE);

        //add each element of the stack trace
        for (StackTraceElement element : aThrowable.getStackTrace() ){
          result.append( element );
          result.append( NEW_LINE );
        }
        return result.toString();
      }	
}
