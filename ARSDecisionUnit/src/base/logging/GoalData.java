/**
 * CHANGELOG
 *
 * 25. Apr. 2016 Kollmann - File created
 *
 */
package base.logging;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import base.datatypes.clsDecisionTreeData;
import base.datatypes.clsEmotion;
import base.datatypes.clsWordPresentationMeshPossibleGoal;
import base.datatypes.interfaces.itfMapTreeNode;
import base.tools.SimaJSONHandler;
import secondaryprocess.modules.F29_EvaluationOfImaginaryActions;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 25. Apr. 2016, 16:22:54
 * 
 */
public class GoalData {
        private clsDecisionTreeData data = new clsDecisionTreeData();
        private clsEmotion undefendedBasicEmotion = null;
        private clsEmotion defendedBasicEmotion = null;
        private List<clsEmotion> defendedExtendedEmotion = new ArrayList<>();
        private Map<String, Double> driveBaseEmotionValues = new HashMap<>();
        private Map<String, Double> perceptionBaseEmotionValues = new HashMap<>();
        private Map<String, Double> memoryBaseEmotionValues = new HashMap<>();
        
        private static boolean collect = true;
        
        public static String FinalEvaluation = "FinalEvaluation";
        public static String EmotionEvaluation = "EmotionEvaluation";
        public static String EmotionMatching = "EmotionMatching";
//        public static String MemorizedEmotion = "MemorizedEmotion";
//        public static String CurrentEmotion = "CurrentExtendedEmotion";
        public static String EmotionDefenseImpact = "EmotionDefenseImpact";
        public static String EmotionRuleImpact = "EmotionRuleImpact";
        public static String BasicEmotion = "BasicEmotion";
        public static String Memories = "Memories";
        public static String InitialDrives2 = "InitialDrives2";
        public static String ExternalWorld2 = "ExternalWorld2";
        public static String EmotionExpectation = "EmotionExpectation";
        public static String DriveEvaluation = "DriveEvaluation";
        public static String InitialDrives = "InitialDrives";
        public static String MemorizedDriveSatisfactions = "MemorizedDriveSatisfactions";
        public static String EffortEvaluation = "EffortEvaluation";
        public static String ExternalWorld = "ExternalWorld";
        
        public GoalData() {         
            data.createNode(null, FinalEvaluation, 0, 0);
            data.createNode(data.getById(FinalEvaluation), EmotionEvaluation, 0, 2, Color.BLACK, 0.0);
            data.createNode(data.getById(EmotionEvaluation), EmotionMatching, 0, 4, Color.BLACK, 0.0);
//            data.createNode(data.getById(EmotionMatching), MemorizedEmotion, 0, 6);
//            data.createNode(data.getById(EmotionMatching), CurrentEmotion, 0, 6);
            data.createNode(data.getById(EmotionMatching), EmotionDefenseImpact, 0, 6, Color.YELLOW, 0.5);
            data.createNode(data.getById(EmotionMatching), EmotionRuleImpact, 0, 6, Color.YELLOW, 0.9);
            data.createNode(data.getById(EmotionMatching), BasicEmotion, 0, 6, Color.BLUE, 0.9);
            data.createNode(data.getById(BasicEmotion), Memories, 0, 8, Color.BLUE, 0.0);
            data.createNode(data.getById(BasicEmotion), InitialDrives2, 0, 8, Color.BLUE, 0.5);
            data.createNode(data.getById(BasicEmotion), ExternalWorld2, 0, 8, Color.BLUE, 1.5);
            data.createNode(data.getById(EmotionEvaluation), EmotionExpectation, 0, 4, Color.ORANGE, 0.0);
            data.createNode(data.getById(FinalEvaluation), DriveEvaluation, 0, 2, Color.RED, 0.9);
            data.createNode(data.getById(DriveEvaluation), InitialDrives, 0, 4, Color.RED, 0.0);
            data.createNode(data.getById(DriveEvaluation), MemorizedDriveSatisfactions, 0, 4, Color.RED, 0.5);
            data.createNode(data.getById(FinalEvaluation), EffortEvaluation, 0, 2, Color.GREEN, 0.9);
            data.createNode(data.getById(EffortEvaluation), ExternalWorld, 0, 4, Color.GREEN, 0.0);
        }
        
