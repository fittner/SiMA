/**
 * CHANGELOG
 *
 * 14.05.2014 herret - File created
 *
 */
package inspector.interfaces;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * 14.05.2014, 10:30:51
 * 
 */
public class clsTimeChartPropeties {
    boolean dynamicScale;

    public clsTimeChartPropeties(boolean dynamicScale) {

        this.dynamicScale = dynamicScale;
    }
    public boolean isDynamicScale() {
        return dynamicScale;
    }

    public void setDynamicScale(boolean dynamicScale) {
        this.dynamicScale = dynamicScale;
    }


    
}
