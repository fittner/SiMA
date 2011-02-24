import math

headyawmax = 75.0*math.pi/180.0
headyawmin = -headyawmax

headpitchmax = 25.0*math.pi/180.0
headpitchmin = -35.0*math.pi/180.0

maxspeed = 0.25 #safety reasons!!!

def headmove(proxies, yaw, pitch, speed):
    if proxies['motion'] == None:
        print "... no motion proxy found"
        return

    
    assert -1.0 <= yaw and yaw <= 1.0
    assert -1.0 <= pitch and pitch <= 1.0   
    assert 0.0 < speed <= 1.0
    
    yaw = headyawmax * yaw
    if pitch<0:
        pitch = -headpitchmin * pitch
    else:
        pitch = headpitchmax * pitch
        
    assert headpitchmin <= pitch <= headpitchmax
    assert headyawmin <= yaw <= headyawmax
    
    names  = ['HeadYaw', 'HeadPitch']
    angles  = [yaw, pitch]

    proxies['motion'].setAngles(names, angles, speed*maxspeed)
