/**
 * CHANGELOG
 *
 * 19.05.2012 hinterleitner - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;
import java.util.List;

import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsPentagon;
import pa._v38.tools.clsTriple;

/**
 * DOCUMENT (havlicek) - The clsConcept class represents the collected ConceptEntities for one agent in one step.
 * 
 * @author hinterleitner 19.05.2012, 16:28:04
 * @author havlicek 13.07.2012 17:32:05
 */
public class clsConcept {

    /**
     * The Concept stored as a {@link clsWordPresentationMesh} to enable integration into the memory.
     */
    protected clsWordPresentationMesh moConceptMesh;

    /** The String representations of the ConceptEntities */
    private List<clsPentagon<clsEntity, clsAction, clsEmotion, clsDistance, clsDrive>> moConceptEntities;

    /**
     * DOCUMENT (havlicek) - Basic Constructor for a new Concept. Holds an empty list of situation specific context entities.
     * 
     * @since 19.05.2012 16:28:39
     * 
     */
    public clsConcept() {
        moConceptMesh = new clsWordPresentationMesh(new clsTriple<Integer, eDataType, eContentType>(1, eDataType.CONCEPT, eContentType.UNDEFINED),
                new ArrayList<clsAssociation>(), "");
        moConceptEntities = new ArrayList<clsPentagon<clsEntity, clsAction, clsEmotion, clsDistance, clsDrive>>();
    }

    /**
     * DOCUMENT (havlicek) - Get the current content of the Concept together with its WPM.
     * 
     * @since 30.09.2012 17:09:03
     * 
     * @return The clsWorldPresentationMesh of the current concept.
     */
    public clsWordPresentationMesh returnContent() {
        return moConceptMesh;
    }

    /**
     * DOCUMENT (havlicek) - Get the current content of the Concept as String
     * 
     * @since 30.09.2012 17:10:14
     * 
     * @return A String representing the current concept.
     */
    public String returnContentString() {
        if (moConceptEntities != null && !moConceptEntities.isEmpty()) {
            return moConceptMesh.moContent;
        }
        return null;
    }

    /**
     * DOCUMENT (havlicek) - Determines if the concept holds any context entities.
     * 
     * @since 17.12.2012 17:42:49
     * 
     * @return <code>true</code> if there are no context entities present yet, <code>false</code> otherwise.
     */
    public boolean isEmpty() {
        if (moConceptEntities == null && moConceptMesh == null) {
            return true;
        }
        if (moConceptEntities.isEmpty()
                && (moConceptMesh.isNullObject() || moConceptMesh.getExternalAssociatedContent().isEmpty()
                        && moConceptMesh.getExternalMoAssociatedContent().isEmpty() && moConceptMesh.getMoInternalAssociatedContent().isEmpty())) {
            return true;
        }
        return false;
    }

    /**
     * @since 21.02.2013 18:52:42
     * 
     * @return the moConceptEntities
     */
    public List<clsPentagon<clsEntity, clsAction, clsEmotion, clsDistance, clsDrive>> getConceptEntities() {
        return moConceptEntities;
    }

    /**
     * @since 21.02.2013 18:52:42
     * 
     * @param poConceptEntities
     *            the moConceptEntities to set
     */
    public void setConceptEntities(final List<clsPentagon<clsEntity, clsAction, clsEmotion, clsDistance, clsDrive>> poConceptEntities) {
        moConceptEntities = poConceptEntities;
    }

    @Override
    public String toString() {
        String text = "";
        text += moConceptEntities.toString();
        return text;
    }

    /*
     * (non-Javadoc)
     * 
     * @since 25.08.2012 13:23:05
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((moConceptMesh == null) ? 0 : moConceptMesh.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @since 25.08.2012 13:23:05
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object poObj) {
        if (this == poObj) {
            return true;
        }
        if (poObj == null) {
            return false;
        }
        if (getClass() != poObj.getClass()) {
            return false;
        }
        clsConcept other = (clsConcept) poObj;
        if (moConceptEntities == null) {
            if (other.moConceptEntities != null) {
                return false;
            }
        } else if (!moConceptEntities.equals(other.moConceptEntities)) {
            return false;
        }
        if (moConceptMesh == null) {
            if (other.moConceptMesh != null) {
                return false;
            }
        } else if (!moConceptMesh.equals(other.moConceptMesh)) {
            return false;
        }
        return true;
    }

    /**
     * DOCUMENT (havlicek) - internal representation of an entity.
     * 
     * @author havlicek 13.10.2012, 15:25:03
     * 
     */
    public class clsEntity {

        private int moDS_ID = -1;
        private String moEntity = "";

        public void setEntity(final int poDS_ID, final String poContent) {
            moDS_ID = poDS_ID;
            moEntity = poContent;
        }

        /**
         * @since 21.02.2013 20:50:12
         * 
         * @return the moDS_ID
         */
        public int getMoDS_ID() {
            return moDS_ID;
        }

        /**
         * @since 21.02.2013 20:50:12
         * 
         * @return the moEntity
         */
        public String getMoEntity() {
            return moEntity;
        }

        /**
         * @since 21.02.2013 20:50:12
         * 
         * @param poDS_ID
         *            the moDS_ID to set
         */
        public void setMoDS_ID(int poDS_ID) {
            moDS_ID = poDS_ID;
        }

        /**
         * @since 21.02.2013 20:50:12
         * 
         * @param poEntity
         *            the moEntity to set
         */
        public void setMoEntity(String poEntity) {
            moEntity = poEntity;
        }

        @Override
        public String toString() {
            String text = "(";
            text += moEntity;
            text += ":";
            text += moDS_ID;
            text += ")";
            return text;
        }

