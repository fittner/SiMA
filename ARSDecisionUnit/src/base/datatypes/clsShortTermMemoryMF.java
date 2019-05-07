
/**
 * CHANGELOG
 *
 * Jun 29, 2018 fittner - File created
 *
 */
package base.datatypes;

import java.util.ArrayList;
import memorymgmt.interfaces.itfModuleMemoryAccess;


/**
 * DOCUMENT (fittner) - insert description 
 * 
 * @author fittner
 * Jun 29, 2018 - File created
 * 
 */
public class clsShortTermMemoryMF {
    
    public ArrayList<clsShortTermMemoryEntry> moShortTermMemoryMF;
    private static int Steps=0;
    private static int Moment;
    private static int actMoment=0;
    private static int MomentCnt=0;
    private static int prevMoment=0;
    private static boolean changeMoment=false;
    private static int moSTMMaxSize=5;


	public clsShortTermMemoryMF(itfModuleMemoryAccess poLongTermMemory	) {
	    moShortTermMemoryMF = new ArrayList<clsShortTermMemoryEntry>();
	}
	
    public void changeMoment()
    {
        // remove last Object
        if(moShortTermMemoryMF.size()>=moSTMMaxSize)
        {
            int index=moSTMMaxSize-1;
            
            moShortTermMemoryMF.remove(index);
            
            for(; index > 0; index--)
            {
                moShortTermMemoryMF.add(index, moShortTermMemoryMF.get(index-1));
            }
        }
    }
    
    public void addNewMoment(clsShortTermMemoryEntry Moment)
    {
        moShortTermMemoryMF.add(Moment);
    }

    
    public static void setActualStep(int step)
    {
        Steps = step;
        actMoment = step%10;
        if(actMoment==0)
        {
            changeMoment = true;
            if(step==0)
            {
                MomentCnt++;
            }
        }
        else
        {
            changeMoment = false;
        }

    }
    public static int getActualStep()
    {
        return Steps;
    }
    
    
    public boolean getChangedMoment()
    {
        return changeMoment;
    }

    @Override
    public String toString()
    {
        String text;
        text = "Hello";
        for(clsShortTermMemoryEntry element : moShortTermMemoryMF)
        {
            text += element.toString();
        }
        return text;
    }


}



