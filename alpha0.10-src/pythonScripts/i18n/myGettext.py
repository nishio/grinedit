class MyGettext:
    def __init__(self, langs):
        self.langs = langs
    def setLang(self, la):
        self.la = la
        self.curLang = self.langs.get(self.la, {})
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