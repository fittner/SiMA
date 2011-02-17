'''
Created on Feb 16, 2011

@author: nao
'''
from NAOProxy.cmd.commandprocessor import process
from NAOProxy.cmd.stiffness import stiffness
from NAOProxy.cmd.initpose import initpose
from NAOProxy.proxy import loadMotionProxy
from NAOProxy.cmd.eCommands import Commands

import config
import sys
import socket

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
def process_msg(motionproxy, msg):     #split the received msg into command id and params
    data = msg.split(';')
    id = data[0]
    cmd = Commands.UNKOWN
    
    if id == '0':
        cmd = Commands.MOVE
        print 'MOVE ', data[1:]
    elif id == '1':
        cmd = Commands.TURN
        print 'TURN ', data[1:]
    elif id == '2':
        cmd = Commands.HALT
        print 'HALT ', data[1:]
    elif id == '3':
        cmd = Commands.INITPOSE     
        print 'INITPOSE ', data[1:]
    elif id == '4':
        cmd = Commands.STIFFNESS         
        print 'STIFFNESS ', data[1:]
    else:
        print 'UNKNOWN COMMAND ', id
    
    process(motionproxy, cmd, data[1:]) 
    return

# ------------------------------------------------------------------------
def generate_sensordata():   # generate valid formed return msg
    msg = ''

    msg += '0,VISION,nothing to see;'
    msg += '1,ODOMETRY,not moved at all;'
    msg += '2,DISTANCE,1,to infinity and beyond;'
    msg += '2,DISTANCE,0,nothing to measure available;'
    msg += '\n'

    return msg

# ------------------------------------------------------------------------
def connectNao():
    print 'Connecting to NAO ('+config.URLNAO+':'+str(config.PORTNAO)+')'
    motionproxy = loadMotionProxy()
    print 'Initializing NAO'
    stiffness(motionproxy, True)
    initpose(motionproxy)
    print ' ... NAO up and operational'
    return motionproxy
    
# ------------------------------------------------------------------------
def disconnectNao(motionproxy):
    print 'Shutting NAO down'
    stiffness(motionproxy, False)
    return
    
# ------------------------------------------------------------------------
# main program
motionproxy = connectNao()
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
        process_msg(motionproxy, data)
        conn.send( generate_sensordata() )
    conn.close()
    print 'Closed server at port ',PORT 
    process(motionproxy, Commands.HALT, []) 
