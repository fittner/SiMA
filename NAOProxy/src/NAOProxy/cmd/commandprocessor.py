from eCommands import Commands 

from halt import halt
from initpose import initpose
from move import move
from turn import turn
from stiffness import stiffness

def process(proxies, cmd, params):
    if cmd == Commands.HALT:
        halt(proxies.motion)
        
    elif cmd == Commands.INITPOSE:
        initpose(proxies.motion)
        
    elif cmd == Commands.MOVE:
        speed = normalize( params[0] )
        forward = toBoolean( params[1] )
        move(proxies.motion, forward, speed)
        
    elif cmd == Commands.STIFFNESS:
        on = toBoolean( params[0] )
        stiffness(proxies.motion, on)
        
    elif cmd == Commands.TURN:
        turnforce = normalize( params[0] )
        turn(proxies.motion, turnforce)
        
    else:
        print 'unkown command '+cmd
    return
        
def toBoolean(param):
    if param == 'true':
        return True
    else:
        return False
    
def normalize(param):
    return float(param) / 255.0
                