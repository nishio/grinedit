import xmlrpclib
server = xmlrpclib.Server('http://localhost:8080/RPC2')

v1 = server.grinedit.addVertex("AnotherSamplePluginVertex", {})
v2 = server.grinedit.addVertex("org.nishiohirokazu.sample_grinedit_plugin.SamplePluginVertex", {})

server.grinedit.addEdge("LinearEdge", {})
