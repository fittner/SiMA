from eSensors import Sensor 
from getdata import readmemory

def init(storage):
    storage.sensorlist['fsr'] = ["Device/SubDeviceList/LFoot/FSR/FrontLeft/Sensor/Value",
              "Device/SubDeviceList/LFoot/FSR/FrontRight/Sensor/Value",
              "Device/SubDeviceList/LFoot/FSR/RearLeft/Sensor/Value",
              "Device/SubDeviceList/LFoot/FSR/RearRight/Sensor/Value",
              "Device/SubDeviceList/RFoot/FSR/FrontLeft/Sensor/Value",
              "Device/SubDeviceList/RFoot/FSR/FrontRight/Sensor/Value",
              "Device/SubDeviceList/RFoot/FSR/RearLeft/Sensor/Value",
              "Device/SubDeviceList/RFoot/FSR/RearRight/Sensor/Value"]

def fsr(proxies, storage):
    """http://academics.aldebaran-robotics.com/docs/site_en/reddoc/hardware/FSR.html"""
    
    memory = proxies['memory']
    return readmemory(memory, Sensor.FSR, storage.sensorlist['fsr'])
