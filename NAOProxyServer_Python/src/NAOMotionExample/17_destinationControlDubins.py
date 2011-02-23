"""
Walk

Small example to make Nao walk

"""
import config

motionProxy = config.loadProxy("ALMotion")

#Set NAO in stiffness On
config.StiffnessOn(motionProxy)

# Example showing the walkTo command
# As length of path is more than 0.4m
# the path will follow a dubins curve
# The units for this command are meters and radians
x  = -0.1
y  = -0.7
theta  = 0.0
motionProxy.walkTo(x, y, theta)
