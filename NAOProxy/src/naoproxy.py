'''
Created on Feb 16, 2011

@author: nao
'''
from NAOProxy.cmd.commandprocessor import process
from NAOProxy.sensor.sensorprocessor import readsensors
from NAOProxy.cmd.stiffness import stiffness
from NAOProxy.cmd.initpose import initpose
from NAOProxy.cmd.eCommands import Commands
from NAOProxy.proxy import getProxies
from NAOProxy.cmd.cower import cower

import config
import sys
import socket
import time

HOST = config.URLPROXY   # Symbolic name meaning the local host
PORT = config.PORTPROXY  # Arbitrary non-privileged port

if len(sys.argv) > 1:
    config.URLNAO = sys.argv[1]

if len(sys.argv) > 2:
    config.PORTNAO = int(sys.argv[2])


# ------------------------------------------------------------------------
def read_line(s):     # read from socket until new line or NULL is read;
                        # return the received data as string
    ret = ''
    while True:
        c = s.recv(1)
        if c == '\n' or c == '':
            break
        else:
            ret += c
    return ret

# ------------------------------------------------------------------------
def log_msg(proxies, msg):
    print msg
    proxies['log'].info("naoproxy", msg)
    return

# ------------------------------------------------------------------------
def process_msg(proxies, msg):     #split the received msg into command id and params
    data = msg.split(';')
    id = data[0]
    cmd = Commands.UNKOWN
    
    if id == '0':
        cmd = Commands.MOVE
        log_msg( proxies, 'MOVE '+str(data[1:]) )
    elif id == '1':
        cmd = Commands.TURN
        log_msg( proxies,  'TURN '+str(data[1:]) )
    elif id == '2':
        cmd = Commands.HALT
        log_msg( proxies,  'HALT '+str(data[1:]) )
    elif id == '3':
        cmd = Commands.INITPOSE     
        log_msg( proxies,  'INITPOSE '+str(data[1:]) )
    elif id == '4':
        cmd = Commands.STIFFNESS         
        log_msg( proxies,  'STIFFNESS '+str(data[1:]) )
    elif id == '5':
        cmd = Commands.SENDMESSAGE
        log_msg( proxies,  'SENDMESSAGE '+str(data[1:]) )
    elif id == '6':
        cmd = Commands.HEADMOVE
        log_msg( proxies,  'HEADMOVE '+str(data[1:]) )
    elif id == '7':
        cmd = Commands.HEADRESET
        log_msg( proxies,  'HEADRESET '+str(data[1:]) )
    elif id == '8':
        cmd = Commands.COWER
        log_msg( proxies,   'COWER '+str(data[1:]) )
    else:
        log_msg( proxies,  'UNKNOWN COMMAND '+ id)

    process(proxies, cmd, data[1:]) 
    return

# ------------------------------------------------------------------------
def generate_sensordata(proxies):   # generate valid formed return msg
    msg = readsensors(proxies) + "\n"

    return msg

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
    print '... waiting for everything to settle'
    time.sleep(1)
    print '... turning stiffness off'
    stiffness(proxies, False)
    
    return
    
# ------------------------------------------------------------------------
# main program
proxies = connectNao()
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((HOST, PORT))
s.listen(1)
while 1:
    print 'Waiting for connections at port ', PORT
    conn, addr = s.accept()
    print 'Connected by', addr
    while 1:
        data = read_line(conn)
        if not data: break
        process_msg(proxies, data)
        conn.send( generate_sensordata(proxies) )
    conn.close()
    print 'Closed server at port ',PORT 
    process(proxies, Commands.HALT, []) 
