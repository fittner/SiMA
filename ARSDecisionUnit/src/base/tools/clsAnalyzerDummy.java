/**
 * CHANGELOG
 *
 * 17.12.2015 Kollmann - File created
 *
 */
package base.tools;

import java.util.HashMap;
import java.util.Map;

import control.interfaces.itfAnalysis;
import control.interfaces.itfDuAnalysis;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 17.12.2015, 11:15:08
 * 
 */
public class clsAnalyzerDummy implements itfAnalysis {
    Map<Integer, itfDuAnalysis> moDus = new HashMap<>();

    /* (non-Javadoc)
     *
     * @since 17.12.2015 11:15:08
     * 
     * @see control.interfaces.itfAnalysis#registerDu(int)
     */
    @Override
    public void registerDu(int nEntityGroupId) {
        moDus.put(nEntityGroupId, new clsDuAnalyzerDummy());
    }

    /* (non-Javadoc)
     *
     * @since 17.12.2015 11:15:09
     * 
     * @see control.interfaces.itfAnalysis#getDu(int)
     */
    @Override
    public itfDuAnalysis getDu(int nEntityGroupId) {
        return moDus.get(nEntityGroupId);
    }

}
