/**
 * CHANGELOG
 *
 * 16.12.2015 Kollmann - File created
 *
 */
package control.interfaces;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 16.12.2015, 12:00:56
 * 
 */
public interface itfAnalysis {
    public void registerDu(int nEntityGroupId);
    public itfDuAnalysis getDu(int nEntityGroupId);
}
