import xmlrpclib
server = xmlrpclib.Server("http://localhost:8080/RPC2")
g = server.grinedit

existVertex = {
    1: g.addVertex("BoxVertex",
                   {"label": "1", "anchored": (0.0, 0.0)})
}

print existVertex
def addVertex(x):
    if not(x in existVertex):
        name = g.addVertex("BoxVertex", {"label": str(x)})
        existVertex[x] = name

    return existVertex[x]

def collatz(x):
    if x == 1:
        return

    if x % 2:
        y = x * 3 + 1
    else:
        y = x / 2

    toContinue = not(y in existVertex)
    v1 = addVertex(x)
    v2 = addVertex(y)
    g.addEdge("LinearEdge", {"v1": v1, "v2": v2})

    if toContinue:
        collatz(y)
        
for i in range(2, 11):
    collatz(i)

g.addLaw("PL_Gravity", {})
