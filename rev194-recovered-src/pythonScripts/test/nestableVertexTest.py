def test():
    cg = med.getCommonGateway()
    cg.pause(True)
    import java
    name = cg.addVertex("NestableVertex", {"label": "a"})
    v = med.getVertex(name)

    name = cg.addVertex("NestableVertex", {"label": "NestableVertexTest"})
    v2 = med.getVertex(name)
    v.addChild(v2)

    name = cg.addVertex("NestableVertex", {"label": "‰Ê•¨"})
    v2 = med.getVertex(name)
    v.addChild(v2)

    name3 = cg.addVertex("NestableVertex", {"label": "‚è‚ñ‚²"})
    v3 = med.getVertex(name3)
    v2.addChild(v3)
    
    name4 = cg.addVertex("CircleVertex", {"bgcolor": (255, 0, 0)})
    print name3, name4
    cg.addEdge("LinearEdge", {"v1": name3, "v2": name4})
    

    name3 = cg.addVertex("NestableVertex", {"label": "ƒoƒiƒi"})
    v3 = med.getVertex(name3)
    v2.addChild(v3)

    name4 = cg.addVertex("CircleVertex", {"bgcolor": (255, 255, 0)})
    print name3, name4
    e = cg.addEdge("LinearEdge", {"v1": name3, "v2": name4})
    es = med.graph.namedDict.get("Edge")
    cg.pause(False)
    