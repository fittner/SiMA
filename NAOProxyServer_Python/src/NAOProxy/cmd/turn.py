import math

def turn(proxies, turn):
    if proxies['motion'] == None:
        print "... no motion proxy found"
        return
    
    #TARGET VELOCITY
    X = 0.0 
    Y = 0.0
    if (turn >= 0.0):
        Theta = -1.0
    else:
        Theta = 1.0
    speed = math.fabs(turn)
    assert 0.0 <= speed and speed <= 1.0
    Frequency = speed #max speed
    proxies['motion'].setWalkTargetVelocity(X, Y, Theta, Frequency)
