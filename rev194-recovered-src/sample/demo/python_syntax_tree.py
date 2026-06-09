code = r"""
def foo(x, y):
    return x + y
"""

import parser, symbol, pprint, token
def code2tree(code):
    def change(ast):
        if type(ast) is list:
            return map(change, ast)
        else:
            if symbol.sym_name.has_key(ast):
                return "S_" + symbol.sym_name[ast]
            elif token.tok_name.has_key(ast):
                return "T_" + token.tok_name[ast]
            else:
                return str(ast)

    return change(parser.ast2list(parser.suite(code)))

tree = code2tree(code)

# connect to GRINEdit
import xmlrpclib
server = xmlrpclib.Server("http://localhost:8080/RPC2")
grinedit = server.grinedit

# initialize graph
grinedit.initGraph()

def walk(tree):
    if tree[0][:2] == "S_":
        v1 = grinedit.addVertex("BoxVertex", {"label": tree[0]})
        for t in tree[1:]:
            v2 = walk(t)
            grinedit.addEdge("TriangleEdge",
                 {"v1": v1, "v2": v2, "length": 1.0})
    else:
        v2 = grinedit.addVertex("BoxVertex", {"label": tree[1], "bgcolor": (200, 100, 100) })
        v1 = grinedit.addVertex("BoxVertex", {"label": tree[0], "bgcolor": (200, 200, 100)})
        grinedit.addEdge("TriangleEdge",
                 {"v1": v1, "v2": v2, "length": 1.0})

    return v1

g.delLaw("PL_SpringEdge")
grinedit.addLaw("PL_Flow", {"ky1": 0.2})
rootVertex = walk(tree)
grinedit.modVertex(rootVertex, {"anchored": (0, -5)})

