/**
 * CHANGELOG
 *
 * 23.02.2015 Kollmann - File created
 *
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsEmotion;
import base.datatypes.clsPrimaryDataStructure;
import base.datatypes.clsWordPresentationMesh;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 23.02.2015, 18:25:39
 * 
 */
public interface I5_23_receive {
    public void receive_I5_23(ArrayList<clsPrimaryDataStructure> poAffectOnlyList, ArrayList<clsEmotion> poEmotions, clsWordPresentationMesh moWordingToContext2);
}
