import config
proxy = config.loadProxy("ALMotion")

# Simple command for the HeadYaw joint at 10% max speed
names = "HeadYaw"
angles = 0.8
fractionMaxSpeed = 0.1
proxy.setAngles(names,angles,fractionMaxSpeed)
