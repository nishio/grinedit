#
# sample of mind-map like system
#

# connect to GRINEdit
import xmlrpclib
g = xmlrpclib.Server("http://localhost:8080/RPC2").grinedit


g.addBoxVertex("AAA")

def chain(frm, new):
    v2 = g.addBoxVertex(new)
    print "add", v2
    for v in g.getObjects("Vertex").values():
        if v["label"] == frm:
            print v["name"]
            g.addLinearEdge(v["name"], v2)


    
