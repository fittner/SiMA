package PropertyTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import PropertyTest.objects.clsObject;

public class clsMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String poFilename = "test.properties";
		
		writeProperties( clsObject.getDefaultProperties("object"), poFilename, "test property file");		
		Properties oProp = readProperties(poFilename);
		
//		oProp.list(System.out);
		
		System.out.println("Standard Object -------------------------------------");
		clsObject oObject = new clsObject();
		System.out.println(oObject);
		
		System.out.println("Standard Property Object -------------------------------------");
		clsObject oObject2 = new clsObject("object",oProp);
		System.out.println(oObject2);

		System.out.println("testA Property Object -------------------------------------");
		clsObject oObject3 = new clsObject("object", readProperties("src\\PropertyTest\\resources\\testA.properties") );
		System.out.println(oObject3);

		System.out.println("testB Property Object -------------------------------------");
		clsObject oObject4 = new clsObject("object", readProperties("src\\PropertyTest\\resources\\testB.properties") );
		System.out.println(oObject4);
	}
	
	public static void writeProperties(Properties poProp, String poFilename, String poComments) {
	    try
	    {
	      FileOutputStream propOutFile =
	         new FileOutputStream( poFilename );
	      
	      poProp.store(propOutFile, poComments);
	    } catch ( FileNotFoundException e ) {
          System.err.println( "Can’t find " + poFilename );
        } catch ( IOException e ) {
	      System.err.println( "I/O failed." );
	    }
	}
	
	public static Properties readProperties(String poFilename) {
        Properties p2 = new Properties();
        
	    try
	    {
	        FileInputStream propInFile = new FileInputStream( poFilename );
	        p2.load( propInFile );
	        
	    } catch ( FileNotFoundException e ) {
          System.err.println( "Can’t find " + poFilename );
        } catch ( IOException e ) {
	      System.err.println( "I/O failed." );
	    }		
        
        return p2;
	}
}
