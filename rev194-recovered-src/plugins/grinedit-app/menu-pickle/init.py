import org.nishiohirokazu.swt.SWTUtil as SWTUtil

gettext.addMap({
"ja":{
  "save as Pickle": "PickleŒ`Ž®‚Å•Û‘¶",
  "open as Pickle": "PickleŒ`Ž®‚ðŠJ‚­"
}})

def obj2json(d):
    import java.util.Map as Map
    import java.util.List as List
    import org.python.core.PyJavaInstance as PyJavaInstance
    if isinstance(d, Map):
        newD = {}
        for key in d.keys():
            newD[key] = obj2json(d[key])
        return newD
    elif isinstance(d, List):
            return list(d)
    elif type(d) == PyJavaInstance:
        classname = d.getClass().getName()
        return {"classname": classname, "params": obj2json(d.getParams())}


    return d
    
def func():
    root = {}
    root["version"] = "alpha0.20"
    names = med.getDictNames()
    for i in range(names.size()):
        name = names[i]
        if name == "All":
            continue
        dict = med.getNamedDict(name)
        root[name] = obj2json(dict)

    filename = SWTUtil.getSaveFilename()
    if filename:
        try:
            import cPickle
            fo = open(filename, "w")
            fo.write(cPickle.dumps(root))
            fo.close()
        except Exception, e:
            print e

menuMediator.addMenu(_("save as Pickle"), func, parentName = "File", accel = "CTRL+U")

def func():
    filename = SWTUtil.getFilename()
    if filename:
            import cPickle
            fi = open(filename)
            data = cPickle.loads(fi.read())
            fi.close()
            

            del data["version"]

            import org.nishiohirokazu.graph.Graph as Graph
            
            med.pause = True
            g = Graph()
            med.setGraph(g)

                
            def add(dictName, data = data, g = g):
                dict = data[dictName]
                g.makeDict(dictName)
                for item in dict.values():
                    grinedit.addObject(dictName, item["classname"], item["params"])
                del data[dictName]
                
            add("Vertex")
            add("Edge")
            
            for dictName in data.keys():
                g.makeDict(dictName)
                dict = data[dictName]
                for item in dict.values():
                    grinedit.addObject(dictName, item["classname"], item["params"])

            med.pause = False


menuMediator.addMenu(_("open as Pickle"), func, parentName = "File", accel = "CTRL+P")
