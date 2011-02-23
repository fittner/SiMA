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
Frequency = 1.0 #max speed
motionProxy.setWalkTargetVelocity(X, Y, Theta, Frequency)

# Wait
time.sleep(10.0)

# End the walk
motionProxy.setWalkTargetVelocity(0.0, 0.0, 0.0, 1.0)
