#
# website visualization as graph
# inspired by http://labs.cybozu.co.jp/blog/akky/
#  (original document is http://www.aharef.info/2006/05/websites_as_graphs.htm)

from HTMLParser import HTMLParser
import urllib

# connect to GRINEdit
import xmlrpclib
server = xmlrpclib.Server("http://localhost:8080/RPC2")
grinedit = server.grinedit

# color setting
colors = {
    "a": (0, 0, 200),
    "div": (0, 200, 0),
    "img": (255, 0, 255),
    "html": (0, 0, 0)
}

for tag in "table tr td".split():
    colors[tag] = (255, 0, 0)

for tag in "from imput textarea select option".split():
    colors[tag] = (255, 255, 0)
    
for tag in "br p blockquote".split():
    colors[tag] = (255, 200, 0)

def getColor(tag):
    return colors.get(tag, (200, 200, 200))

# make HTML Parser and start it
class Node:
    def __init__(self, tag, parent):
        self.parent = parent
        params = {"bgcolor": getColor(tag)}
        if parent != None:
            pos = grinedit.getVertex(parent.index)["position"]
            params["position"] = pos

        self.index = grinedit.addVertex("CircleVertex", params)

        if parent != None:
            grinedit.addEdge(
                "LinearEdge",
                {"length": 0.0, "v1": parent.index, "v2":self.index})

    
class MyHTMLParser(HTMLParser):
    def start(self, url):
        data = urllib.urlopen(url).read()

        self.cur = None
        self.feed(data)
        self.close()

    def handle_starttag(self, tag, attrs):
        self.cur = Node(tag, self.cur)
        
    def handle_endtag(self, tag):
        self.cur = self.cur.parent

if __name__ == "__main__":
    grinedit.initGraph()
    grinedit.modLaw("PL_Repulsion", {"repulsionRadius": 10.0})
    MyHTMLParser().start("http://www.google.com/")
    raw_input("hit enter")
    grinedit.delLaw("PL_SpringEdge")
    grinedit.addLaw("PL_Flow", {})
