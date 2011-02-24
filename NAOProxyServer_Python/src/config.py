PORTNAO = 9559          #port of the nao - default is 9669
URLNAO  = "192.168.1.212"   #address of the nao - either localhost in case of simulation or an arbitrary IP
PORTPROXY = 9669        #port for the tcpsocket which offers the nao proxy to the java program
URLPROXY = ""           #should usually be set to localport - can be left empty. is used to create the tcpsocket
outerdelimiter = ";"
innerdelimiter = ","
namedelimiter  = ":" 
polarcoordelimiter = "@"
maxstiffness = 0.8
consumerange = 0.5      # max range of objects to be consumed. 
enableproxies = {'memory':True, 'motion':True, 'speech':True, 'log':True, 'sonar':True,'led':True, 'vision':True}