/**
 * clsThingPresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:48:16
 */
package base.datatypes;

import java.awt.Color;
import java.util.HashMap;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import base.datatypes.helpstructures.clsTriple;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * Introduces clsThingPresentation the atomic data structure TP to the model
 * 
 * moContent (Object):	Defines the content of a data structure – as a thing presentation can hold content of type 
 * 			boolean (Bumb sensor), RGB values (Color), or double values (position), the variable is an object of type Object.  
 * poDataStructureIdentifier (clsTripple):	Holds the data structure Id, the data type, as well as the content type. 
 * 			It is passed on to the super class
 * 
 * @author zeilinger
 * 23.05.2010, 21:48:16
 * 
 */
public class clsThingPresentation extends clsPhysicalRepresentation{
	
	private Object moContent = null;
	/**
	 * @author zeilinger
	 * 17.03.2011, 00:52:10
	 * 
	 * @return the moContent
	 */
	public Object getContent() {
		return moContent;
	}
	/**
	 * @author zeilinger
	 * 17.03.2011, 00:52:10
	 * 
	 * @param moContent the moContent to set
	 */
	public void setMoContent(Object moContent) {
		this.moContent = moContent;
	}
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:43:50
	 *
	 * @param poWordPresentationAssociation
	 */
	public clsThingPresentation(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, Object poContent) {
		
		super(poDataStructureIdentifier);
		moContent = poContent;
	}
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 13.07.2010, 20:58:55
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		double oRetVal = 0.0;
		if(this.moDataStructureType != poDataStructure.moDataStructureType){return oRetVal;}
		
		clsThingPresentation oDataStructure = (clsThingPresentation)poDataStructure;
		//This if statement proofs if the compared datastructure does already have an ID =>
		//the ID sepcifies that the data structure has been already compared with a stored
		//data structure and replaced by it. Hence they can be compared by their IDs.
		if(this.moDS_ID == oDataStructure.moDS_ID){
				/*In case the DataStructureIDs are equal, the return value is the number 
				 * of associated data structures and their number of associations. The idendityMatch number
				 * is not used here as it would distort the result.   
				 */
				oRetVal = 1.0;
		}
		/*Each saved CAKE or other individual shall have an own ID. Here, the ID is treated as a type ID, which makes it
		 *impossible to compare individuals */
		//else if (oDataStructure.moDS_ID > -1) {return oRetVal;}
			
		//In case the data structure does not have an ID, it has to be compared to a stored 
		//data structure and replaced by it (the processes base on information that is already
		//defined
		
		//HZ 16.08.2010 Be careful - as their are upper-case and lower-case issues within the simulator 
		//				and in between the simulator and the ontology regarding the current naming 
		//				of SymbolTypes, the content types are compared without case sensitivity. 
		if(moContentType == oDataStructure.moContentType){
							
				if(this.moContent instanceof Boolean && oDataStructure.moContent instanceof Boolean) {
					oRetVal = compare((Boolean)this.moContent, (Boolean)oDataStructure.moContent );
				}
				else if(this.moContent instanceof String && oDataStructure.moContent instanceof Color )
				{
				    Integer thisR;
                    Integer thisG;
                    Integer thisB;
                    Integer oDataStructureR;
                    Integer oDataStructureG;
                    Integer oDataStructureB;
                    if((((String)this.moContent).length()) > 6)
                    {
                        thisR = (int) Long.parseUnsignedLong(((String) this.moContent).substring(1,3), 16);
                        thisG = (int) Long.parseUnsignedLong(((String) this.moContent).substring(3,5), 16);
                        thisB = (int) Long.parseUnsignedLong(((String) this.moContent).substring(5,7), 16);
                        //Integer.parseUnsignedInt(((String) this.moContent).substring(1,3));
                        oDataStructureR = (((Color)oDataStructure.moContent).getRed());
                        oDataStructureG = (((Color)oDataStructure.moContent).getGreen());
                        oDataStructureB = (((Color)oDataStructure.moContent).getBlue());
                        //oDataStructureG = (int) Long.parseUnsignedLong(((String) oDataStructureR.moContent).substring(5,7), 16);
                        //oDataStructureB = (int) Long.parseUnsignedLong(((String) oDataStructureR.moContent).substring(5,7), 16);
                        oRetVal = Math.sqrt(Math.pow(Math.abs(thisR - oDataStructureR), 2)+Math.pow(Math.abs(thisG - oDataStructureG),2)+Math.pow(Math.abs(thisB - oDataStructureB),2));
                        oRetVal = oRetVal/(Math.sqrt(255*255*3));
                        oRetVal = 1-oRetVal;
                        if(oRetVal<0.95)
                        {
                            oRetVal = compare((String)this.moContent, (String)"#"+Integer.toString(((Color)oDataStructure.moContent).getRGB() & 0xffffff, 16).toUpperCase() );
                        }
                        else
                        {
                            if(oRetVal<1.00)
                            {
                                oDataStructureR=0;
                            }
                        }
                    }
                    else
                    {
    				    oRetVal = 0;
    				}
					//oRetVal = compare((String)this.moContent, (String)"#"+Integer.toString(((Color)oDataStructure.moContent).getRGB() & 0xffffff, 16).toUpperCase() );
				}
				else if(this.moContent instanceof String && oDataStructure.moContent instanceof Boolean ) {
					oRetVal = compare((Boolean) Boolean.parseBoolean(this.moContent.toString()), (Boolean)oDataStructure.moContent );
				}
				else if(this.moContent instanceof String && oDataStructure.moContent instanceof Enum) {
					oRetVal = compare((String)this.moContent, ((Enum<?>)oDataStructure.moContent).name());
				}
				else if(this.moContent instanceof String && oDataStructure.moContent instanceof String) {
					oRetVal = compare((String)this.moContent, (String)oDataStructure.moContent);
				}
				else if(this.moContent instanceof Double && oDataStructure.moContent instanceof Double) {
					oRetVal = compare((Double)this.moContent, (Double)oDataStructure.moContent);
				}
		}
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (herret) - insert description
	 *
	 * @since Jan 30, 2013 1:56:38 PM
	 *
	 * @param moContent2
	 * @param moContent3
	 * @return
	 */
	private double compare(Double moContent2, Double moContent3) {
		if(moContent2 == moContent3) return 1.0;
		else return 0.0;

	}
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 17.08.2010, 10:57:28
	 *
	 * @param moContent2
	 * @param string
	 * @return
	 */
	private double compare(String a, String b) {
		double oRetVal = 0; 
		
		if( a.intern() == b.intern() ){
			oRetVal = 1.0;
		}
		
		return oRetVal; 
	}
	
	private double compare (Boolean a, Boolean b){
		double oRetVal = 0; 
		
		if( a.booleanValue() == b.booleanValue() ){
			oRetVal = 1;
		}
		
		return oRetVal; 
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsThingPresentation oClone = (clsThingPresentation)super.clone();
        	
         	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}
	
	public Object clone(HashMap<clsDataStructurePA, clsDataStructurePA> poClonedNodeMap) throws CloneNotSupportedException {
		clsThingPresentation oClone = null;
		
		try {
			oClone = (clsThingPresentation)super.clone();
        	poClonedNodeMap.put(this, oClone);
        	
         	
        } catch (CloneNotSupportedException e) {
           return e;
        }
        
        return oClone;
	}
	
	@Override
	public String toString(){
		String oResult = "::"+this.moDataStructureType+"::";  
		oResult += this.moDS_ID + ":" + this.moContentType + ":" + this.moContent;
	
		return oResult; 
	}
}
