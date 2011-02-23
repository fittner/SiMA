import config
proxy = config.loadProxy("ALMotion")

# Example showing how to get joint names 
print proxy.getJointNames("Body") 

print len(proxy.getJointNames("Body"))

print proxy.getJointNames("LArm")
