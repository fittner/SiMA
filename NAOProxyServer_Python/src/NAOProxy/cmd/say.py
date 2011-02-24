def say(proxies, text):
    #TARGET VELOCITY
    if proxies['speech'] != None:
        proxies['speech'].say(text)
    elif proxies['log'] != None:
        proxies['log'].info("say",text)
