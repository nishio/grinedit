


import xmlrpclib
server = xmlrpclib.Server('http://localhost:8080/RPC2')
g = server.grinedit

v1 = g.addVertex(
    "org.nishiohirokazu.narVisualizer.ListlikeVertex",
    {"label": "Foo", "keys": [0, 1, 2]})

c1 = g.addVertex(
    "BoxVertex",
    {"label": "0"})

c2 = g.addVertex(
    "BoxVertex",
    {"label": "1"})

c3 = g.addVertex(
    "BoxVertex",
    {"label": "2"})

g.addEdge("ArrowEdge", {"v1": v1, "v2": c1})
g.addEdge("ArrowEdge", {"v1": v1, "v2": c2})
g.addEdge("ArrowEdge", {"v1": v1, "v2": c3})
