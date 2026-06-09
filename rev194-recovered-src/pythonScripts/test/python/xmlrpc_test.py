import unittest

class XMLRPCTestCase(unittest.TestCase):
    def setUp(self):
        import xmlrpclib
        self.server = xmlrpclib.Server('http://localhost:8080/RPC2')
        self.grinedit = self.server.grinedit
        
    def testAddVertexAndEdge(self):
        "simple test"
        g = self.grinedit

        v1 = g.addVertex("BoxVertex", {"label": "Hello"})
        v2 = g.addVertex("CircleVertex", {})
        g.addEdge("LinearEdge", {"v1": v1, "v2": v2})
    
    def testBatch(self):
        self.grinedit.batch([
            {"method": "addVertex", "params": ["CircleVertex", {}]},
            {"method": "addVertex", "params": ["CircleVertex", {}]},
            {"method": "addVertex", "params": ["CircleVertex", {}]}])
            

def suite():
    return unittest.makeSuite(XMLRPCTestCase)

if __name__ == "__main__":
    unittest.main()
