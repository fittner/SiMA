maxspeed = 0.25 #safety reasons!!!

def headreset(proxies):
    if proxies['motion'] == None:
        print "... no motion proxy found"
        return

    
    names  = ['HeadYaw', 'HeadPitch']
    angles  = [0.0, 0.0]

    proxies['motion'].setAngles(names, angles, 1.0*maxspeed)
