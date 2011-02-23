from eCommands import Commands 

from halt import halt
from initpose import initpose
from move import move
from turn import turn
from stiffness import stiffness
from say import say
from headmove import headmove
from headreset import headreset
from cower import cower
from reset import reset
from consume import consume

def process(proxies, storage, cmd, params):
    if cmd == Commands.HALT:
        halt(proxies)
        
    elif cmd == Commands.INITPOSE:
        initpose(proxies)
        
    elif cmd == Commands.MOVE:
        speed = normalize( params[0] )
        forward = toBoolean( params[1] )
        move(proxies, forward, speed)
        
    elif cmd == Commands.STIFFNESS:
        on = toBoolean( params[0] )
        stiffness(proxies, on)
        
    elif cmd == Commands.TURN:
        turnforce = normalize( params[0] )
        turn(proxies, turnforce)
        
    elif cmd == Commands.SENDMESSAGE:
        text = params[0]
        say(proxies, text)
        
    elif cmd == Commands.HEADMOVE:
        yaw = normalize( params[0] )
        pitch = normalize( params[1] )
        speed = normalize( params[2] )
        headmove(proxies, yaw, pitch, speed)
        
    elif cmd == Commands.HEADRESET:
        headreset(proxies)
        
    elif cmd == Commands.COWER:
        cower(proxies)
        
    elif cmd == Commands.RESET:
        reset(proxies, storage)
        
    elif cmd == Commands.CONSUME:
        consume(storage, int( params[0] ))
        
    else:
        print 'unknown command '+cmd
    return
        
def toBoolean(param):
    if param == 'true':
        return True
    else:
        return False
    
def normalize(param):
    return float(param) / 255.0
                
