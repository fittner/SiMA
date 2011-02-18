from config import innerdelimiter
from config import namedelimiter

def readmemory(memory, id, list):
    memory = memory
    
    data = str(id)+innerdelimiter
    
    for item in list:
        data += getdata2(memory, item)

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