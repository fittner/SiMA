from config import innerdelimiter
from config import namedelimiter

def readmemory(memory, id, list):
    """uses getdata2 to split each item in list into a name and a device"""
    
    memory = memory
    
    data = str(id)+innerdelimiter
    
    for item in list:
        data += getdata2(memory, item)

    return data[:-1]

def readmemory2(memory, id, list):
    """equal value for device and name"""
    
    memory = memory
    
    data = str(id)+innerdelimiter
    
    for item in list:
        data += getdata(memory, item, item)

    return data[:-1]

def readmemory3(memory, id, list):
    """list is a dictionary with name:device entry pairs"""
    
    memory = memory
    
    data = str(id)+innerdelimiter
    
    for k,v in list.iteritems():
        data += getdata(memory, k, v)    

    return data[:-1]

def getdata(memory, name, device):
    data = ""
    try:
        data = name + namedelimiter + str( memory.getData(device, 0) ) + innerdelimiter
    except:
        print "ALMemory: '"+device+"' does not exist"
        
    return data

def getdata2(memory, device):
    pre = len("Device/SubDeviceList/")
    post = len("/Sensor/Value")
    return getdata(memory, device[pre:-post], device)