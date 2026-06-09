# XML-RPC
# test for rigidbody

import xmlrpclib
server = xmlrpclib.Server("http://localhost:8080/RPC2")
grinedit = server.grinedit

# initialize graph
grinedit.initGraph()


# add another vertex
next = grinedit.addVertex("BoxVertex", {})


# make a complete graph
vs = []
for i in range(5):
    v1 = grinedit.addVertex("BoxVertex", {"label": str(i)})
    for v2 in vs:
        grinedit.addEdge("LinearEdge",
                         {"v1": v1, "v2": v2, "length": 2.0})
        
    vs.append(v1)
    
# connect the complete graph to the handle
grinedit.addEdge("LinearEdge", {"v1": next, "v2": vs[0]})
grinedit.addLaw("PL_RigidBody", {"target": vs})

