##
# ad hoc Loader
# You can edit this script for ad hoc solution or rough prototyping
#

from org.nishiohirokazu.semistructured.graph import PyGraphWrapper

def load(filename):
    pgw=PyGraphWrapper()

    fi=open(filename)
    vertexes={}
    edges=[]
    for line in fi.readlines():
        items=line.split()
        v1name=items[1]
        v2name=items[3]
        if vertexes.has_key(v1name):
            v1=vertexes[v1name]
        else:
            v1=pgw.addVertex(v1name)
            vertexes[v1name]=v1
        if vertexes.has_key(v2name):
            v2=vertexes[v2name]
        else:
            v2=pgw.addVertex(v2name)
            vertexes[v2name]=v2
        if v1==v2:
            v1.setSelfLink(1)
        else:
            if (v1, v2) in edges:
                continue
            if (v2, v1) in edges:
                continue
            pgw.addEdge(v1, v2)
            edges.append((v1, v2))
    fi.close()
    return pgw


