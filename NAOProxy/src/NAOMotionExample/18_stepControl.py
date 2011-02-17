"""
Walk

Small example to make Nao walk

"""
import config
motionProxy = config.loadProxy("ALMotion")

#Set NAO in stiffness On
config.StiffnessOn(motionProxy)

# A small step forwards and anti-clockwise rotation with the left foot
Leg = "LLeg"
X = 0.02
Y = 0.0
Theta = 0.30
motionProxy.stepTo(Leg, X, Y, Theta)
# block script until stepTo task is not finished
motionProxy.waitUntilWalkIsFinished()
