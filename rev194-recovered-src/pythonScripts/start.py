#
# start.py
# This script is executed when GRINEdit start.

#
# demonstration
#


#
# boot Plugins

import os
def func(args, root, items):
    for f in items:
        if f == "init.py":
            if root.find("__ignore__") >= 0:
                print "ignored plugin:", root
            else:
                print "processing plugin:", root
                oldDir = os.getcwd()
                execfile(os.path.join(root, f), {})

            continue

os.path.walk("plugins", func, None)

#
# close splash

print "CLOSE_SPLASH"


DO_TEST = False
SHOW_DEMO = False

# TODO: PyUnit based test
#import test.nestableVertexTest
#test.nestableVertexTest.test()

if DO_TEST:
	import test
	if DO_TEST == "ALL":
		test.do_allTest()
	else:
		test.do_test(DO_TEST)

if SHOW_DEMO:
	# doesn't work @ alpha1.0
	from org.nishiohirokazu.graph import RenderableGraph
	pgw=RenderableGraph()
	data="""
		,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
		,GGGGGG..RRRRRR..II..NN...NN,
		,GGGGGG..RRRRRR..II..NNN..NN,
		,GG......RR..RR..II..NNNN.NN,
		,GG..GG..RRRRRR..II..NNNNNNN,
		,GG..GG..RRRRR...II..NN.NNNN,
		,GGGGGG..RR.RRR..II..NN..NNN,
		,GGGGGG..RR..RR..II..NN...NN,
		,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
	"""
	matrix=data.strip().split("\n")
	dict={}
	for y in range(len(matrix)):
		line=matrix[y].strip()
		for x in range(len(line)):
			if line[x]!=".":
				if line[x]==",":
					v=pgw.addVertex()
				else:
					v=pgw.addVertex(line[x])
				dict[(y,x)]=v
				if dict.has_key((y-1,x)):
					pgw.addEdge(v, dict[(y-1,x)])
				if dict.has_key((y,x-1)):
					pgw.addEdge(v, dict[(y,x-1)])

	med.setGraph(pgw)
