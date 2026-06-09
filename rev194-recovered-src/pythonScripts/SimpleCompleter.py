
class SimpleCompleter:
    def __init__(self):
        import re
        self.pat_dotted = re.compile("(\w+)\.$")
        self.pat_normal = re.compile("(\w*)$")
    def __call__(self, s):
        result = []
        m = self.pat_dotted.search(s)
        gl = globals()
        if m:
            key = m.groups()[0]
            if gl.has_key(key):
                for item in dir(gl[key]):
                    result.append(item)

        m = self.pat_normal.search(s)
        if m:
            key = m.groups()[0]

            gkeys = gl.keys()
            gkeys.sort()
            for item in gkeys:
                if item.find(key) == 0:
                    result.append(item)
        
