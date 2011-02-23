import config
proxy = config.loadProxy("ALMotion")

# Example showing a joint trajectory with a single destination
names = "HeadYaw"
angleLists = 1.0
times = 1.0
isAbsolute = True
proxy.angleInterpolation(names, angleLists, times, isAbsolute)


