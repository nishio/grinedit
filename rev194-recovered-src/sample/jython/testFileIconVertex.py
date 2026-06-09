#
# test FileIconVertex
#

vs = []
for ext in "xls,doc,ppt,txt,ini".split(","):
    v1 = grinedit.addVertex("org.nishiohirokazu.graph.FileIconVertex", {"extension": ext})
    for v2 in vs:
        grinedit.addEdge("LinearEdge", {"v1": v1, "v2": v2})
    
    vs.append(v1)