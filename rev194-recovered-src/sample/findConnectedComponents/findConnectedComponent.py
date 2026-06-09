#
# find connected components
#     http://en.wikipedia.org/wiki/Connected_component_(graph_theory)
# ÉOÉâÉtÇÃòAåãê¨ï™ÇãÅÇﬂÇÈ
#

vs = med.getVertexList()
es = med.getEdgeDict().values()

#
# make adjacency dict

adjDict = {}
for v in vs:
    adjDict[v] = []

for e in es:
    v1 = e.getV1()
    v2 = e.getV2()
    adjDict[v1].append(v2)
    adjDict[v2].append(v1)

#
# find components (Breadth-first search)

visited = []
components = []
for v in med.getVertexList():
    if v in visited: # the vertex was already visited
        continue
    aComp = []
    queue = [v]
    while queue != []:
        newQueue = []
        for current in queue:
            visited.append(current)
            aComp.append(current)
            for neighbor in adjDict[current]:
                if not(neighbor in visited) and not(neighbor in newQueue):
                    newQueue.append(neighbor)
        
        queue = newQueue
    
    components.append(aComp)

#
# change color of each component

def hsv2rgb((h, s, v)):
    hi = int(h / 60) % 6
    f = h / 60.0 - hi
    p = 255 * v * (1.0 - s)
    q = 255 * v * (1.0 - f * s)
    t = 255 * v * (1.0 - (1 - f) * s)
    v *= 255
    return [
        (v, t, p),
        (q, v, p),
        (p, v, t),
        (p, q, v),
        (t, p, v),
        (v, p, q)][hi]

n = len(components)
for i in range(n):
    h = 360.0 / n * i
    rgb = hsv2rgb((h, 0.9, 0.9))
    for v in components[i]:
        v.setBackgroundColor(rgb)

print "ok"