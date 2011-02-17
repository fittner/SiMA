import config
motionProxy = config.loadProxy("ALMotion")

#We use the "Body" name to signify the collection of all joints
pNames = "Body"
pStiffnessLists = 1.0
pTimeLists = 1.0
motionProxy.stiffnessInterpolation(pNames, pStiffnessLists, pTimeLists)


#~ # Example showing a stiffness trajectory
#~ # Here the stiffness of the HeadYaw Joint, rises to
#~ # 0.8, then goes back to zero.
#~ pNames          = "HeadYaw"
#~ pStiffnessLists = [0.0, 0.8, 0.0]
#~ pTimeLists      = [0.5, 1.0, 1.5]
#~ proxy.stiffnessInterpolation(pNames, pStiffnessLists, pTimeLists)


#~ # Example showing multiple stiffness trajectories
#~ # Here the stiffness of the HeadYaw Joint, rises to
#~ # 0.5, then goes back to zero, while the HeadPitch
#~ # joint rises to 1.0
#~ pNames          = ["HeadYaw","HeadPitch"]
#~ pStiffnessLists = [[0.0, 0.5, 0.0],[0.0, 1.0, 0.0]]
#~ pTimeLists      = [[0.5, 1.0, 1.5],[0.5, 1.0, 1.5]]
#~ proxy.stiffnessInterpolation(pNames, pStiffnessLists, pTimeLists)