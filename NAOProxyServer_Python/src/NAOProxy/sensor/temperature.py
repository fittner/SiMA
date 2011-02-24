from eSensors import Sensor 
from getdata import getdata2
from config import innerdelimiter
from getdata import readmemory

def init(storage):
    storage.sensorlist['temperature'] = ["Device/SubDeviceList/Battery/Temperature/Sensor/Value", 
            "Device/SubDeviceList/HeadPitch/Temperature/Sensor/Value", 
            "Device/SubDeviceList/HeadYaw/Temperature/Sensor/Value", 
            "Device/SubDeviceList/LAnklePitch/Temperature/Sensor/Value", 
            "Device/SubDeviceList/LAnkleRoll/Temperature/Sensor/Value", 
            "Device/SubDeviceList/LElbowRoll/Temperature/Sensor/Value", 
            "Device/SubDeviceList/LElbowYaw/Temperature/Sensor/Value", 
            "Device/SubDeviceList/LHand/Temperature/Sensor/Value", 
            "Device/SubDeviceList/LHipPitch/Temperature/Sensor/Value", 
            "Device/SubDeviceList/LHipRoll/Temperature/Sensor/Value", 
            "Device/SubDeviceList/LHipYawPitch/Temperature/Sensor/Value", 
            "Device/SubDeviceList/LKneePitch/Temperature/Sensor/Value", 
            "Device/SubDeviceList/LShoulderPitch/Temperature/Sensor/Value", 
            "Device/SubDeviceList/LShoulderRoll/Temperature/Sensor/Value", 
            "Device/SubDeviceList/LWristYaw/Temperature/Sensor/Value", 
            "Device/SubDeviceList/RAnklePitch/Temperature/Sensor/Value", 
            "Device/SubDeviceList/RAnkleRoll/Temperature/Sensor/Value", 
            "Device/SubDeviceList/RElbowRoll/Temperature/Sensor/Value", 
            "Device/SubDeviceList/RElbowYaw/Temperature/Sensor/Value", 
            "Device/SubDeviceList/RHand/Temperature/Sensor/Value", 
            "Device/SubDeviceList/RHipPitch/Temperature/Sensor/Value", 
            "Device/SubDeviceList/RHipRoll/Temperature/Sensor/Value", 
            "Device/SubDeviceList/RKneePitch/Temperature/Sensor/Value", 
            "Device/SubDeviceList/RShoulderPitch/Temperature/Sensor/Value", 
            "Device/SubDeviceList/RShoulderRoll/Temperature/Sensor/Value", 
            "Device/SubDeviceList/RWristYaw/Temperature/Sensor/Value"]

def temperature(proxies, storage):
    memory = proxies['memory']
    return readmemory(memory, Sensor.TEMPERATURE, storage.sensorlist['temperature'])
