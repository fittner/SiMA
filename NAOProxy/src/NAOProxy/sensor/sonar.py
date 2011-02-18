from eSensors import Sensor 
from getdata import getdata
from config import innerdelimiter

sensorlist = {"US/Left0":"Device/SubDeviceList/US/Left/Sensor/Value", 
            "US/Left1":"Device/SubDeviceList/US/Left/Sensor/Value1", 
            "US/Left2":"Device/SubDeviceList/US/Left/Sensor/Value2", 
            "US/Left3":"Device/SubDeviceList/US/Left/Sensor/Value3", 
            "US/Left4":"Device/SubDeviceList/US/Left/Sensor/Value4", 
            "US/Left5":"Device/SubDeviceList/US/Left/Sensor/Value5", 
            "US/Left6":"Device/SubDeviceList/US/Left/Sensor/Value6", 
            "US/Left7":"Device/SubDeviceList/US/Left/Sensor/Value7", 
            "US/Left8":"Device/SubDeviceList/US/Left/Sensor/Value8", 
            "US/Left9":"Device/SubDeviceList/US/Left/Sensor/Value9", 
            
            "US/Right0":"Device/SubDeviceList/US/Right/Sensor/Value", 
            "US/Right1":"Device/SubDeviceList/US/Right/Sensor/Value1", 
            "US/Right2":"Device/SubDeviceList/US/Right/Sensor/Value2", 
            "US/Right3":"Device/SubDeviceList/US/Right/Sensor/Value3", 
            "US/Right4":"Device/SubDeviceList/US/Right/Sensor/Value4", 
            "US/Right5":"Device/SubDeviceList/US/Right/Sensor/Value5", 
            "US/Right6":"Device/SubDeviceList/US/Right/Sensor/Value6", 
            "US/Right7":"Device/SubDeviceList/US/Right/Sensor/Value7", 
            "US/Right8":"Device/SubDeviceList/US/Right/Sensor/Value8", 
            "US/Right9":"Device/SubDeviceList/US/Right/Sensor/Value9"}

def sonar(proxies):
    """http://academics.aldebaran-robotics.com/docs/site_en/reddoc/hardware/us_sensor.html"""
    
    memory = proxies['memory']
    
    data = str(Sensor.SONAR)+innerdelimiter
    
    for k,v in sensorlist.iteritems():
        data += getdata(memory, k, v)

    return data[:-1]