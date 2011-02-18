from eSensors import Sensor 
from getdata import getdata
from config import innerdelimiter

sensorlist = ["ALSentinel/BatteryLevel",
            "ALSentinel/DoubleClickOccured",
            "ALSentinel/FallManaging",
            "ALSentinel/MemoryLow",
            "ALSentinel/SimpleClickOccured",
            "ALSentinel/TripleClickOccured"]

def sentinel(proxies):
    """http://academics.aldebaran-robotics.com/docs/site_en/bluedoc/ALSentinel.html"""
    
    memory = proxies['memory']
    data = str(Sensor.SENTINEL)+innerdelimiter
    
    for item in sensorlist:
        data += getdata(memory, item, item)

    return data[:-1]    
