"""
Walk

Small example to make Nao walk

"""
import config
import time
motionProxy = config.loadProxy("ALMotion")

#Set NAO in stiffness On
config.StiffnessOn(motionProxy)

#TARGET VELOCITY
X = 1.0  #forward
Y = 0.0
Theta = 0.0
Frequency =1.0 #max speed
motionProxy.setWalkTargetVelocity(X, Y, Theta, Frequency)

time.sleep(4.0)

#TARGET VELOCITY : END WALK
X = 0.0
Y = 0.0
Theta = 0.0
motionProxy.setWalkTargetVelocity(  X, Y, Theta, Frequency)
