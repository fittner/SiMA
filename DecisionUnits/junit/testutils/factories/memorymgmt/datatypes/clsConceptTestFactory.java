/**
 * CHANGELOG
 *
 * 23.02.2013 havlicek - File created
 *
 */
package testutils.factories.memorymgmt.datatypes;

import java.util.List;

import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsConcept.clsAction;
import pa._v38.memorymgmt.datatypes.clsConcept.clsDistance;
import pa._v38.memorymgmt.datatypes.clsConcept.clsEmotion;
import pa._v38.memorymgmt.datatypes.clsConcept.clsEntity;
import pa._v38.tools.clsQuadruppel;

/**
 * DOCUMENT (havlicek) - insert description
 * 
 * @author havlicek 23.02.2013, 11:39:39
 * 
 */
public class clsConceptTestFactory extends clsAbstractDatatypesFactory<clsConcept> {

    private clsConceptTestFactory() {
        moObject = new clsConcept();
    }

    public clsConceptTestFactory create() {
        return new clsConceptTestFactory();
    }

    public clsConceptTestFactory withConceptEntities(List<clsQuadruppel<clsEntity, clsAction, clsEmotion, clsDistance>> poConceptEntities) {
        moObject.setConceptEntities(poConceptEntities);
        return this;
    }

    @Override
    public clsConcept getEmptyObject() {
        moObject = new clsConcept();
        return moObject;
    }

}
