# -*- coding: cp932 -*-
import xmlrpclib
server = xmlrpclib.Server("http://localhost:8080/RPC2")
g = server.grinedit

g.initGraph()

existVertex = {
    1: g.addVertex(
        "BoxVertex",
        {"label": "1", "anchored": (0.0, 0.0)})
}

def addVertex(x):
    if not(x in existVertex):
        name = g.addVertex("BoxVertex", {"label": str(x)})
        existVertex[x] = name

    return existVertex[x]

def collatz(x):
    raw_input("...")
    # 次の数を求める
    if x % 2:
        y = x * 3 + 1
    else:
        y = x / 2

    print x, "の次は", y, "です"
    toReturn = y in existVertex
    v1 = addVertex(x)
    v2 = addVertex(y)
    g.addEdge("ArrowEdge", {"v1": v1, "v2": v2})

    if toReturn:
        print y, "はすでに計算済みなので計算を中断します"
        return
    collatz(y)
        
for i in range(2, 11):
    if i in existVertex:
        print i, "はすでに計算済みなのでスキップします"
        continue
    print i, "についての計算を始めます"
    collatz(i)
    raw_input("...")

#g.addLaw("PL_Gravity", {})
