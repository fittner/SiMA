def halt(motionproxy):
    #TARGET VELOCITY
    X = 0.0 
    Y = 0.0
    Theta = 0.0
    Frequency = 1.0 #max speed
    motionproxy.setWalkTargetVelocity(X, Y, Theta, Frequency)