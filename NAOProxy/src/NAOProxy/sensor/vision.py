from eSensors import Sensor 
from eVisionEntryTypes import VisionEntryTypes
from config import innerdelimiter
from config import namedelimiter
import math
#from NAOProxy.datastorage import datastorage

polarcoordelimiter = "@"

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
        data = str(id) + namedelimiter + type + polarcoordelimiter + str(distance) + polarcoordelimiter +str(direction) + innerdelimiter
        
    return data

def vision(proxies, storage):
#    memory = proxies['memory']
    data = str(Sensor.ODOMETRY)+innerdelimiter
    
    del storage.visibleEntities[:] # reset list of visible ids - is refilled reach round
    
    data += visionentry(1, VisionEntryTypes.BUBBLE, 0, 5, storage)
    data += visionentry(0, VisionEntryTypes.CAKE, -0.2, 3, storage)
    data += visionentry(2, VisionEntryTypes.CAN, 0.5, 7, storage)
    data += visionentry(7, VisionEntryTypes.STONE, -0.5, 0.5, storage)
    data += visionentry(4, VisionEntryTypes.CAKE, -0.1, 3.5, storage)
    data += visionentry(11, VisionEntryTypes.BUBBLE, 0.231, 6.32454, storage)
    
    return data[:-1]