import config
proxy = config.loadProxy("ALMotion")

# Shake the head from side to side
names = "HeadYaw"
angleLists = [1.0, -1.0, 1.0, -1.0, 0.0]
times      = [1.0,  2.0, 3.0,  4.0, 5.0]
isAbsolute = True
proxy.angleInterpolation(names, angleLists, times, isAbsolute)


