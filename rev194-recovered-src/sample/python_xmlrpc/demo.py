#
# script for demonstration 2006/07/25
#  please run step by step on Python interactive console
# PythonからXML-RPCでGRINEditを操作するデモ。
#  一度に実行せずIDLEなどから1行ずつ実行してください。


# connect to GRINEdit
import xmlrpclib
server = xmlrpclib.Server("http://localhost:8080/RPC2")
g = server.grinedit

# initialize graph
g.initGraph()

# add a vertex
top = g.addVertex("CircleVertex", {})

# add another vertex
next = g.addVertex("BoxVertex", {})

# add an edge
g.addEdge("LinearEdge", {"v1": top, "v2": next})

# make a complete graph
vs = []
for i in range(5):
    v1 = g.addVertex("BoxVertex", {"label": str(i)})
    for v2 in vs:
        g.addEdge(
            "LinearEdge",
            {"v1": v1, "v2": v2, "length": 2.0})
    vs.append(v1)
    
# connect the complete graph to the handle
g.addEdge("LinearEdge", {"v1": next, "v2": vs[0]})

# demonstrate "move vertex"

# grouping:
#  apply a physical law to the complete graph

g.addLaw("PL_RigidBody", {"target": vs})

# demonstrate and put the handle aside of the complete graph

# add a gravity
g.addLaw("PL_Gravity", {})

# add a wall constrain
wall = g.addLaw("PL_Wall", {})

# demonstrate the wall constrain

# delete the wall constrain
g.delLaw(wall)

# add a cohesion constrain
g.addLaw("PL_Cohesion", {})

# demonstrate the cohesion constrain
