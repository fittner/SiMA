def halt(proxies):
    #TARGET VELOCITY
    X = 0.0 
    Y = 0.0
    Theta = 0.0
    Frequency = 1.0 #max speed
    proxies.motion.setWalkTargetVelocity(X, Y, Theta, Frequency)