        private double driveDistributionRatio = 0.0f;
        
        public void putDriveFulfillmentImportance_F26(double totalSetDriveImportance, double currentDriveDemands) {
            if(!collect) return;
            
            driveDistributionRatio = totalSetDriveImportance / currentDriveDemands;
        }
        public void putBasicEmotionF63(clsEmotion basicEmotion) {
            this.undefendedBasicEmotion = basicEmotion;
        }
        
        public void putDefendedBasicEmotionF71(clsEmotion basicEmotion) {
            this.defendedBasicEmotion = basicEmotion;
        }
        
        public void putDefendedExtendedEmotionF71(List<clsEmotion> extendedEmotions) {
            for(clsEmotion emotion : extendedEmotions) {
                this.defendedExtendedEmotion.add(emotion.flatCopy());
            }
        }
        
        public double emotionDifference(clsEmotion firstEmotion, clsEmotion secondEmotion) {
            double difference = 0.0f;
            
            difference = (1 - firstEmotion.compareTo(secondEmotion)); //compareTo delivers the match value
            
            return difference;
        }
        
        public double emotionDifference(clsEmotion basicEmotion, List<clsEmotion> extendedEmotion) {
            double difference = 0.0f;
            
            
            
            return difference;
        }
        
        public void putEmotionVectorsF63(Map<String, Double> driveBaseEmotionVector, Map<String, Double> perceptionBaseEmotionVector, Map<String, Double> memoryBaseEmotionVector) {
            driveBaseEmotionValues = driveBaseEmotionVector;
            perceptionBaseEmotionValues = perceptionBaseEmotionVector;
            //the perception base emotion values vector contains the values further split on how the perception influenced the emotion: by associated drivemeshes, by past experience or by body state
            perceptionBaseEmotionValues.put("rPerceptionUnpleasure", perceptionBaseEmotionValues.get("rPerceptionDriveMeshUnpleasure") + perceptionBaseEmotionValues.get("rPerceptionExperienceUnpleasure") + perceptionBaseEmotionValues.get("rPerceptionBodystateUnpleasure"));
            perceptionBaseEmotionValues.put("rPerceptionPleasure", perceptionBaseEmotionValues.get("rPerceptionDriveMeshPleasure") + perceptionBaseEmotionValues.get("rPerceptionExperiencePleasure") + perceptionBaseEmotionValues.get("rPerceptionBodystatePleasure"));
            perceptionBaseEmotionValues.put("rPerceptionLibid", perceptionBaseEmotionValues.get("rPerceptionDriveMeshLibid") + perceptionBaseEmotionValues.get("rPerceptionExperienceLibid") + perceptionBaseEmotionValues.get("rPerceptionBodystateLibid"));
            perceptionBaseEmotionValues.put("rPerceptionAggr", perceptionBaseEmotionValues.get("rPerceptionDriveMeshAggr") + perceptionBaseEmotionValues.get("rPerceptionExperienceAggr") + perceptionBaseEmotionValues.get("rPerceptionBodystateAggr"));
            
            memoryBaseEmotionValues = memoryBaseEmotionVector;
        }
        
        public List<Double> extractImpact(double emotionValue, double driveValue, double perceptionValue, double memoryValue) {
            double importanceSum = driveValue + perceptionValue + memoryValue;
            double driveImpact = driveValue / importanceSum;
            double perceptionImpact = perceptionValue / importanceSum;
            double memoryImpact = memoryValue / importanceSum;

            return Arrays.asList(driveImpact * emotionValue, perceptionImpact * emotionValue, memoryImpact * emotionValue);
        }
        
