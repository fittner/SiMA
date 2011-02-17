def move(proxies, forward, speed):
    #TARGET VELOCITY
    if (forward):
        X = 1.0  #forward
    else:
        X = -1.0 #backward
    Y = 0.0
    Theta = 0.0
    assert 0.0 <= speed and speed <= 1.0
    Frequency = speed #max speed
    proxies.motion.setWalkTargetVelocity(X, Y, Theta, Frequency)