        /*
         * (non-Javadoc)
         * 
         * @since 21.02.2013 19:18:55
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public final int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + moDS_ID;
            result = prime * result + ((moEntity == null) ? 0 : moEntity.hashCode());
            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @since 21.02.2013 19:18:55
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public final boolean equals(Object poObj) {
            if (this == poObj) {
                return true;
            }
            if (poObj == null) {
                return false;
            }
            if (!(poObj instanceof clsEntity)) {
                return false;
            }
            clsEntity other = (clsEntity) poObj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (moDS_ID != other.moDS_ID) {
                return false;
            }
            if (moEntity == null) {
                if (other.moEntity != null) {
                    return false;
                }
            } else if (!moEntity.equals(other.moEntity)) {
                return false;
            }
            return true;
        }

        private clsConcept getOuterType() {
            return clsConcept.this;
        }

    }

    /**
     * DOCUMENT (havlicek) - internal representation of an action.
     * 
     * @author havlicek 13.10.2012, 15:24:48
     * 
     */
    public class clsAction {

        private String moAction = "";

        /**
         * @since 13.10.2012 15:27:57
         * 
         * @return the moAction
         */
        public String getAction() {
            return moAction;
        }

        /**
         * @since 13.10.2012 15:27:57
         * 
         * @param moAction
         *            the moAction to set
         */
        public void setAction(final String poAction) {
            moAction = poAction;
        }

        @Override
        public String toString() {
            String text = "";
            text += moAction;
            return text;
        }

        /*
         * (non-Javadoc)
         * 
         * @since 21.02.2013 19:19:53
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((moAction == null) ? 0 : moAction.hashCode());
            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @since 21.02.2013 19:19:53
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object poObj) {
            if (this == poObj) {
                return true;
            }
            if (poObj == null) {
                return false;
            }
            if (!(poObj instanceof clsAction)) {
                return false;
            }
            clsAction other = (clsAction) poObj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (moAction == null) {
                if (other.moAction != null) {
                    return false;
                }
            } else if (!moAction.equals(other.moAction)) {
                return false;
            }
            return true;
        }

        private clsConcept getOuterType() {
            return clsConcept.this;
        }

    }

    /**
     * DOCUMENT (havlicek) - internal representation of an emotion.
     * 
     * @author havlicek 13.10.2012, 15:24:33
     * 
     */
    public class clsEmotion {

        private String moEmotion = "";

        /**
         * @since 13.10.2012 15:27:11
         * 
         * @return the moEmotion
         */
        public String getEmotion() {
            return moEmotion;
        }

        /**
         * @since 13.10.2012 15:27:11
         * 
         * @param poEmotion
         *            the moEmotion to set
         */
        public void setEmotion(final String poEmotion) {
            this.moEmotion = poEmotion;
        }

        @Override
        public String toString() {
            String text = "";
            text += moEmotion;
            return text;
        }

        /*
         * (non-Javadoc)
         * 
         * @since 21.02.2013 19:20:14
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((moEmotion == null) ? 0 : moEmotion.hashCode());
            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @since 21.02.2013 19:20:14
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object poObj) {
            if (this == poObj) {
                return true;
            }
            if (poObj == null) {
                return false;
            }
            if (!(poObj instanceof clsEmotion)) {
                return false;
            }
            clsEmotion other = (clsEmotion) poObj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (moEmotion == null) {
                if (other.moEmotion != null) {
                    return false;
                }
            } else if (!moEmotion.equals(other.moEmotion)) {
                return false;
            }
            return true;
        }

        private clsConcept getOuterType() {
            return clsConcept.this;
        }

    }

    /**
     * DOCUMENT (havlicek) - internal representation of a distance.
     * 
     * @author havlicek 13.10.2012, 15:23:53
     * 
     */
    public class clsDistance {

        private String moDistance = "";
        private String moPosition = "";

        /**
         * @since 13.10.2012 12:01:28
         * 
         * @return the moPosition
         */
        public String getPosition() {
            return moPosition;
        }

        /**
         * @since 13.10.2012 12:01:28
         * 
         * @param poPosition
         *            the moPosition to set
         */
        public void setPosition(final String poPosition) {
            moPosition = poPosition;
        }

        /**
         * @since 13.10.2012 12:01:42
         * 
         * @return the moDistance
         */
        public String getDistance() {
            return moDistance;
        }

        /**
         * @since 13.10.2012 12:01:42
         * 
         * @param poDistance
         *            the moDistance to set
         */
        public void setDistance(final String poDistance) {
            moDistance = poDistance;
        }

        @Override
        public String toString() {
            String text = "(";
            text += moDistance;
            text += ":";
            text += moPosition;
            text += ")";
            return text;
        }

        /*
         * (non-Javadoc)
         * 
         * @since 21.02.2013 19:20:26
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((moDistance == null) ? 0 : moDistance.hashCode());
            result = prime * result + ((moPosition == null) ? 0 : moPosition.hashCode());
            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @since 21.02.2013 19:20:26
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object poObj) {
            if (this == poObj) {
                return true;
            }
            if (poObj == null) {
                return false;
            }
            if (!(poObj instanceof clsDistance)) {
                return false;
            }
            clsDistance other = (clsDistance) poObj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (moDistance == null) {
                if (other.moDistance != null) {
                    return false;
                }
            } else if (!moDistance.equals(other.moDistance)) {
                return false;
            }
            if (moPosition == null) {
                if (other.moPosition != null) {
                    return false;
                }
            } else if (!moPosition.equals(other.moPosition)) {
                return false;
            }
            return true;
        }

        private clsConcept getOuterType() {
            return clsConcept.this;
        }

    }
    
    /**
     * DOCUMENT (hinterleitner) - insert description 
     * 
     * @author hinterleitner
     * 11.05.2013, 12:24:10
     * 
     */
    public class clsDrive {

    }


}