        public List<Double> extractImpact(clsEmotion emotion, Map<String, Double> driveVector, Map<String, Double> perceptionVector, Map<String, Double> memoryVector) {
            List<Double> unpleasureImpacts = extractImpact(emotion.getSourceUnpleasure(), driveVector.get("rDriveUnpleasure"), perceptionVector.get("rPerceptionUnpleasure"), memoryVector.get("rPerceptionUnpleasure"));
            List<Double> pleasureImpacts = extractImpact(emotion.getSourcePleasure(), driveVector.get("rDrivePleasure"), perceptionVector.get("rPerceptionPleasure"), memoryVector.get("rPerceptionPleasure"));
            List<Double> libidImpacts = extractImpact(emotion.getSourceLibid(), driveVector.get("rDriveLibid"), perceptionVector.get("rPerceptionLibid"), memoryVector.get("rPerceptionLibid"));
            List<Double> aggrImpacts = extractImpact(emotion.getSourceAggr(), driveVector.get("rDriveAggr"), perceptionVector.get("rPerceptionAggr"), memoryVector.get("rPerceptionAggr"));
            
            List<Double> results = new ArrayList<>();
            
            //safety check
            if(unpleasureImpacts.size() != 3 || pleasureImpacts.size() != 3 || libidImpacts.size() != 3 || aggrImpacts.size() != 3) {
                throw new IllegalArgumentException("One of the emotion impect vectors calculated for the current emotion has an invalid length != 3.\n" + unpleasureImpacts + "\n" + pleasureImpacts + "\n" + libidImpacts + "\n" + aggrImpacts);
            }
            
            for(int i = 0; i < 3; ++i) {
                double value = 0;
                value += unpleasureImpacts.get(i) / emotion.getSourceUnpleasure();
                value += pleasureImpacts.get(i) / emotion.getSourcePleasure();
                value += libidImpacts.get(i) / emotion.getSourceLibid();
                value += aggrImpacts.get(i) / emotion.getSourceAggr();
                
                results.add(value / 4.0f);
            }
            
            return results;
        }
        
