def say(proxies, text):
    #TARGET VELOCITY
    if proxies['speech'] != None:
        proxies['speech'].say(text)
    elif proxies['log'] != None:
        proxies['log'].into("say",text)
