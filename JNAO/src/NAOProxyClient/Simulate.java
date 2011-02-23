package NAOProxyClient;

import java.util.Random;
import java.util.Vector;

public class Simulate {
	private static final int _maxwait = 15000;
	private static final int _minwait = 5000;
	private static final int _port = 9669;
//	private static final String _url = "localhost";
	private static final String _url = "192.168.128.130";
	private static final int _loops = 50;
	
	private static void com(NAOProxyClient nao, Command cmd) throws Exception {
		Vector<Sensor> sensors;
		System.out.println(">> c:"+cmd);
		sensors = nao.communicate(cmd);
		System.out.println("<< s:"+sensors);
	}
	
	private static void testCommands(NAOProxyClient nao) throws Exception {
		int wait = 1000;
		System.out.println("testCommands ...");
		
		com(nao, CommandGenerator.halt());	Thread.sleep(wait);
		com(nao, CommandGenerator.turn(1.0));	Thread.sleep(wait);
		com(nao, CommandGenerator.halt());	Thread.sleep(wait);
		com(nao, CommandGenerator.turn(-1.0));	Thread.sleep(wait);
		com(nao, CommandGenerator.halt());	Thread.sleep(wait);
		com(nao, CommandGenerator.move(1.0, true));	Thread.sleep(wait);
		com(nao, CommandGenerator.halt());	Thread.sleep(wait);
		com(nao, CommandGenerator.move(1.0, false));	Thread.sleep(wait);
		com(nao, CommandGenerator.halt());	Thread.sleep(wait);
		com(nao, CommandGenerator.headyaw(-1.0, 1.0));	Thread.sleep(wait);
		com(nao, CommandGenerator.halt());	Thread.sleep(wait);		
		com(nao, CommandGenerator.headyaw(1.0, 1.0));	Thread.sleep(wait);
		com(nao, CommandGenerator.halt());	Thread.sleep(wait);			
		com(nao, CommandGenerator.headreset());	Thread.sleep(wait);
		com(nao, CommandGenerator.halt());	Thread.sleep(wait);
		com(nao, CommandGenerator.headpitch(-1.0, 1.0));	Thread.sleep(wait);
		com(nao, CommandGenerator.halt());	Thread.sleep(wait);		
		com(nao, CommandGenerator.headpitch(1.0, 1.0));	Thread.sleep(wait);
		com(nao, CommandGenerator.halt());	Thread.sleep(wait);	
		com(nao, CommandGenerator.headreset());	Thread.sleep(wait);
		com(nao, CommandGenerator.halt());	Thread.sleep(wait);		
		com(nao, CommandGenerator.say("juhuu!"));	Thread.sleep(wait);
		com(nao, CommandGenerator.halt());	Thread.sleep(wait);
		
		System.out.println("... done");
	}
	
	private static void randomCommands(NAOProxyClient nao) throws Exception {
		System.out.println("randomCommands ...");
		Random rand = new Random();
		
		Command c;

		int wait = 0;
		int waitduration = _maxwait - _minwait; 
		
		for (int i=0; i<_loops; i++) {
			c = CommandGenerator.getRandom();
			System.out.println(i);
			com(nao,c);
			wait = _minwait + rand.nextInt(waitduration);
			System.out.println(" ... wait "+wait+"ms");
			Thread.sleep(wait);
		}		
		System.out.println("... done");
	}
	
	public static void main(String argv[]) throws Exception {
		try {
			NAOProxyClient nao = new NAOProxyClient(_url, _port);
			
			testCommands(nao);
			randomCommands(nao);

			System.out.println("\ndone executing program ... disconnecting from nao.");
			nao.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("\nend.");		
	}
}
