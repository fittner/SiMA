package base;
import utils.clsGetARSPath;


public class clsAnalysisConstants {
	public static class config {
		public static class directories {
			public static final String PERSONALITY = clsGetARSPath.getDecisionUnitPeronalityParameterConfigPath();
			public static final String BODY = clsGetARSPath.getBodyPeronalityParameterConfigPath();
			public static final String MEMORY = clsGetARSPath.getMemoryPath() + clsGetARSPath.getSeperator() + "ANALYSIS";
			public static final String SCENARIO = clsGetARSPath.getScenarioPath();
		}
	}
}
