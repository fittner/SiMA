from halt import halt
from initpose import initpose

def reset(proxies, storage):
    halt(proxies)
    initpose(proxies)
    storage.reset()
