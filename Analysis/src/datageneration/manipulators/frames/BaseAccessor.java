package datageneration.manipulators.frames;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;

import edu.stanford.smi.protege.model.Cls;
import edu.stanford.smi.protege.model.Instance;
import edu.stanford.smi.protege.model.KnowledgeBase;
import edu.stanford.smi.protege.model.Project;
import edu.stanford.smi.protege.model.Slot;
import logger.clsLogger;

public class BaseAccessor implements IAccess {
	private static final Logger moLogger = clsLogger.getLog("factory");
	private Project moProject = null;
	private KnowledgeBase moKnowledgeBase = null;

	public BaseAccessor() {
		
	}
	
	public BaseAccessor(String poProjectSource) {
		open(poProjectSource);
	}
	
	private void handleErrors(Collection<Object> errors) {
		Iterator<Object> i = errors.iterator();
		while (i.hasNext()) {
			moLogger.error("Protege API Error: " + i.next());
		}
		if (!errors.isEmpty()) {
			throw new RuntimeException("Error(s) in Protege API. See console for infos");
		}
	}
	
	@Override
	public boolean open(String poOntologyName) {
		Collection<Object> oErrorList = new ArrayList<Object>();
		moProject = Project.loadProjectFromFile(poOntologyName, oErrorList);
		handleErrors(oErrorList);
		moLogger.debug("Factory created for project {}", poOntologyName);
		moKnowledgeBase = moProject.getKnowledgeBase();
		
		return true;
	}

	@Override
	public boolean save() {
		moLogger.debug("Writing project");
        Collection<Object> errors = new ArrayList<Object>();
        moProject.save(errors);
        handleErrors(errors);
        
        return true;
	}
	
	@Override
	public boolean createItem(String poClassName, String poInstanceName, Map<String, Object> poParameters) {
		boolean bSuccess = false;
		
		moLogger.debug("Creating item " + poInstanceName);
		String oFullName = poClassName + ":" + poInstanceName;
		Instance oInstance = getItem(oFullName); 
		
		if(oInstance == null) {
			oInstance = createInstance(oFullName, poClassName);
		}
		
		for(Map.Entry<String, Object> oEntry : poParameters.entrySet()) {
			setSlotDirect(oInstance, oEntry.getKey(), oEntry.getValue());			
		}
		
		return bSuccess;
	}

	@Override
	public void setSlotDirect(Instance poInstance, String poSlotName, Object poData) {
		Slot oSlot = moKnowledgeBase.getSlot(poSlotName);
		
		if(poData instanceof Collection<?>) {
			poInstance.setOwnSlotValues(oSlot, (Collection<?>) poData);
		} else {
			poInstance.setOwnSlotValue(oSlot, poData.toString());
		}
	}
	
	protected Instance createInstance(String poFullName, String poClassName) {
		Cls oImageClass = moKnowledgeBase.getCls(poClassName);
		if(oImageClass == null) {
			throw new RuntimeException("Unknown instance class " + poClassName + " for instance " + poFullName);
		}
		
		moLogger.debug("Creating new instance " + poFullName);
		Instance oInstance = moKnowledgeBase.createInstance(poFullName, oImageClass);
		
		if(oInstance == null) {
			throw new RuntimeException("Could not create instance " + poFullName + " of class " + poClassName );
		}
		
		return oInstance;
	}

	@Override
	public Instance getItem(String poFullName) {
		return moKnowledgeBase.getInstance(poFullName);
	}
}