        public void putGoalF29(clsWordPresentationMeshPossibleGoal oGoal) {
            if(!collect) return;
            
            DataCollector.all().transferEmotionData(this); //means: ALL transfers emotion data to THIS
            
            double scalingFactor = 1.0;
            
            double rTotalImportance = 0;
            
            //Drive demand and feelings importance are combined via non-proportional aggregation, therefore we divide the
            //PP impact factor, according to the relation between drive demand impact and feelings impact
            double rTempImportanceSum = oGoal.getDriveDemandImportance() +  oGoal.getFeelingsMatchImportance() + oGoal.getFeelingsExcpactationImportance();
            double rTempDriveDemandImportance = oGoal.getPPImportance() * (oGoal.getDriveDemandImportance() / rTempImportanceSum);
            double rTempFeelingMatchImportance = oGoal.getPPImportance() * (oGoal.getFeelingsMatchImportance() / rTempImportanceSum);
            double rTempFeelingExpectationImportance = oGoal.getPPImportance() * (oGoal.getFeelingsExcpactationImportance() / rTempImportanceSum);
            
            data.getById(FinalEvaluation).setData("value", Double.toString(oGoal.getTotalImportance() * scalingFactor));
            
            double rDriveImpact = rTempDriveDemandImportance + oGoal.getDriveDemandCorrectionImportance() + oGoal.getDriveAimImportance();
            
            data.getById(DriveEvaluation).setData("value", Double.toString(rDriveImpact * scalingFactor));
            rTotalImportance += rDriveImpact;
            double rTempValue = (rDriveImpact * scalingFactor / 2.0f) * driveDistributionRatio;
            data.getById(MemorizedDriveSatisfactions).setData("value", Double.toString(rTempValue));
            data.getById(InitialDrives).setData("value", Double.toString(rDriveImpact - rTempValue));
            
            data.getById(EmotionEvaluation).setData("value", Double.toString((rTempFeelingMatchImportance + rTempFeelingExpectationImportance) * scalingFactor));
            double emotionImpact = rTempFeelingMatchImportance * scalingFactor;
            data.getById(EmotionMatching).setData("value", Double.toString(emotionImpact));
//            data.getById(CurrentEmotion).setData("value", Double.toString(emotionImpact));
            double changeDuringExtendedEmotionGeneration = emotionDifference(defendedBasicEmotion, defendedExtendedEmotion);
            data.getById(EmotionRuleImpact).setData("value", Double.toString(emotionImpact * changeDuringExtendedEmotionGeneration));
            emotionImpact = emotionImpact * (1 - changeDuringExtendedEmotionGeneration);
            double emotionDiffAfterDefense = emotionDifference(undefendedBasicEmotion, defendedBasicEmotion);
            data.getById(EmotionDefenseImpact).setData("value", Double.toString(emotionImpact * emotionDiffAfterDefense));
            data.getById(BasicEmotion).setData("value", Double.toString(emotionImpact * (1 - emotionDiffAfterDefense)));
            
            List<Double> emotionImpacts = extractImpact(undefendedBasicEmotion, driveBaseEmotionValues, perceptionBaseEmotionValues, memoryBaseEmotionValues);
            
            data.getById(InitialDrives2).setData("value", Double.toString((emotionImpact * (1 - emotionDiffAfterDefense)) * emotionImpacts.get(0)));
            data.getById(ExternalWorld2).setData("value", Double.toString((emotionImpact * (1 - emotionDiffAfterDefense)) * emotionImpacts.get(1)));
            data.getById(Memories).setData("value", Double.toString((emotionImpact * (1 - emotionDiffAfterDefense)) * emotionImpacts.get(2)));
            
//            data.getById(MemorizedEmotion).setData("value", Double.toString(rTempFeelingMatchImportance * scalingFactor));
            rTotalImportance += rTempFeelingMatchImportance;
            data.getById(EmotionExpectation).setData("value", Double.toString(rTempFeelingExpectationImportance * scalingFactor));
            rTotalImportance += rTempFeelingExpectationImportance;
            
            data.getById(EffortEvaluation).setData("value", Double.toString(oGoal.getEffortImpactImportance() * scalingFactor));
            data.getById(ExternalWorld).setData("value", Double.toString(oGoal.getEffortImpactImportance() * scalingFactor));
            rTotalImportance += oGoal.getEffortImpactImportance(); 
            
            if(Math.abs(rTotalImportance - oGoal.getTotalImportance()) > 0.01) {
//                throw new RuntimeException("Error when collecting data GoalF29: the impacts do not sum up correctly.\nSum of importances = " + rTotalImportance + " but total goal importance = " + oGoal.getTotalImportance());
                Logger.getLogger(F29_EvaluationOfImaginaryActions.class).error("Error when collecting data GoalF29: the impacts do not sum up correctly.\nSum of importances = " + rTotalImportance + " but total goal importance = " + oGoal.getTotalImportance());
            }
            
//            oData.get(3).add(oGoal.getDriveDemandCorrectionImportance());
//            rTotalImportance += oGoal.getDriveDemandCorrectionImportance();
//            oData.get(4).add(oGoal.getEffortImpactImportance());
//            rTotalImportance += oGoal.getEffortImpactImportance();
//            oData.get(5).add(oGoal.getDriveAimImportance());
//            rTotalImportance += oGoal.getDriveAimImportance();
//            oData.get(6).add(oGoal.getSocialRulesImportance());
//            rTotalImportance += oGoal.getSocialRulesImportance();
//            oData.get(7).add(oGoal.getEntityValuationImportance());
//            rTotalImportance += oGoal.getEntityValuationImportance();
//            oData.get(8).add(oGoal.getEntityBodystateImportance());
//            rTotalImportance += oGoal.getEntityBodystateImportance();
//            oData.get(9).add(oGoal.getTotalImportance() - rTotalImportance);
        }
        
        protected void printNode(itfMapTreeNode node) {
            System.out.print("Node: " + node.getData("id") + " has children: ");
            for(itfMapTreeNode child : node.getChildren()) {
                System.out.print(child.getData("id"));
                System.out.print(", ");
            }
            System.out.println(" |");
        }
        
        protected void printTree(itfMapTreeNode node) {
            printNode(node);
            for(itfMapTreeNode child : node.getChildren()) {
                printTree(child);
            }
        }
        
        protected void transferEmotionData(GoalData targetGoal) {
            targetGoal.putBasicEmotionF63(undefendedBasicEmotion);
            targetGoal.putDefendedBasicEmotionF71(defendedBasicEmotion);
            targetGoal.putDefendedExtendedEmotionF71(defendedExtendedEmotion);
            
            targetGoal.putEmotionVectorsF63(driveBaseEmotionValues, perceptionBaseEmotionValues, memoryBaseEmotionValues);
        }
        
        public void finish() {
            SimaJSONHandler handler = new SimaJSONHandler();
            
            handler.writeDecisionTreeData(data.getRoot());
            handler.writeTreeMapData(data.getRoot());
            
            //go through the tree
            printTree(data.getRoot());
            
            System.out.println("done");
        }
}
