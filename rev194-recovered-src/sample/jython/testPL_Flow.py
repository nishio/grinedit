#
# test PL_Flow
#
g = grinedit
v1 = g.addBoxVertex("1")
v2 = g.addBoxVertex("1-1")
g.addLinearEdge(v1, v2)
v3 = g.addBoxVertex("1-1-1")
g.addLinearEdge(v2, v3)
v3 = g.addBoxVertex("1-1-2")
g.addLinearEdge(v2, v3)
v3 = g.addBoxVertex("1-1-3")
g.addLinearEdge(v2, v3)


# 合流のあるグラフ
#g.addLinearEdge(v1, v3)
# ループのあるグラフ
#g.addLinearEdge(v3, v1)


g.delLaw("PL_SpringEdge")
g.addLaw("PL_Flow", {})