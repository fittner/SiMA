/**
 * CHANGELOG
 *
 * 17.12.2015 Kollmann - File created
 *
 */
package base.tools;

import control.interfaces.itfDuAnalysis;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 17.12.2015, 11:15:34
 * 
 */
public class clsDuAnalyzerDummy implements itfDuAnalysis {

    @Override
    public void putFactor(String oFactorId, String oFactorValue) {
        //do nothing
    }

    /* (non-Javadoc)
     *
     * @since 17.12.2015 11:15:35
     * 
     * @see control.interfaces.itfDuAnalysis#putAction(java.lang.String)
     */
    @Override
    public void putAction(String oActionValue) {
        //do nothing
    }

}
