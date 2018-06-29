
/**
 * CHANGELOG
 *
 * Jun 29, 2018 fittner - File created
 *
 */
package base.datatypes;

import java.util.ArrayList;

import base.datatypes.helpstructures.clsPair;

/**
 * DOCUMENT (fittner) - insert description 
 * 
 * @author fittner
 * Jun 29, 2018 - File created
 * 
 */
public class clsShortTermMemory {
	
    private double QoAactivation;
    private ArrayList<clsDriveMesh> DMs;
    private ArrayList<clsPair<Integer, ArrayList<clsDriveMesh>>> snapshots = new ArrayList<clsPair<Integer, ArrayList<clsDriveMesh>>>();;
    public static ArrayList<clsPair<Double, clsThingPresentationMesh>> moArrayObjPairSort = new ArrayList<clsPair<Double, clsThingPresentationMesh>>();
    private int Steps;
    
	/**
	 * DOCUMENT (fittner)
	 * 
	 * Constructor of clsShortTermMemory:
	 * 
	 * @param TODO
	 *
	 * @since  Jun 29, 2018 - File created
	 * 
	 */
	public clsShortTermMemory(	) {
	    QoAactivation = 0.0;
	}
	
    public double getActualQoAactivation()
    {
        return QoAactivation;
    }
    
    public void setActualSnapShot(ArrayList<clsDriveMesh> DM)
    {
        snapshots.add(new clsPair<Integer, ArrayList<clsDriveMesh>>(Steps, DM));
    }
    
    public void setActualStep(int step)
    {
        Steps = step;
    }
	
}



