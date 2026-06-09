#
# Jython Console Demonstration
#

# open "sqr10.txt" from sampleData

# write "med." and press Ctrl +Space
# to demonstrate completion

med.getVertexList()

v = med.getVertexList()[0]

v.getClass()

v.setBackgroundColor((255, 0, 0))

for v in med.getVertexList():
    if v.getLabel() in ["23", "73", "28", "78"]:
        v.setAnchored((0, 0))

# demonstrate history by press UP-key
for v in med.getVertexList():
    if v.getLabel() in ["23", "73", "28", "78"]:
        v.setDisanchored()

