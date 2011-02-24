from eSensors import Sensor 
from getdata import readmemory

def init(storage):
    storage.sensorlist['battery'] = ["Device/SubDeviceList/Battery/Charge/Sensor/Value", 
                          "Device/SubDeviceList/Battery/Current/Sensor/Value"]
    
def battery(proxies, storage):
    memory = proxies['memory']
    return readmemory(memory, Sensor.BATTERY, storage.sensorlist['battery'])
    
