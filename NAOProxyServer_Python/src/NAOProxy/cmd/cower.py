import config
#import time

def cower(proxies):
    if proxies['motion'] == None:
        print "... no motion proxy found"
        return
        
    motionProxy = proxies['motion']
    
    time1 = 2
    time2 = 5
        
    pNames = "Body"
    pStiffnessLists = 0.5 * config.maxstiffness
    pTimeLists = 1.0
    proxies['motion'].stiffnessInterpolation(pNames, pStiffnessLists, pTimeLists)
    
    motionProxy.post.angleInterpolation ( "RShoulderPitch", 0, time1, True)
    motionProxy.post.angleInterpolation ( "LShoulderPitch", 0, time1, True)
    motionProxy.post.angleInterpolation ( "RShoulderRoll", -0.5, time1, True)
    motionProxy.post.angleInterpolation ( "LShoulderRoll", 0.5, time1, True)
    motionProxy.post.angleInterpolation ( "RElbowYaw", 0, time1, True)
    motionProxy.post.angleInterpolation ( "LElbowYaw", 0, time1, True)
    motionProxy.post.angleInterpolation ( "RElbowRoll", 0, time1, True)
    motionProxy.angleInterpolation ( "LElbowRoll", 0, time1, True)
    
    motionProxy.post.angleInterpolation ( "RShoulderPitch", 1.4, time2, True)
    motionProxy.post.angleInterpolation ( "LShoulderPitch", 1.4, time2, True)
    motionProxy.post.angleInterpolation ( "RShoulderRoll", -0.1, time2, True)
    motionProxy.post.angleInterpolation ( "LShoulderRoll", 0.1, time2, True)
    motionProxy.post.angleInterpolation ( "RElbowYaw", 0, time2, True)
    motionProxy.post.angleInterpolation ( "LElbowYaw", 0, time2, True)
    motionProxy.post.angleInterpolation ( "RElbowRoll", 0, time2, True)
    motionProxy.post.angleInterpolation ( "LElbowRoll", 0, time2, True)
    
    motionProxy.post.angleInterpolation ( "RKneePitch", 2.2, time2, True)
    motionProxy.post.angleInterpolation ( "LKneePitch", 2.2, time2, True)
    motionProxy.post.angleInterpolation ( "RHipYawPitch", 0, time2, True)
    motionProxy.post.angleInterpolation ( "LHipYawPitch", 0, time2, True)
    motionProxy.post.angleInterpolation ( "RAnkleRoll", 0, time2, True)
    motionProxy.post.angleInterpolation ( "LAnkleRoll", 0, time2, True)
    motionProxy.post.angleInterpolation ( "RAnklePitch", -2.2, time2, True)
    motionProxy.post.angleInterpolation ( "LAnklePitch", -2.2, time2, True)
    motionProxy.post.angleInterpolation ( "RHipPitch", -1, time2, True)
    motionProxy.post.angleInterpolation ( "LHipPitch", -1, time2, True)
    motionProxy.post.angleInterpolation ( "RHipRoll", 0, time2, True)
    motionProxy.post.angleInterpolation ( "LHipRoll", 0, time2, True)
    
    
    motionProxy.post.angleInterpolation ( "HeadPitch", 0, time2, True)
    motionProxy.angleInterpolation ( "HeadYaw", 0, time2*2, True)

    pNames = "Body"
    pStiffnessLists = 0.0
    pTimeLists = 1.0
    proxies['motion'].stiffnessInterpolation("Head", pStiffnessLists, pTimeLists)
    proxies['motion'].stiffnessInterpolation("LArm", pStiffnessLists, pTimeLists)
    proxies['motion'].stiffnessInterpolation("RArm", pStiffnessLists, pTimeLists)
    
#    pNames = "Body"
#    pStiffnessLists = config.maxstiffness
#    pTimeLists = 1.0
#    proxies['motion'].stiffnessInterpolation(pNames, pStiffnessLists, pTimeLists)    