#
# init.py
# This script is executed at first (after config.py loaded)

#
# set path
import sys
import org.nishiohirokazu.grinEdit.Infrastructure as i
sys.path.append(i.SCRIPT_PATH)
sys.path.append(JYTHON_LIB)

#
# relay standard output/error to Java
class Tee:
    def __init__(self, python, java):
        self.python = python
        self.java = java
    def write(self, s):
        self.python.write(s)
        self.java.print(s)

import java.lang.System as System

sys.stdout = Tee(sys.stdout, System.out)
sys.stderr = Tee(sys.stderr, System.err)

#
# get Mediator instance
from org.nishiohirokazu.grinEdit import Mediator
med = Mediator.getInstance()

#
# get gettext util. 
import i18n
gettext = i18n.myGettext
gettext.setLang(LANG)
(_, N_) = gettext.getFuncs()

#
# make variables which are accessible anywhere
import __builtin__
__builtin__.__dict__['_'] = _
__builtin__.__dict__['N_'] = N_
__builtin__.__dict__['gettext'] = gettext
__builtin__.__dict__['med'] = med
__builtin__.__dict__['grinedit'] = med.getCommonGateway()
__builtin__.__dict__["SWT"] = med.loadClass("org.eclipse.swt.SWT")
__builtin__.__dict__["DEFAULT_PHYSICAL_LAWS"] = DEFAULT_PHYSICAL_LAWS
__builtin__.__dict__["STATIC_LAYOUT_ON_LOAD"] = [
    med.loadClass(classname)() for classname in STATIC_LAYOUT_ON_LOAD
]

#
# run XML-RPC server
if USE_XML_RPC:
    WebServer = med.loadClass("org.apache.xmlrpc.WebServer")
    server = WebServer(XML_RPC_PORT)
    server.addHandler("grinedit", grinedit)
    server.start()
    med.setServer(server)
    
#
# set renderer and layout-engine
med.setRenderer(RENDERER)
med.setLayoutEngine(LAYOUT_ENGINE)

