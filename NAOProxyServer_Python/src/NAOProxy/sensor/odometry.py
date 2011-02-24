from eSensors import Sensor 
from getdata import readmemory

def init(storage):
    storage.sensorlist['odometry'] = ["Device/SubDeviceList/InertialSensor/AccX/Sensor/Value",
              "Device/SubDeviceList/InertialSensor/AccY/Sensor/Value",
              "Device/SubDeviceList/InertialSensor/AccZ/Sensor/Value",
              "Device/SubDeviceList/InertialSensor/GyrX/Sensor/Value",
              "Device/SubDeviceList/InertialSensor/GyrY/Sensor/Value",
              "Device/SubDeviceList/InertialSensor/AngleX/Sensor/Value",
              "Device/SubDeviceList/InertialSensor/AngleY/Sensor/Value"]
    
def odometry(proxies, storage):
    """http://academics.aldebaran-robotics.com/docs/site_en/reddoc/hardware/inertial_unit.html"""
    
    memory = proxies['memory']
    return readmemory(memory, Sensor.ODOMETRY, storage.sensorlist['odometry'])