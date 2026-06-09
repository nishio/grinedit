import org.nishiohirokazu.swt.SWTUtil as SWTUtil

gettext.addMap({
"ja":{
  "save as YAML": "YAMLŒ`Ž®‚Å•Û‘¶",
  "open as YAML": "YAMLŒ`Ž®‚ðŠJ‚­"
}})

# import org.nishiohirokazu.grinEdit.menuAction.MA_SaveAsYaml as Y
# menuMediator.addMenu(_("save as YAML"), Y(med), parentName = "File", accel = "CTRL+S")
#
# it's not work because of JYAML's bug


def func():
    med.pause = True
    filename = SWTUtil.getFilename()
    if filename:
        Yaml = med.loadClass("org.ho.yaml.Yaml")
        fi = open(filename)
        data = fi.read()
        data = Yaml.load(data)
        fi.close()

        del data["version"]

        import org.nishiohirokazu.graph.Graph as Graph

        import java.util.Map as Map
        

        g = Graph()
        med.setGraph(g)
        
        DEFAULT_VERTEX_CLASS = "BoxVertex"
        DEFAULT_EDGE_CLASS = "LinearEdge"
        # add vertex
        subdict = data["Vertex"]
        for key in subdict.keySet():
            item = subdict[key]
            if item.containsKey("classname"):
                classname = item["classname"]
                params = item["params"]
            else:
                classname = DEFAULT_VERTEX_CLASS
                params = item

            params["id"] = key
            grinedit.addObject("Vertex", classname, params)

        del data["Vertex"]

        # add edge
        subdict = data["Edge"]
        for key in subdict.keySet():
            item = subdict[key]
            if isinstance(item, Map) and item.containsKey("classname"):
                    classname = item["classname"]
                    params = item["params"]
            else:
                classname = DEFAULT_EDGE_CLASS
                params = item

            if not(isinstance(params, Map)):
                params = {"v1": params[0], "v2": params[1]}
            
            params["id"] = key            
            grinedit.addObject("Edge", classname, params)

        del data["Edge"]

        # add law
        if data.containsKey("Law"):
            subdict = data["Law"]
            for item in subdict.values():
                classname = item["classname"]
                params = item["params"]
    
                grinedit.addObject("Law", classname, params)
    
            del data["Law"]
        else:
            # default laws
            for law in DEFAULT_PHYSICAL_LAWS:
                grinedit.addObject("Law", law, {"id": law})
        
        # add other object
        
        for dictName in data.keySet():
            g.makeDict(dictName)
            subdict = data[dictName]
            for item in subdict.values():
                grinedit.addObject(dictName, item["classname"], item["params"])

        for stl in STATIC_LAYOUT_ON_LOAD:
            stl.layoutStep(g)

    med.pause = False

menuMediator.addMenu(_("open as YAML"), func, parentName = "File", accel = "CTRL+O")
