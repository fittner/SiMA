/**
 * CHANGELOG
 *
 * 25. Apr. 2016 Kollmann - File created
 *
 */
package base.logging;

import org.apache.log4j.Logger;

import base.datatypes.clsDecisionTreeData;
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
        
        private static boolean collect = true;
        
        public static String FinalEvaluation = "FinalEvaluation";
        public static String EmotionEvaluation = "EmotionEvaluation";
        public static String EmotionMatching = "EmotionMatching";
        public static String MemorizedEmotion = "MemorizedEmotion";
        public static String CurrentEmotion = "CurrentEmotion";
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
            data.createNode(data.getById(FinalEvaluation), EmotionEvaluation, 0, 2);
            data.createNode(data.getById(EmotionEvaluation), EmotionMatching, 0, 4);
            data.createNode(data.getById(EmotionMatching), MemorizedEmotion, 0, 6);
            data.createNode(data.getById(EmotionMatching), CurrentEmotion, 0, 6);
            data.createNode(data.getById(CurrentEmotion), EmotionDefenseImpact, 0, 8);
            data.createNode(data.getById(CurrentEmotion), EmotionRuleImpact, 0, 8);
            data.createNode(data.getById(CurrentEmotion), BasicEmotion, 0, 8);
            data.createNode(data.getById(BasicEmotion), Memories, 0, 10);
            data.createNode(data.getById(BasicEmotion), InitialDrives2, 0, 10);
            data.createNode(data.getById(BasicEmotion), ExternalWorld2, 0, 10);
            data.createNode(data.getById(EmotionEvaluation), EmotionExpectation, 0, 4);
            data.createNode(data.getById(FinalEvaluation), DriveEvaluation, 0, 2);
            data.createNode(data.getById(DriveEvaluation), InitialDrives, 0, 4);
            data.createNode(data.getById(DriveEvaluation), MemorizedDriveSatisfactions, 0, 4);
            data.createNode(data.getById(FinalEvaluation), EffortEvaluation, 0, 2);
            data.createNode(data.getById(EffortEvaluation), ExternalWorld, 0, 4);
        }
        
        private double driveDistributionRatio = 0.0f;
        
        public void putDriveFulfillmentImportance_F26(double totalSetDriveImportance, double currentDriveDemands) {
            if(!collect) return;
            
            driveDistributionRatio = totalSetDriveImportance / currentDriveDemands;
        }
        
        public void putGoalF29(clsWordPresentationMeshPossibleGoal oGoal) {
            if(!collect) return;
            
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
            data.getById(EmotionMatching).setData("value", Double.toString(rTempFeelingMatchImportance * scalingFactor));
            data.getById(CurrentEmotion).setData("value", Double.toString(rTempFeelingMatchImportance * scalingFactor));
            data.getById(MemorizedEmotion).setData("value", Double.toString(rTempFeelingMatchImportance * scalingFactor));
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
        
        public void finish() {
            SimaJSONHandler handler = new SimaJSONHandler();
            
            handler.writeData(data.getRoot());
            
            //go through the tree
            printTree(data.getRoot());
            
            System.out.println("done");
        }
}
