#
# start.py
# This script is executed when GRINEdit start.

#
# demonstration
#

DO_TEST = False
SHOW_DEMO = False

try:
	DEMO_LEGACY_FILE
except:
	DEMO_LEGACY_FILE = None

if DEMO_LEGACY_FILE:
	import legacyLoader
	print "load demo legacy data", DEMO_LEGACY_FILE
	legacyLoader.load(str(DEMO_LEGACY_FILE))

if DO_TEST:
	import test

if SHOW_DEMO:
	# doesn't work @ alpha1.0
	from org.nishiohirokazu.graph import Graph
	pgw=Graph()
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

	mediator.setGraph(pgw)
