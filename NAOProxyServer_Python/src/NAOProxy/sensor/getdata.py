from config import innerdelimiter
from config import namedelimiter

def removeFromList(list, id):
    print "ALMemory: '"+id+"' does not exist. Is temporarily removed from list."
    list.remove(id)
    return

def removeFromDict(list, id):
    print "ALMemory: '"+id+"' does not exist. Is temporarily removed from list."
    del list[id]
    return

def readmemory(memory, id, list):
    """uses getdata2 to split each item in list into a name and a device"""
    
    data = str(id)+innerdelimiter
    elementadded = False
    
    for item in list:
        try:
            data += getdata2(memory, item)
            elementadded = True
        except:
            removeFromList(list, item)

    if elementadded:
        data = data[:-1]

    return data

def readmemory2(memory, id, list):
    """equal value for device and name"""
    
    data = str(id)+innerdelimiter
    elementadded = False
            
    for item in list:
        try:
            data += getdata(memory, item, item)
            elementadded = True
        except:
            removeFromList(list, item)

    if elementadded:
        data = data[:-1]

    return data

def readmemory3(memory, id, list):
    """list is a dictionary with name:device entry pairs"""
    
    data = str(id)+innerdelimiter
    elementadded = False
    
    removecandidates = []
    
    for k,v in list.iteritems():
        try:
            data += getdata(memory, k, v)
            elementadded = True
        except:
            removecandidates.append(k)
            
    for item in removecandidates:
        removeFromDict(list, item)      

    if elementadded:
        data = data[:-1]

    return data

def getdata(memory, name, device):
    data = name + namedelimiter
#    try:
    data += str( memory.getData(device, 0) )
#    except:
#        print "ALMemory: '"+device+"' does not exist"
#        raise Exception
    
    data += innerdelimiter
        
    return data

def getdata2(memory, device):
    pre = len("Device/SubDeviceList/")
    post = len("/Sensor/Value")
    return getdata(memory, device[pre:-post], device)