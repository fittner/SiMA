package NAOProxyClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient
{
	private Socket clientSocket;
	private DataOutputStream outToServer;
	private BufferedReader inFromServer;
	
	public TCPClient(String URL, int port) throws UnknownHostException, IOException  {
		connect(URL, port);
	}
	
	protected void finalize() throws Throwable {
	    try {
	        close();        // close open files
	    } finally {
	        super.finalize();
	    }
	}

	
	private void connect(String URL, int port) throws UnknownHostException, IOException {
		clientSocket = new Socket(URL, port);
	    outToServer = new DataOutputStream(clientSocket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));		
	}
	
	public void close() throws Exception  {
		outToServer = null;
		inFromServer = null;
		clientSocket.close();	
	}
	
	public void send(String msg) throws IOException   {
		outToServer.writeBytes(msg + '\n');
	}
	
	public String recieve() throws IOException   {
		return inFromServer.readLine();
	}
	
	public static void main(String argv[]) throws Exception {
	    String sentence;
	    String modifiedSentence = "";
	    BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));

	    System.out.print("enter line:");
	    sentence = inFromUser.readLine();
	  
	    TCPClient jClient = new TCPClient("localhost", 6996);
	  for (int i=0; i<10; i++) {
	    jClient.send(sentence);
	    modifiedSentence = jClient.recieve();
	  }
	    jClient.close();
	    
	    System.out.println("FROM SERVER: " + modifiedSentence);
	}
}