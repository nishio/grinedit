#
# script to test interface of XML-RPC
#

# connect to GRINEdit
import xmlrpclib
server = xmlrpclib.Server("http://localhost:8080/RPC2")
grinedit = server.grinedit

# initialize graph
grinedit.initGraph()

# add a vertex
for clsname in ["CircleVertex", "BoxVertex"]:
    print clsname
    v = grinedit.addVertex(clsname, {})
    params = grinedit.getVertex(v)
    grinedit.modVertex(v, params)

# add an edge    
for clsname in ["LinearEdge", "TriangleEdge"]:
    print clsname
    e = grinedit.addEdge(clsname, {"v1": v, "v2": v})
    params = grinedit.getEdge(e)
    grinedit.modEdge(e, params)
    
# add a law
laws = """
PL_Gravity PL_Repulsion PL_RigidBody
PL_Anchor PL_Cohesion PL_Inertia PL_RigidBody
PL_SpringEdge PL_Wall""".strip().split()

for clsname in laws:
    print clsname
    l = grinedit.addLaw(clsname, {})
    params = grinedit.getLaw(l)
    grinedit.modLaw(l, params)
    
