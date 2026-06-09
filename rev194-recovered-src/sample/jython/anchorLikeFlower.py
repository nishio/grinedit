#
# Jython Console Demonstration
#

# paste to Jython console line by line

for v in med.getVertexList():
    if v.getLabel() in ["23", "73", "28", "78"]:
        v.setAnchored((0, 0))

# demonstrate history
for v in med.getVertexList():
    if v.getLabel() in ["23", "73", "28", "78"]:

# type "v." and demonstrate complesion
        v.setDisanchored()

