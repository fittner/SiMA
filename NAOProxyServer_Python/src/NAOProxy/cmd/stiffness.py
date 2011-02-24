import config

def stiffness(proxies, on):
    if on:
        stiffnessOn(proxies)
    else:
        stiffnessOff(proxies)

def stiffnessOff(proxies):
    if proxies['motion'] == None:
        print "... no motion proxy found"
        return
    
    #We use the "Body" name to signify the collection of all joints
    pNames = "Body"
    pStiffnessLists = 0.0
    pTimeLists = 1.0
    proxies['motion'].stiffnessInterpolation(pNames, pStiffnessLists, pTimeLists)
    
def stiffnessOn(proxies):
    if proxies['motion'] == None:
        print "... no motion proxy found"
        return
    
    #We use the "Body" name to signify the collection of all joints
    pNames = "Body"
    pStiffnessLists = config.maxstiffness
    pTimeLists = 1.0
    proxies['motion'].stiffnessInterpolation(pNames, pStiffnessLists, pTimeLists)    