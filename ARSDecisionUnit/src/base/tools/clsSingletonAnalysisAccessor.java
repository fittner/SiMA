/**
 * CHANGELOG
 *
 * 16.12.2015 Kollmann - File created
 *
 */
package base.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logger.clsLogger;
import memorymgmt.enums.eEmotionType;

import org.slf4j.Logger;

import base.datatypes.clsEmotion;
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
    protected static final Logger log = clsLogger.getLog("analysis.analyzer.singleton");
    private static itfAnalysis moAnalyzer = null;
    
    public static void setAnalyzer(itfAnalysis oAnalyzer) {
        if(oAnalyzer == null) {
            throw new IllegalArgumentException("Analyzer implementation registered at clsSingletonAnalysisAccessor must not be null");
        }
        moAnalyzer = oAnalyzer;
    }
    
    public static itfAnalysis getAnalyzer() {
        if(moAnalyzer == null) {
            log.warn("Trying to access analyzer before it has been initialized - using dummy analyzer clsAnalyzerDummy");
            moAnalyzer = new clsAnalyzerDummy();
        }
        return moAnalyzer;
    }
    
    public static itfDuAnalysis getAnalyzerForGroupId(int nEntityGroupId) {
        return moAnalyzer.getDu(nEntityGroupId);
    }
    
    private static Map<eEmotionType, Double> moInitialValues;
    private static Map<eEmotionType, Double> moLastValues;
    
    public static void log_F71_emotions(List<clsEmotion> poEmotions) {
        Map<String, String> oEmotionVariableMapping = new HashMap<>();
        oEmotionVariableMapping.put(eEmotionType.ANGER.toString(), "ANGER");
        oEmotionVariableMapping.put(eEmotionType.ANXIETY.toString(), "ANXIETY");
        oEmotionVariableMapping.put(eEmotionType.MOURNING.toString(), "MOURNING");
        oEmotionVariableMapping.put(eEmotionType.SATURATION.toString(), "SATURATION");
        oEmotionVariableMapping.put(eEmotionType.ELATION.toString(), "ELATION");
        oEmotionVariableMapping.put(eEmotionType.JOY.toString(), "JOY");
        oEmotionVariableMapping.put(eEmotionType.GUILT.toString(), "GUILT");
        
        for(clsEmotion oEmotion : poEmotions) {
            if(moInitialValues.containsKey(oEmotion.getContent())) {
                moLastValues.put(oEmotion.getContent(), oEmotion.getEmotionIntensity());
            } else {
                moInitialValues.put(oEmotion.getContent(), oEmotion.getEmotionIntensity());
            }
        }
    }
}
