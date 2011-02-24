from eSensors import Sensor 
from getdata import readmemory

def init(storage):
    storage.sensorlist['position'] = ["Device/SubDeviceList/HeadPitch/Position/Sensor/Value", 
            "Device/SubDeviceList/HeadYaw/Position/Sensor/Value", 
            "Device/SubDeviceList/LAnklePitch/Position/Sensor/Value", 
            "Device/SubDeviceList/LAnkleRoll/Position/Sensor/Value", 
            "Device/SubDeviceList/LElbowRoll/Position/Sensor/Value", 
            "Device/SubDeviceList/LElbowYaw/Position/Sensor/Value", 
            "Device/SubDeviceList/LHand/Position/Sensor/Value", 
            "Device/SubDeviceList/LHipPitch/Position/Sensor/Value", 
            "Device/SubDeviceList/LHipRoll/Position/Sensor/Value", 
            "Device/SubDeviceList/LHipYawPitch/Position/Sensor/Value", 
            "Device/SubDeviceList/LKneePitch/Position/Sensor/Value", 
            "Device/SubDeviceList/LShoulderPitch/Position/Sensor/Value", 
            "Device/SubDeviceList/LShoulderRoll/Position/Sensor/Value", 
            "Device/SubDeviceList/LWristYaw/Position/Sensor/Value", 
            "Device/SubDeviceList/RAnklePitch/Position/Sensor/Value", 
            "Device/SubDeviceList/RAnkleRoll/Position/Sensor/Value", 
            "Device/SubDeviceList/RElbowRoll/Position/Sensor/Value", 
            "Device/SubDeviceList/RElbowYaw/Position/Sensor/Value", 
            "Device/SubDeviceList/RHand/Position/Sensor/Value", 
            "Device/SubDeviceList/RHipPitch/Position/Sensor/Value", 
            "Device/SubDeviceList/RHipRoll/Position/Sensor/Value", 
            "Device/SubDeviceList/RHipYawPitch/Position/Sensor/Value", 
            "Device/SubDeviceList/RShoulderPitch/Position/Sensor/Value", 
            "Device/SubDeviceList/RShoulderRoll/Position/Sensor/Value", 
            "Device/SubDeviceList/RWristYaw/Position/Sensor/Value"]

def position(proxies, storage):
    memory = proxies['memory']
    return readmemory(memory, Sensor.POSITION, storage.sensorlist['position'])
