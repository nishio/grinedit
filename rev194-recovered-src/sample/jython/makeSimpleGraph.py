g = grinedit

g.initGraph()
v1 = g.addVertex("CircleVertex", {})
v2 = g.addVertex("CircleVertex", {"bgcolor": (255,0,0)})
g.addEdge("LinearEdge", {"v1": v1, "v2": v2})

