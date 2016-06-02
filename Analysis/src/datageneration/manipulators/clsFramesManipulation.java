package datageneration.manipulators;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import datageneration.clsManipulation;
import datageneration.manipulators.frames.BaseAccessor;
import datageneration.manipulators.frames.IAccess;
import edu.stanford.smi.protege.model.Instance;
import utils.clsGetARSPath;

public class clsFramesManipulation extends clsManipulation {
	IAccess accessor = new BaseAccessor();

	public clsFramesManipulation(URI oTarget) {
		super(oTarget);
	}

	@Override
	public void run() {
		log.error("Frames manipulation not implemented yet");
		
		openFile();
		
		String oItemId = getItemId(getTarget());
		String oSlotId = getSlotId(getTarget());
		
//		getAccessor().getItem();
		Instance oItem = getAccessor().getItem(oItemId);
		getAccessor().setSlotDirect(oItem, oSlotId, getValue());
		
		getAccessor().save();
	}

	@Override
	protected File openFile() {
		File file = super.openFile();
		
		getAccessor().open(file.getAbsolutePath());
		
		return file;
	}

	public IAccess getAccessor() {
		return accessor;
	}

	public void setAccessor(IAccess accessor) {
		this.accessor = notNull(accessor, "clsFrameManipulation accessor must not be set to null");
	}

	@Override
	protected String getItemId(URI oTarget) {
		String oFullPath = getTarget().getPath();
		List<String> oParts = new ArrayList<String>(Arrays.asList(oFullPath.substring(1, oFullPath.length()).split("/")));
		if(oParts.size() > 1) {
			return oParts.get(1);
		} else {
			throw new IllegalArgumentException("Cannot extract item id from URI " + oTarget + ". Path element of URI has not enough parts.");
		}
	}
	
	protected String getSlotId(URI oTarget) {
		String oFullPath = getTarget().getPath();
		List<String> oParts = new ArrayList<String>(Arrays.asList(oFullPath.substring(1, oFullPath.length()).split("/")));
		if(oParts.size() > 2) {
			return oParts.get(2);
		} else {
			throw new IllegalArgumentException("Cannot extract item id from URI " + oTarget + ". Path element of URI has not enough parts.");
		}
	}
}
