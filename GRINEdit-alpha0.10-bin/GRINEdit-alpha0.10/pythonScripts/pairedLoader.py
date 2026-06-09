#
# load paired data
#

def load(filename):
    from org.nishiohirokazu.graph import Graph
    from org.nishiohirokazu.grinEdit import Mediator

    g = Graph()
    vertexes={}
    edges=[]
    for line in open(filename).readlines():
        items=line.split()
        v1name=items[0]
        v2name=items[1]
        if vertexes.has_key(v1name):
            v1=vertexes[v1name]
        else:
            v1=g.addVertex(v1name)
            vertexes[v1name]=v1
        if vertexes.has_key(v2name):
            v2=vertexes[v2name]
        else:
            v2=g.addVertex(v2name)
            vertexes[v2name]=v2
        if v1==v2:
            v1.setSelfLink(1)
        else:
            if (v1, v2) in edges:
                continue
            if (v2, v1) in edges:
                continue
            g.addEdge(v1, v2)
            edges.append((v1, v2))
    
    Mediator.instance.setGraph(g)