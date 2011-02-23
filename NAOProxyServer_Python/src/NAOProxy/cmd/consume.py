from config import consumerange

def consume(storage, id):
    """this is a virtual command --- it permanently removes id from the list of visible objects and a consumesuccess msg is generated"""
    
    if id in storage.visibleEntities:
        entry = storage.visibleEntities["id"]
        if entry[2] <= consumerange:
            storage.consumedId = id # set value in storage . will be processed by sensor.consumesuccess
        else:
            print "consume: entity with id="+str(id)+" not in consume area ("+str(entry[2])+">"+str(consumerange)+")."
    else:
        print "consume: entity with id="+str(id)+" not in list of visible entities"
    
    