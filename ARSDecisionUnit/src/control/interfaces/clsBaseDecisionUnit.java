/**
 * CHANGELOG
 * 
 * 2011/07/06 TD - added javadoc comments. code sanitation.
 */
package control.interfaces;

import base.clsCommunicationInterface;

import communication.datatypes.clsDataContainer;
import control.communicationPorts.clsCommunicationPortBodyData;
import control.communicationPorts.clsCommunicationPortControl;

import properties.clsProperties;

import du.enums.eDecisionType;

/**
 * The base class for all control architectures/decision units to be used with the brain socket an the projekt
 * DecisionUnitInterface.
 * 
 * @author deutsch 06.07.2011, 12:43:22
 * 
 */
public abstract class clsBaseDecisionUnit implements itfDecisionUnit
{
    /** which decision unit type. @see eDecisionType; @since 06.07.2011 12:46:45 */
    protected eDecisionType                meDecisionType;
    /// ** history of selected and executed actions.; @since 06.07.2011 12:47:05 */
    // public clsActionLogger moActionLogger;

    protected clsCommunicationPortControl  moCommunicationPortControl;
    protected clsCommunicationPortBodyData moCommunicationPortBodyData;

    /**
     * DOCUMENT (deutsch) - insert description
     *
     * @since 06.07.2011 12:47:59
     *
     * @param poPrefix
     *            prefix for all relevant entries in the property object.
     * @param poProp
     *            the property object.
     * @param uid
     *            unique identifier. the body and the brain get the same. eases debugging and logging.
     */
    public clsBaseDecisionUnit(
            String poPrefix, clsProperties poProp, int uid
            )
    {
        setDecisionUnitType();
        applyProperties(poPrefix, poProp);
        // moActionLogger = new clsActionLogger(uid);

        moCommunicationPortBodyData = new clsCommunicationPortBodyData(this);
        moCommunicationPortControl = new clsCommunicationPortControl(this);
    }

    @Override
    public void setBodyDataInterface(
            clsCommunicationInterface poInterface
            )
    {
        moCommunicationPortBodyData.setCommunicationInterface(poInterface);
    }

    @Override
    public void setControlInterface(
            clsCommunicationInterface poInterface
            )
    {
        moCommunicationPortControl.setControlInterface(poInterface);
    }

    /**
     * Processes the incoming data and produces the action commands. Also known as "thinking".
     * 
     * @since 06.07.2011 12:55:30
     * 
     * @see control.interfaces.itfDecisionUnit#process()
     */
    @Override
    public abstract void process(
    );

    /**
     * Returns the sensor data.
     *
     * @since 06.07.2011 13:00:55
     *
     * @return
     */
    public clsDataContainer getSensorData(
    )
    {
        return moCommunicationPortBodyData.getSensorData();
    }

    /**
     * Update the action logger. Add the currently selected actions to the log.
     *
     * @since 06.07.2011 13:01:06
     * 
     * @see control.interfaces.itfDecisionUnit#updateActionLogger()
     */
    @Override
    public void updateActionLogger(
    )
    {
        // moActionLogger.add(moActionProcessor.logText());
    }

    /**
     * Provides the default entries for this class. See config.clsProperties in project DecisionUnitInterface.
     *
     * @since 06.07.2011 13:02:15
     *
     * @param poPrefix
     * @return
     */
    public static clsProperties getDefaultProperties(
            String poPrefix
            )
    {
        // String pre = clsProperties.addDot(poPrefix);

        clsProperties oProp = new clsProperties();

        return oProp;
    }

    /**
     * Applies the provided properties and creates all instances of various member variables.
     *
     * @since 06.07.2011 13:01:45
     *
     * @param poPrefix
     * @param poProp
     */
    private void applyProperties(
            String poPrefix, clsProperties poProp
            )
    {
        // String pre = clsProperties.addDot(poPrefix);

    }

    /**
     * Returns the type of the decision unit.
     * 
     * @since 06.07.2011 12:56:32
     * 
     * @see control.interfaces.itfDecisionUnit#getDecisionUnitType()
     */
    @Override
    public eDecisionType getDecisionUnitType(
    )
    {
        return meDecisionType;
    }

    /**
     * Set the type of the decision unit. To be called by the siblings of this class in their constructor.
     *
     * @since 06.07.2011 12:56:43
     *
     */
    protected abstract void setDecisionUnitType(
    );
}
