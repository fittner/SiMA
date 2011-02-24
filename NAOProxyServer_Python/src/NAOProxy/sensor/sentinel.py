from eSensors import Sensor 
from getdata import readmemory2

def init(storage):
    storage.sensorlist['sentinel'] = ["ALSentinel/BatteryLevel",
            "ALSentinel/DoubleClickOccured",
            "ALSentinel/FallManaging",
            "ALSentinel/MemoryLow",
            "ALSentinel/SimpleClickOccured",
            "ALSentinel/TripleClickOccured"]

def sentinel(proxies, storage):
    """http://academics.aldebaran-robotics.com/docs/site_en/bluedoc/ALSentinel.html"""
    
    memory = proxies['memory']
    return readmemory2(memory, Sensor.SENTINEL, storage.sensorlist['sentinel'])
