import config
proxy = config.loadProxy("ALMotion")

# Two trajectories in one command. Each trajectory must have a
# corresponding number of times
names = ["HeadYaw", "HeadPitch"]
angleLists = [[1.0, -1.0, 1.0, -1.0], [-1.0]]
times      = [[1.0,  2.0, 3.0,  4.0], [ 5.0]]
isAbsolute = True
proxy.angleInterpolation(names, angleLists, times, isAbsolute)


