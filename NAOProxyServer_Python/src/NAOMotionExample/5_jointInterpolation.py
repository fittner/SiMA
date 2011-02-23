import config
proxy = config.loadProxy("ALMotion")

# Example showing a command for the two joints in the 'Head' alias
# 'Head' is expanded to ['HeadYaw','HeadPitch']
names = "Head"
angleLists = [-1.0,-1.0]
times = 1.0
isAbsolute = True
proxy.angleInterpolation(names, angleLists, times, isAbsolute)


