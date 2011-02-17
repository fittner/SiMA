def stiffness(motionproxy, on):
    if on:
        stiffnessOn(motionproxy)
    else:
        stiffnessOff(motionproxy)

def stiffnessOff(motionproxy):
    #We use the "Body" name to signify the collection of all joints
    pNames = "Body"
    pStiffnessLists = 0.0
    pTimeLists = 1.0
    motionproxy.stiffnessInterpolation(pNames, pStiffnessLists, pTimeLists)
    
def stiffnessOn(motionproxy):
    #We use the "Body" name to signify the collection of all joints
    pNames = "Body"
    pStiffnessLists = 1.0
    pTimeLists = 1.0
    motionproxy.stiffnessInterpolation(pNames, pStiffnessLists, pTimeLists)    