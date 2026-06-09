class MyGettext:
    def __init__(self, langs):
        self.langs = langs

    def setLang(self, la):
        self.la = la
        self.curLang = self.langs.get(self.la, {})

    def addMap(self, map):
        for lang in map.keys():
            langMap = map[lang]
            targetMap = self.langs.get(lang, {})
            for key in langMap.keys():
                if targetMap.has_key(key):
                    print "gettext: key '%s' conflicts"  % key
                    raise RuntimeException()
                
                targetMap[key] = langMap[key]
            
            self.langs[lang] = targetMap
        
        
    def gettext(self, key):
        result = self.curLang.get(key, key)
        if result == "":
             return key
        return result

    def gettext_noop(self, key):
        return key

    def getFuncs(self):
        """usage: (_, N_) = myGettext.getFunc()"""
        return (self.gettext, self.gettext_noop)