from config import outerdelimiter

import bump
import fsr
import odometry
import sonar
import temperature
import position
import battery
import sentinel
import vision
import consumesuccess

def readsensors(proxies, storage):
    data = ""
    
    data += bump.bump(proxies, storage) + outerdelimiter
    data += sonar.sonar(proxies, storage) + outerdelimiter
    data += fsr.fsr(proxies, storage) + outerdelimiter
    data += odometry.odometry(proxies, storage) + outerdelimiter
    data += temperature.temperature(proxies, storage) + outerdelimiter
    data += position.position(proxies, storage) + outerdelimiter
    data += battery.battery(proxies, storage) + outerdelimiter
    data += sentinel.sentinel(proxies, storage) + outerdelimiter
    data += vision.vision(proxies, storage) + outerdelimiter
    data += consumesuccess.consumesuccess(storage) + outerdelimiter
    
    return data[:-1]

def initsensors(storage):
    bump.init(storage)
    fsr.init(storage)
    odometry.init(storage)
    sonar.init(storage)
    temperature.init(storage)
    position.init(storage)
    battery.init(storage)
    sentinel.init(storage)
    vision.init(storage)
    consumesuccess.init(storage)
    