#
# test FileIconVertex
#

vs = []
w = 0
for ext in "xls,doc,ppt,txt,ini".split(","):
    v1 = grinedit.addVertex("BoxVertex", {})
    for v2 in vs:
        w += 1
        grinedit.addEdge(
            "BasicStrokeEdge",
            {"v1": v1, "v2": v2, "width": w})
    
    vs.append(v1)