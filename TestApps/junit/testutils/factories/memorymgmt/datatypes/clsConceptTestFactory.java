/**
 * CHANGELOG
 *
 * 23.02.2013 havlicek - File created
 *
 */
package testutils.factories.memorymgmt.datatypes;

import java.util.List;

import base.datatypes.clsConcept;
import base.datatypes.clsConcept.clsAction;
import base.datatypes.clsConcept.clsDistance;
import base.datatypes.clsConcept.clsDrive;
import base.datatypes.clsConcept.clsEmotion;
import base.datatypes.clsConcept.clsEntity;
import base.datatypes.helpstructures.clsPentagon;

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

    public static clsConceptTestFactory create() {
        return new clsConceptTestFactory();
    }

    public clsConceptTestFactory withConceptEntities(List<clsPentagon<clsEntity, clsAction, clsEmotion, clsDistance, clsDrive>> poConceptEntities) {
        moObject.setConceptEntities(poConceptEntities);
        return this;
    }

    public clsConceptTestFactory withConceptEntity(clsEntity poEntity, clsAction poAction, clsEmotion poEmotion, clsDistance poDistance, clsDrive poDrive) {
        clsPentagon<clsEntity, clsAction, clsEmotion, clsDistance, clsDrive> oQuad = new clsPentagon<clsConcept.clsEntity, clsConcept.clsAction, clsConcept.clsEmotion, clsConcept.clsDistance, clsConcept.clsDrive>(
                poEntity, poAction, poEmotion, poDistance, poDrive);
        moObject.getConceptEntities().add(oQuad);
        return this;
    }

    @Override
    public clsConcept getEmptyObject() {
        moObject = new clsConcept();
        return moObject;
    }

}
