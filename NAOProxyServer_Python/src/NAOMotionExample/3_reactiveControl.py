import config
proxy = config.loadProxy("ALMotion")

import time

# Example simulating reactive control
names = "HeadYaw"
angles = 0.3
fractionMaxSpeed = 0.2
proxy.setAngles(names,angles,fractionMaxSpeed)

# wait half a second
time.sleep(0.5)

# change target
angles = -0.5
proxy.setAngles(names,angles,fractionMaxSpeed)

# wait half a second
time.sleep(0.5)

# change target
angles = 0.5
proxy.setAngles(names,angles,fractionMaxSpeed)
