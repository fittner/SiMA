/**
 * 
 */
package decisionunit.itf.sensors;

import java.util.Formatter;

/**
 * @author langr
 *
 * Actual energy consumption - the sum of all consumers
 *
 */
public class clsEnergyConsumption extends clsSensorIntern implements Cloneable {
	protected double mrEnergy;
	
	public double getEnergy() {
		return mrEnergy;
	}
	public void setEnergy(double prEnergy) {
		mrEnergy = prEnergy;
	}
	
	@Override
	public String logXML() {
		String logEntry = "";
		
		logEntry += addXMLTag("Energy", new Double(mrEnergy).toString()); 

		return addXMLTag(logEntry);
	}
	
	@Override
	public String toString() {
		return getClassName()+": Energy "+mrEnergy;
	}

	@Override
	public String logHTML() {
		Formatter oDoubleFormatter = new Formatter();
		return "<tr><td>"+getClassName()+"</td><td>"+oDoubleFormatter .format("%.5f",mrEnergy)+"</td></tr>";
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsEnergyConsumption oClone = (clsEnergyConsumption)super.clone();

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
}
