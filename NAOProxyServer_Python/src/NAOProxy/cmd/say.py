import time

def say(proxies, text):
    #TARGET VELOCITY
    if proxies['speech'] != None:
        proxies['speech'].post.say(text)
    elif proxies['log'] != None:
        print "... speech proxy not found -> using log proxy instead"
        proxies['log'].info("say",text)
    else:
        print "... neither speech nor log proxy found"
        return