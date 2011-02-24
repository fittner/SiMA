from eSensors import Sensor 
from getdata import readmemory

def init(storage):
    storage.sensorlist['bump'] = ["Device/SubDeviceList/RFoot/Bumper/Left/Sensor/Value", 
              "Device/SubDeviceList/RFoot/Bumper/Right/Sensor/Value",
              "Device/SubDeviceList/LFoot/Bumper/Left/Sensor/Value",
              "Device/SubDeviceList/LFoot/Bumper/Right/Sensor/Value",
              "Device/SubDeviceList/ChestBoard/Button/Sensor/Value"]  

def bump(proxies, storage):
    memory = proxies['memory']
    return readmemory(memory, Sensor.BUMP, storage.sensorlist['bump'])