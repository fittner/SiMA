def say(proxies, text):
    #TARGET VELOCITY
    if proxies['speech'] != None:
        proxies['speech'].say(text)
