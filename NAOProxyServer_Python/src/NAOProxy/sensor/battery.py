from eSensors import Sensor 
from getdata import readmemory

sensorlist = ["Device/SubDeviceList/Battery/Charge/Sensor/Value", 
              "Device/SubDeviceList/Battery/Current/Sensor/Value"]

def battery(proxies):
    memory = proxies['memory']
    data = readmemory(memory, Sensor.BATTERY, sensorlist)
    return data
    