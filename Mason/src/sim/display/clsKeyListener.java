package sim.display;

public class clsKeyListener {

	private static clsKeyListener moListener = null;
	
	public clsKeyListener()
	{
	}

	public static clsKeyListener getListener()
	{
		if( moListener == null )
		{
			moListener = new clsKeyListener();
		}
		return moListener;
	}
	
	public static int getKeyPressed()
	{
		return moListener.mnKeyPressed;		
	}
	
	public static void setKeyPressed(int keyPressed)
	{
		mnKeyPressed = keyPressed;
	}
	
	private static int mnKeyPressed = 0;
	
}
