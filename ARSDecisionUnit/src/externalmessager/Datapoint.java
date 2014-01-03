/**
 * CHANGELOG
 *
 * 02.01.2014 wendt - File created
 *
 */
package externalmessager;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 02.01.2014, 17:01:44
 * 
 */
public class Datapoint<T> {
    private final String DatapointName;
    private T DatapointValue;
    
    public Datapoint(String name, T value) {
        this.DatapointName = name;
        this.DatapointValue = value;
    }
    
    public Datapoint(String name) {
        this.DatapointName = name;
        this.DatapointValue = null;
    }
    
    public T getValue() {
        return (T) this.DatapointValue;
    }
    
    public void setValue(T value) {
        this.DatapointValue = value;
    }
    
    public String getName() {
        return DatapointName;
    }
    
    @Override
    public String toString() {
        String result = this.getName() + ": " + this.DatapointValue;
//        if (this.DatapointValue!=null) {
//            result += ": " + this.DatapointValue;
//        }
        return result;
    }
}
