from eSensors import Sensor 
from getdata import readmemory2

sensorlist = ["ALSentinel/BatteryLevel",
            "ALSentinel/DoubleClickOccured",
            "ALSentinel/FallManaging",
            "ALSentinel/MemoryLow",
            "ALSentinel/SimpleClickOccured",
            "ALSentinel/TripleClickOccured"]

def sentinel(proxies):
    """http://academics.aldebaran-robotics.com/docs/site_en/bluedoc/ALSentinel.html"""
    
    memory = proxies['memory']
    data = readmemory2(memory, Sensor.SENTINEL, sensorlist)
    
    return data[:-1]    
