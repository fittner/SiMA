from eSensors import Sensor 
from config import innerdelimiter
from config import namedelimiter

polarcoordelimiter = "@"

def visionentry(id, direction, distance):
    assert -1.0 <= direction <= 1.0
    assert 0 <= distance
    data = id + namedelimiter + str(distance) + polarcoordelimiter +str(direction) + innerdelimiter
    return data

def vision(proxies):
#    memory = proxies['memory']
    data = str(Sensor.ODOMETRY)+innerdelimiter
    
    data += visionentry(str(1), 0, 5)
    data += visionentry(str(1), -0.2, 3)
    data += visionentry(str(2), 0.5, 7)
    data += visionentry(str(7), -0.5, 0.5)
    data += visionentry("torte", -0.1, 3.5)
    data += visionentry("arsini", 0.231, 6.32454)
    
    return data[:-1]