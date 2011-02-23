"""
Walk

Small example to make Nao walk

"""
import config

motionProxy = config.loadProxy("ALMotion")

#Set NAO in stiffness On
config.StiffnessOn(motionProxy)

# Example showing the walkTo command.
# As length of path is less than 0.4m
# the path will use an SE3 interpolation
# The units for this command are meters and radians
x  = 0.2
y  = 0.2
# pi/2 anti-clockwise (90 degrees)
theta  = 1.5709
motionProxy.walkTo(x, y, theta)
