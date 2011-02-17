"""
Walk

Small example to make Nao walk

"""
import config
import motion
import time
motionProxy = config.loadProxy("ALMotion")

#Set NAO in stiffness On
config.StiffnessOn(motionProxy)

# Example showing smooth arm motions during the walk process
# Enable arm motions
motionProxy.setWalkArmsEnable(True, True)

# Start walking
motionProxy.setWalkTargetVelocity(0.8, 0.0, 0.0, 1.0)

# Wait
time.sleep(4.0)

# User arm motions ( a punch in the air )
Arm1 = [-40,  25, 0, -40, 0, 0]
Arm1 = [ x * motion.TO_RAD for x in Arm1]
Arm2 = [-40,  50, 0, -80, 0, 0]
Arm2 = [ x * motion.TO_RAD for x in Arm2]
motionProxy.angleInterpolationWithSpeed("LArm",Arm1, 0.8)
motionProxy.angleInterpolationWithSpeed("LArm",Arm2, 0.8)
motionProxy.angleInterpolationWithSpeed("LArm",Arm1, 0.8)

# Wait
time.sleep(3.0)

# End the walk
motionProxy.setWalkTargetVelocity(0.0, 0.0, 0.0, 1.0)
