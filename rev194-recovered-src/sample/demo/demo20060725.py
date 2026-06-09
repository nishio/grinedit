#
# script for demonstration 2006/07/25
#  not to run, to run step by step on console

# connect to GRINEdit
import xmlrpclib
server = xmlrpclib.Server("http://localhost:8080/RPC2")
grinedit = server.grinedit

# initialize graph
grinedit.initGraph()

# add a vertex
top = grinedit.addVertex("CircleVertex", {})

# add another vertex
next = grinedit.addVertex("BoxVertex", {})

# add an edge
grinedit.addEdge("LinearEdge", {"v1": top, "v2": next})

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

# demonstrate "move vertex" and "grouping"
#  put the handle aside of the complete graph

# add a gravity
grinedit.addLaw("PL_Gravity", {})

# add a wall constrain
wall = grinedit.addLaw("PL_Wall", {})

# demonstrate the wall constrain

# delete the wall constrain
grinedit.delLaw(wall)

# add a cohesion constrain
grinedit.addLaw("PL_Cohesion", {})

# demonstrate the cohesion constrain
