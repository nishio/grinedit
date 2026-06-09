#
# test TerminalVertex and TerminalEdge
#

import xmlrpclib
server = xmlrpclib.Server('http://localhost:8080/RPC2')
g = server.grinedit

v1 = g.addVertex("org.nishiohirokazu.narVisualizer.TerminalVertex", {"label": "Foo"})
v2 = g.addVertex("org.nishiohirokazu.narVisualizer.TerminalVertex", {"label": "Bar"})
g.addEdge("org.nishiohirokazu.narVisualizer.TarminalEdge", {"v1": v1, "v2": v2})
