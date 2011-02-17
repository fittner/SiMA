def say(proxies, text):
    #TARGET VELOCITY
    if proxies._ALTextToSpeech:
        proxies.speech.say(text)