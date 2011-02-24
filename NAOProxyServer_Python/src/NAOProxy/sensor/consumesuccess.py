from config import innerdelimiter
from eSensors import Sensor

def init(storage):
    return

def consumesuccess(storage):
    data = str(Sensor.CONSUMESUCCESS)+innerdelimiter
    if storage.consumedId != None: # if there is a value different from  None -> this the id of the element just eaten!
        data += str(storage.consumedId)
        storage.consumedId = None # reset value - this id has been consumed now.

    return data