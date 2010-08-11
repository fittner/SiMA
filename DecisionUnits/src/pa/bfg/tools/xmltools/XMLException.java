// File XMLException.java
// June 26, 2007
//

// Belongs to package
package pa.bfg.tools.xmltools;

// Imports

  //this classes exception
  //---------------------------------------------------------------------------
/**
 *  @deprecated
 */
  public class XMLException extends Exception
  //---------------------------------------------------------------------------
  {
    /**
	 * @author deutsch
	 * 10.08.2010, 18:01:26
	 */
	private static final long serialVersionUID = -2495199893859658867L;

	public XMLException(String msg){
      super(msg);
    }
  };

