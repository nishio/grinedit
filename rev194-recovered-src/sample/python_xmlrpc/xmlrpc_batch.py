import xmlrpclib
server = xmlrpclib.Server('http://localhost:8080/RPC2')

print server.grinedit.batch([
    {"method": "addVertex", "params": ["CircleVertex", {}]},
    {"method": "addVertex", "params": ["CircleVertex", {}]},
    {"method": "addVertex", "params": ["CircleVertex", {}]}])