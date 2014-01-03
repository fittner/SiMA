package timing;

import externalmessager.DatapointHandlerForCodeletInterface;

public class DatapointClientFactory {
	private final DatapointHandlerForCodeletInterface codeletHandler;
	
	public DatapointClientFactory(DatapointHandlerForCodeletInterface codeletHandler) {
		this.codeletHandler = codeletHandler;
	}
	
	public DatapointClient createCodelet(String moduleNumber) throws Exception {
		DatapointClient codelet = new CalculateAverageTimeOfModuleClient(this.codeletHandler, moduleNumber);
		
		codelet.setName(codelet.getCodeletName());
		this.codeletHandler.registerCodelet(codelet);
		codelet.start();
		
		return codelet;
	}
}
