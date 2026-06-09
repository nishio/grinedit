#
# calc density of selected subgraph
#

n = med.getSelectedVertex().size()
e = med.getSelectedEdge().size()

print float(e) / n * (n - 1)