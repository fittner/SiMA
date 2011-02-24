from naoqi import ALProxy
import config

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
    return loadProxy("ALMotion")

def loadSpeechProxy():
    return loadProxy("ALTextToSpeech")

def loadLogProxy():
    return loadProxy("ALLogger")

def loadMemoryProxy():
    return loadProxy("ALMemory")

def loadSonarProxy():
    proxy = loadProxy("ALSonar")
    if proxy != None:
        proxy.subscribe("NAOProxy") # subscribe to the ALSonar proxy to start the sonar system. otherweise, always 0 is returned 
    return proxy

def loadLedProxy():
    proxy = loadProxy("ALLeds")
    return proxy

def loadVisionProxy():
    proxy = loadProxy("armodule")
    return proxy

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
            
        
        
