/**
 * CHANGELOG
 *
 * 16.12.2015 Kollmann - File created
 *
 */
package base.tools;

import control.interfaces.itfAnalysis;
import control.interfaces.itfDuAnalysis;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 16.12.2015, 12:03:18
 * 
 */
public class clsSingletonAnalysisAccessor {
    private static itfAnalysis moAnalyzer = null;
    
    public static void setAnalyzer(itfAnalysis oAnalyzer) {
        if(oAnalyzer == null) {
            throw new IllegalArgumentException("Analyzer implementation registered at clsSingletonAnalysisAccessor must not be null");
        }
        moAnalyzer = oAnalyzer;
    }
    
    public static itfAnalysis getAnalyzer() {
        return moAnalyzer;
    }
    
    public static itfDuAnalysis getAnalyzerForGroupId(int nEntityGroupId) {
        return moAnalyzer.getDu(nEntityGroupId);
    }
}
