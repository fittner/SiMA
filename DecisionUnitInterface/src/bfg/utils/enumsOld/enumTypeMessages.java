/**
 * @author deutsch
 * 
 * Copied from the old bubble family game
 * 
 * CHKME (deutsch) - keep this file?
 * @deprecated taken from old BFG
 * 
 * $Rev:: 1825                 $: Revision of last commit
 * $Author:: deutsch           $: Author of last commit
 * $Date:: 2009-01-27 19:59:35#$: Date of last commit
 */

// File enumTypeMessages.java
// July 17, 2006
//

// Belongs to package
package bfg.utils.enumsOld;

import bfg.utils.enums.enumClass;

// Imports

/**
 *
 * This is the class description ...
 *
 * @deprecated
 * 
 * $Revision: 1825 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2009-01-27 19:59:35 +0100 (Di, 27 Jän 2009) $: Date of last commit
 *
 */
public class enumTypeMessages extends enumClass {
  public final static int TMESSAGES_UNDEFINED = -1;
  public final static int TMESSAGES_DEBUG     = 9999;

  public final static int TMESSAGES_SIMULATOR_ACTIONRESPONSE    = 1000;
  public final static int TMESSAGES_SIMULATOR_CREATEACK         = 1001;
  public final static int TMESSAGES_SIMULATOR_STARTACTIONROUND  = 1002;
  public final static int TMESSAGES_SIMULATOR_ENTITYLOST        = 1003;

  public final static int TMESSAGES_ENTITY_ACTION               = 2000;
  public final static int TMESSAGES_ENTITY_CREATE               = 2001;
  public final static int TMESSAGES_ENTITY_REMOVE               = 2002;

  public final static int TMESSAGES_CLIENT_DISCONNECT           = 3000;
  public final static int TMESSAGES_CLIENT_INITCOMPLETED        = 3001;
  public final static int TMESSAGES_CLIENT_WAITFORINIT          = 3002;

  public final static int TMESSAGES_SERVER_DISCONNECT           = 4000;
  public final static int TMESSAGES_SERVER_DOINIT               = 4001;
  public final static int TMESSAGES_SERVER_RESTART              = 4002;
  public final static int TMESSAGES_SERVER_WAKEUPCALL           = 4003;

  public final static int TMESSAGES_VISUALIZATION_START         = 5000;
  public final static int TMESSAGES_VISUALIZATION_END           = 5001;

  public final static int TMESSAGES_VIS2D_RESET                 = 6000;
  public final static int TMESSAGES_VIS2D_SUBTITLE              = 6001;
  public final static int TMESSAGES_VIS2D_ENTITY_CREATE         = 6100;
  public final static int TMESSAGES_VIS2D_ENTITY_UPDATE         = 6101;
  public final static int TMESSAGES_VIS2D_ENTITY_REMOVE         = 6102;
  public final static int TMESSAGES_VIS2D_LANDSCAPE_CREATE      = 6200;
  public final static int TMESSAGES_VIS2D_OBSTACLE_CREATE       = 6300;
  public final static int TMESSAGES_VIS2D_LIMITS_SET            = 6400;

  public final static int TMESSAGES_CONTR_SIM_PAUSE             = 7000;
  public final static int TMESSAGES_CONTR_SIM_RUN               = 7001;
  public final static int TMESSAGES_CONTR_SIM_STEP              = 7002;
  public final static int TMESSAGES_CONTR_SIM_STOP              = 7003;
  public final static int TMESSAGES_CONTR_SIM_LOAD              = 7004;
  public final static int TMESSAGES_CONTR_SIM_LOAD_ID           = 7005;

  public final static int TMESSAGES_CONTR_RESENDREQUEST_SETUP   = 7050;
  public final static int TMESSAGES_CONTR_RESENDREQUEST_INFO    = 7051;

  public final static int TMESSAGES_CONTR_VIS_HIGHLIGHT         = 7100;
  public final static int TMESSAGES_CONTR_VIS_MOVE              = 7101;
  public final static int TMESSAGES_CONTR_VIS_RESETHIGHLIGHT    = 7102;
  public final static int TMESSAGES_CONTR_VIS_SELECT            = 7103;

  public final static int TMESSAGES_CONTR_NEWCYCLEPING          = 7200;

  public final static int TMESSAGES_CONTR_ENTITYSTATE_ALTERCEMOTION    = 7300;
  public final static int TMESSAGES_CONTR_ENTITYSTATE_ALTERDRIVE       = 7301;
  public final static int TMESSAGES_CONTR_ENTITYSTATE_ALTEREMOTION     = 7302;
  public final static int TMESSAGES_CONTR_ENTITYSTATE_ALTERHEALTHSTATE = 7303;
  public final static int TMESSAGES_CONTR_ENTITYSTATE_ALTERHORMONE     = 7304;
  public final static int TMESSAGES_CONTR_ENTITYSTATE_ALTERALIVE       = 7305;

  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case -1:oResult = "[undefined]";break;
      case  TMESSAGES_SIMULATOR_ACTIONRESPONSE:oResult = "[simulator_actionresponse]";break;
      case  TMESSAGES_SIMULATOR_CREATEACK:oResult = "[simulator_createack]";break;
      case  TMESSAGES_SIMULATOR_STARTACTIONROUND:oResult = "[simulator_startactionround]";break;
      case  TMESSAGES_SIMULATOR_ENTITYLOST:oResult = "[simulator_entitylost]";break;

