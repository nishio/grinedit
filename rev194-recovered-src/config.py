#
# local setting (for each user)
#

##
# language settings

#LANG = "en" # English
LANG = "ja" # Japanese

##
# jython settings

# set jython library path
JYTHON_LIB = r"Lib\jythonLib\Lib"

# If you installed jython and set its location to this variable,
# you can remove files in "Lib\jythonLib\Lib"
#
#JYTHON_LIB = r'c:\jython-2.1\Lib'


##
# XML-RPC settings

# run XML-RPC server?
USE_XML_RPC = True
XML_RPC_PORT = 8080

##
# other settings

RENDERER = "org.nishiohirokazu.grinEdit.DefaultRenderer"

LAYOUT_ENGINE = "org.nishiohirokazu.grinEdit.SimpleLayout"

DEFAULT_PHYSICAL_LAWS = [
    "PL_SpringEdge",
    "PL_Repulsion",
    "PL_Anchor",
]

STATIC_LAYOUT_ON_LOAD = [
#    "org.nishiohirokazu.grinEdit.SeparateComponentLayout",
]



# end of setting
#
