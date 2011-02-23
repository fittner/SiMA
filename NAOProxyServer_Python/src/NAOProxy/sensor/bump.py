from eSensors import Sensor 
from getdata import readmemory

sensorlist = ["Device/SubDeviceList/RFoot/Bumper/Left/Sensor/Value", 
              "Device/SubDeviceList/RFoot/Bumper/Right/Sensor/Value",
              "Device/SubDeviceList/LFoot/Bumper/Left/Sensor/Value",
              "Device/SubDeviceList/LFoot/Bumper/Right/Sensor/Value",
              "Device/SubDeviceList/ChestBoard/Button/Sensor/Value"]  

def bump(proxies):
    memory = proxies['memory']
    return readmemory(memory, Sensor.BUMP, sensorlist)