      case  TMESSAGES_ENTITY_ACTION:oResult = "[entity_action]";break;
      case  TMESSAGES_ENTITY_CREATE:oResult = "[entity_create]";break;
      case  TMESSAGES_ENTITY_REMOVE:oResult = "[entity_remove]";break;

      case  TMESSAGES_CLIENT_DISCONNECT:oResult = "[client_disconnect]";break;
      case  TMESSAGES_CLIENT_INITCOMPLETED:oResult = "[client_initcompleted]";break;
      case  TMESSAGES_CLIENT_WAITFORINIT:oResult = "[client_waitforinit]";break;

      case  TMESSAGES_SERVER_DISCONNECT:oResult = "[server_disconnect]";break;
      case  TMESSAGES_SERVER_DOINIT:oResult = "[server_doinit]";break;
      case  TMESSAGES_SERVER_RESTART:oResult = "[server_restart]";break;
      case  TMESSAGES_SERVER_WAKEUPCALL:oResult = "[server_wakeupcall]";break;

      case  TMESSAGES_VISUALIZATION_START:oResult = "[visualization_disconnect]";break;
      case  TMESSAGES_VISUALIZATION_END:oResult = "[visualization_doinit]";break;

      case  TMESSAGES_VIS2D_RESET:oResult = "[vis2d_reset]";break;
      case  TMESSAGES_VIS2D_SUBTITLE:oResult = "[vis2d_subtitle]";break;
      case  TMESSAGES_VIS2D_ENTITY_CREATE:oResult = "[vis2d_entity_create]";break;
      case  TMESSAGES_VIS2D_ENTITY_UPDATE:oResult = "[vis2d_entity_update]";break;
      case  TMESSAGES_VIS2D_ENTITY_REMOVE:oResult = "[vis2d_entity_remove]";break;
      case  TMESSAGES_VIS2D_LANDSCAPE_CREATE:oResult = "[vis2d_landscape_create]";break;
      case  TMESSAGES_VIS2D_OBSTACLE_CREATE:oResult = "[vis2d_obstacke_create]";break;
      case  TMESSAGES_VIS2D_LIMITS_SET:oResult = "[vis2d_limits_set]";break;

      case  TMESSAGES_CONTR_SIM_PAUSE:oResult = "[contr_sim_pause]";break;
      case  TMESSAGES_CONTR_SIM_RUN:oResult = "[contr_sim_run]";break;
      case  TMESSAGES_CONTR_SIM_STEP:oResult = "[contr_sim_step]";break;
      case  TMESSAGES_CONTR_SIM_STOP:oResult = "[contr_sim_stop]";break;
      case  TMESSAGES_CONTR_SIM_LOAD:oResult = "[contr_sim_load]";break;
      case  TMESSAGES_CONTR_SIM_LOAD_ID:oResult = "[contr_sim_load_id]";break;

      case  TMESSAGES_CONTR_VIS_HIGHLIGHT:oResult = "[contr_entity_highlight]";break;
      case  TMESSAGES_CONTR_VIS_MOVE:oResult = "[contr_entity_move]";break;
      case  TMESSAGES_CONTR_VIS_RESETHIGHLIGHT:oResult = "[contr_entity_resethighlight]";break;
      case  TMESSAGES_CONTR_VIS_SELECT:oResult = "[contr_entity_select]";break;

      case  TMESSAGES_CONTR_NEWCYCLEPING:oResult = "[contr_newcycleping]";break;

      case  TMESSAGES_CONTR_ENTITYSTATE_ALTERCEMOTION:oResult = "[contr_entitystate_alter_complex_emotion]";break;
      case  TMESSAGES_CONTR_ENTITYSTATE_ALTEREMOTION:oResult = "[contr_entitystate_alter_emotion]";break;
      case  TMESSAGES_CONTR_ENTITYSTATE_ALTERDRIVE:oResult = "[contr_entitystate_alter_drive]";break;
      case  TMESSAGES_CONTR_ENTITYSTATE_ALTERHEALTHSTATE:oResult = "[contr_entitystate_alter_healthstate]";break;
      case  TMESSAGES_CONTR_ENTITYSTATE_ALTERHORMONE:oResult = "[contr_entitystate_alter_hormone]";break;
      case  TMESSAGES_CONTR_ENTITYSTATE_ALTERALIVE:oResult = "[contr_entitystate_alter_alive]";break;

      case  TMESSAGES_DEBUG:oResult = "[debug]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }
};
