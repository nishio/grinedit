#
# init.py
# This script is executed at first (after config.py loaded)

# set path
import sys
sys.path.append(SCRIPT_PATH)
sys.path.append(JYTHON_PATH)


# get Mediator instance
from org.nishiohirokazu.grinEdit import Mediator
med = Mediator.getInstance()

# get gettext util. 
import i18n
gettext = i18n.myGettext
gettext.setLang(LANG)
(_, N_) = gettext.getFuncs()

# make variables which are accessible anywhere
import __builtin__
__builtin__.__dict__['_'] = _
__builtin__.__dict__['N_'] = N_
__builtin__.__dict__['med'] = med

# run XML-RPC server
if USE_XML_RPC:
    from org.apache.xmlrpc import WebServer
    
    server = WebServer(8080)
    server.addHandler("grinedit", med.getXMLRPCHandler())
    server.start()
    med.setServer(server)

# utility
def list2vector(aList):
    "transform PyList to java.util.Vector"
    import java.util.Vector as Vector
    result = Vector(len(aList))
    for item in aList:
        result.add(item)
    return result

__builtin__.__dict__['list2vector'] = list2vector
