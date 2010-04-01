package PropertyTest;

import PropertyTest.Properties.clsBWProperties;
import PropertyTest.objects.clsObject;

public class clsMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String poFilename = "test.properties";
		
		clsBWProperties.writeProperties( clsObject.getDefaultProperties("object"), poFilename, "test property file");		
		clsBWProperties oProp = clsBWProperties.readProperties(poFilename);
		
		
//		oProp.list(System.out);
		
		System.out.println("Standard Object -------------------------------------");
		clsObject oObject = new clsObject();
		System.out.println(oObject);
		
		System.out.println("Standard Property Object -------------------------------------");
 		clsObject oObject2 = new clsObject("object",oProp);
		System.out.println(oObject2);

		System.out.println("testA Property Object -------------------------------------");
		clsObject oObject3 = new clsObject("object", clsBWProperties.readProperties("src\\PropertyTest\\resources\\testA.properties") );
		System.out.println(oObject3);

		System.out.println("testB Property Object -------------------------------------");
		clsObject oObject4 = new clsObject("object", clsBWProperties.readProperties("src\\PropertyTest\\resources\\testB.properties") );
		System.out.println(oObject4);
		
		System.out.println("testC Property Object -------------------------------------");
		clsObject oObject5 = new clsObject("object", clsBWProperties.readProperties("src\\PropertyTest\\resources\\testC.properties") );
		System.out.println(oObject5);
		
	}
	

}
