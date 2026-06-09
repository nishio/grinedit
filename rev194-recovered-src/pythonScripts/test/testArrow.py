#
# test ArrowEdge
#

import xmlrpclib
server = xmlrpclib.Server('http://localhost:8080/RPC2')
g = server.grinedit

v1 = g.addVertex("BoxVertex", {"label": "Foo"})
v2 = g.addVertex("BoxVertex", {"label": "Bar"})
g.addEdge("ArrowEdge", {"v1": v1, "v2": v2})

v1 = g.addVertex("CircleVertex", {})
v2 = g.addVertex("CircleVertex", {})
g.addEdge("ArrowEdge", {"v1": v1, "v2": v2})
