maxspeed = 0.25 #safety reasons!!!

def headreset(proxies):
    names  = ['HeadYaw', 'HeadPitch']
    angles  = [0.0, 0.0]

    proxies['motion'].setAngles(names, angles, 1.0*maxspeed)
