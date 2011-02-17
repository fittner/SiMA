from naoqi import ALProxy
import config

def loadMotionProxy():
    return loadProxy("ALMotion")

def loadSpeechProxy():
    return loadProxy("ALTextToSpeech")

def loadProxy(pName):
    PORT = config.PORTNAO
    IP = config.URLNAO
    if (IP == ""):
        print "IP address not defined, aborting"
        print "Please define it in " + __file__
        exit(1)
    print "---------------------"
    print "Loading proxy"
    print "---------------------"
    proxy = ALProxy(pName, IP, PORT)
    print "---------------------"
    print "Starting " + pName + " Tests"
    print "---------------------"
    return proxy

class proxiesContainer:
    def __init__(self):
        self.motion = loadProxy("ALMotion")
        
        self._ALTextToSpeech = False
        try:
            self.speech = loadProxy("ALTextToSpeech")
            self._ALTextToSpeech = True
        
            
            
        
        