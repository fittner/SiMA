'''
Created on 23.02.2011

@author: deutsch
'''

class datastorage:
    def __init__(self):
        self.invisibleEntities = [] # with each consume Id success, the id is added to this list and thus rendered invisible for the system
        self.visibleEntities = {} # all ids visible in the last sensing step
        self.sensorlist = {} # list off all sensors which should be fetched from ALMemory
        self.consumedId = None
    
    def reset(self):
        del self.invisibleEntities[:]
        self.visibleEntities.clear()
        self.consumedId = None
        
    def clearVE(self):
        self.visibleEntities.clear()