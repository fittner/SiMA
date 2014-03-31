/**
 * 
 */
package du.itf.sensors;

import java.util.Formatter;

/**
 * @author langr
 *
 * Actual energy consumption - the sum of all consumers
 *
 */
public class clsEnergyConsumption extends clsSensorIntern implements Cloneable {
	protected double mrEnergyConsumption;
	
	public double getEnergyConsumption() {
		return mrEnergyConsumption;
	}
	public void setEnergyConsumption(double prEnergy) {
		mrEnergyConsumption = prEnergy;
	}
	
	@Override
	public String logXML() {
		String logEntry = "";
		
		logEntry += addXMLTag("Energy", new Double(mrEnergyConsumption).toString()); 

		return addXMLTag(logEntry);
	}
	
	@Override
	public String toString() {
		return getClassName()+": EnergyConsumption "+mrEnergyConsumption;
	}

	@Override
	public String logHTML() {
		Formatter oDoubleFormatter = new Formatter();
		return "<tr><td>"+getClassName()+"</td><td>"+oDoubleFormatter .format("%.5f",mrEnergyConsumption)+"</td></tr>";
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
