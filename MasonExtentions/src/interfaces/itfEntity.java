package interfaces;

import tools.clsPose;

public interface itfEntity {
	public void updateInternalState();
	public void sensing();
	public void processing();
	public void execution();
	public void exec();
	public void updateEntityInternals();
	public void setRegistered(boolean poRegStatus);
	public clsPose getPose();


}
