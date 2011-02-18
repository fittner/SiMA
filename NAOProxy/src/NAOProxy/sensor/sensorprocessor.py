from bump import bump
from fsr import fsr
from odometry import odometry
from sonar import sonar
from config import outerdelimiter
from temperature import temperature
from position import position
from battery import battery
from sentinel import sentinel

def readsensors(proxies):
    data = ""
    
    data += bump(proxies) + outerdelimiter
    data += fsr(proxies) + outerdelimiter
    data += odometry(proxies) + outerdelimiter
    data += sonar(proxies) + outerdelimiter
    data += temperature(proxies) + outerdelimiter
    data += position(proxies) + outerdelimiter
    data += battery(proxies) + outerdelimiter
    data += sentinel(proxies) + outerdelimiter
    
    return data[:-1]