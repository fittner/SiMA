package datageneration.manipulators.frames;

import java.util.Map;

import edu.stanford.smi.protege.model.Instance;

public interface IAccess {
	public boolean open(String poOntologyName);
	public boolean createItem(String poClassName, String poInstanceName, Map<String, Object> poParameters);
	public Instance getItem(String poFullName);
	public boolean save();
	public void setSlotDirect(Instance poInstance, String poSlotName, Object poData);
}
