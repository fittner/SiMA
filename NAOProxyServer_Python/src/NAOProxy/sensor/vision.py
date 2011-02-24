from eSensors import Sensor 
from eVisionEntryTypes import VisionEntryTypes
from config import innerdelimiter
from config import namedelimiter
from config import polarcoordelimiter
import math
#from NAOProxy.datastorage import datastorage

def init(storage):
    return

def toPolar2D(x, y):
    r = math.sqrt(x*x + y*y)
    a = math.atan2(y, x)
    return (r,a)

def visionentry(id, type, direction, distance, storage):
    assert -1.0 <= direction <= 1.0
    assert 0 <= distance
    
    if id in storage.invisibleEntities:
        data = None # this id is in list of invisibleEntitites. thus, it has to be removed from the resultslist
        print "vision.visionentry: ignored entry with id="+str(id)+" from vision result list."
    else:  
        # id is in rnage and allowed to be visible - add to return results and to visible enities list
        storage.visibleEntities[id] = (type, direction, distance)
        data = str(id) + namedelimiter 
        data += str(type) + polarcoordelimiter 
        data += str(distance) + polarcoordelimiter 
        data += str(direction) + innerdelimiter
        
    return data

def getId(name):
    id = -1
    try:
        id = int(name[0])
    except:
        if name == 'TU':
            id = 0

    return id

def getType(id):
    type = VisionEntryTypes.UNKNOWN
    
    if id == 0:
        type = VisionEntryTypes.STONE
    elif id < 5:
        type = VisionEntryTypes.CAKE
    elif id < 9:
        type = VisionEntryTypes.BUBBLE
        
    return type

def vision(proxies, storage):
#    memory = proxies['memory']
    data = str(Sensor.VISION)+innerdelimiter
    storage.clearVE() # reset list of visible ids - is refilled reach round

    if proxies['vision'] != None:
        cam2ros = proxies['vision'].objects("da") # object= [Name, x, y, z, wx, wy, wz, confidence, time.sec, time.usec ]
        
        added = False
        for object in cam2ros:
            name = object[0]
            x = object[1]
            y = object[2]
            z = object[3]
            r,a=toPolar2D(x, y)
        
            id = getId(name)
            type = getType(id)
        
            print name+", "+str(x)+", "+str(y)+", "+str(z)+" vs. "+str(id)+", "+str(type)+", "+str(r)+"@"+str(a)

            data += visionentry(id, type, a, r, storage)
            added = True

        
        if added:
            data = data[:-1]
    else:
        print "Visionproxy not available!"
        
    return data
