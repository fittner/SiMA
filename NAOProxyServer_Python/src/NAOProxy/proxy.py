from naoqi import ALProxy
import config
from config import enableproxies

def loadProxy(pName):
    PORT = config.PORTNAO
    IP = config.URLNAO
    if (IP == ""):
        print "IP address not defined, aborting"
        print "Please define it in " + __file__
        exit(1)
    print "---------------------------------"
    print "Loading proxy '"+pName+"'"
    try:
        proxy = ALProxy(pName, IP, PORT)
        print " ... done"
    except:
        proxy = None
        print "=============================="
        print "Proxy "+pName+" not available"
        print "=============================="
    print "---------------------------------"
    return proxy

def loadMotionProxy():
    if enableproxies['motion']:
        return loadProxy("ALMotion")
    else:
        return None

def loadSpeechProxy():
    if enableproxies['speech']:
        return loadProxy("ALTextToSpeech")
    else:
        return None

def loadLogProxy():
    if enableproxies['log']:
        return loadProxy("ALLogger")
    else:
        return None

def loadMemoryProxy():
    if enableproxies['memory']:
        return loadProxy("ALMemory")
    else:
        return None

def loadSonarProxy():
    if enableproxies['sonar']:
        proxy = loadProxy("ALSonar")
        if proxy != None:
            proxy.subscribe("NAOProxy") # subscribe to the ALSonar proxy to start the sonar system. otherweise, always 0 is returned 
        return proxy
    else:
        return None

def loadLedProxy():
    if enableproxies['led']:
        proxy = loadProxy("ALLeds")
        return proxy
    else:
        return None

def loadVisionProxy():
    if enableproxies['vision']:    
        proxy = loadProxy("armodule")
        return proxy
    else:
        return None

def getProxies():
    print ">>> getproxies"

    proxies = {'memory':loadMemoryProxy(),
               'motion':loadMotionProxy(), 
               'speech':loadSpeechProxy(),
               'log':loadLogProxy(),
               'sonar':loadSonarProxy(),
               'led':loadLedProxy(),
               'vision':loadVisionProxy()
               }
    
    print "<<< done get proxies: ", proxies
    
    return proxies
            
        
        
