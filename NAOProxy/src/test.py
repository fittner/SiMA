'''
Created on Feb 16, 2011

@author: nao
'''
from NAOProxy.sensor.sensorprocessor import readsensors
from NAOProxy.cmd.stiffness import stiffness
from NAOProxy.cmd.initpose import initpose
from NAOProxy.proxy import getProxies
from NAOProxy.cmd.headmove import headmove
from NAOProxy.cmd.headreset import headreset
from NAOProxy.cmd.cower import cower

import time

import config

config.URLNAO = "192.168.1.212"

# ------------------------------------------------------------------------
def connectNao():
    print 'Connecting to NAO ('+config.URLNAO+':'+str(config.PORTNAO)+')'
    proxies = getProxies()
    print 'Initializing NAO'
    stiffness(proxies, True)
    initpose(proxies)
    print ' ... NAO up and operational'
    return proxies
    
# ------------------------------------------------------------------------
def disconnectNao(proxies):
    print 'Shutting NAO down'
    print '... sitting down'
    cower(proxies)
#    print '... waiting for everything to settle'
#    time.sleep(1)
    print '... turning stiffness off'
    stiffness(proxies, False)
    
    return
    
# ------------------------------------------------------------------------
# main program
proxies = connectNao()
time.sleep(1)

if False:
    headmove(proxies, 1.0, 0.0, 1.0)
    time.sleep(2)
    headmove(proxies, -1.0, 0.0, 1.0)
    time.sleep(2)
    headreset(proxies)
    time.sleep(2)
    headmove(proxies, 0.0, 1.0, 1.0)
    time.sleep(2)
    headmove(proxies, 0.0, -1.0, 1.0)
    time.sleep(2)
    headreset(proxies)
    
    
    print ">>> "
    data = readsensors(proxies)
    data = data.split(";")
    for item in sorted(data):
        print "  ", item
    print "\n\n"
    time.sleep(1)
    
    if False:
        data = proxies['memory'].getDataListName()
        print ">>> ",data
        print "\n\n"
        time.sleep(1)

disconnectNao(proxies)