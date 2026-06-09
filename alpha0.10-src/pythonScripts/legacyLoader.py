##
# Loader for legacy format (samples are in /sampleData/legacy)
#
from org.nishiohirokazu.graph import RenderableGraph

def hex2rgb(h):
	color = eval("0x" + h)
	b = color & 0xFF
	gr = (color >> 8) & 0xFF
	r = color >> 16
	return (r, gr, b)

def load(filename):
	print 'load legacy data'
	g = RenderableGraph()

	fi=open(filename)
	phase='phIdVertex'
	while 1:
		l=fi.readline()
		if l=="": break
		l=l.strip()
		if l=="": continue # ‹ós‚È‚Ì‚Å‰½‚à‚µ‚È‚¢
		if l[0]=="#": continue # ƒRƒƒ“ƒg‚È‚Ì‚Å‰½‚à‚µ‚È‚¢
		if l=="VERTEX":
			if phase=='phIdVertex':
				phase='phVertexNum'
			else:
				raise '%s - syntax error'%filename
		elif phase=='phVertexNum':
			numVertex=int(l)
			vertex = [None] * numVertex
			degree = [0] * numVertex
			phase='phVertexName'
		elif l=='NODE' or l=='EDGE':
			phase='phNode'
		elif phase=='phVertexName':
			sp=l.split()
			if len(sp) == 2:
				vertex[int(sp[0])-1]=g.addVertex(sp[1])
			elif len(sp) == 3:
				(r, gr, b) = hex2rgb(sp[2])
				vertex[int(sp[0])-1]=g.addVertex(sp[1], r, gr, b)
			else:
				print "Warning: unrecognizable line", repr(l)
		elif phase=='phNode':
			sp = l.split("\t")
			if len(sp) > 1:
				i=int(sp[0])-1
				j=int(sp[1])-1
				degree[i] += 1
				degree[j] += 1
				if vertex[i]==None:
					vertex[i]=g.addVertex()
				if vertex[j]==None:
					vertex[j]=g.addVertex()
				if len(sp) > 2:
					# "+" and "-" are not recommended
					if sp[2] == "+":
						g.addEdge(vertex[i], vertex[j], 255, 0, 0)
					elif sp[2] == "-":
						g.addEdge(vertex[i], vertex[j], 0, 255, 0)
					else:
						(r, gr, b) = hex2rgb(sp[2])
						g.addEdge(vertex[i], vertex[j], r, gr, b)
				else:
					g.addEdge(vertex[i], vertex[j])
			else:
				print "Warning: unrecognizable line", repr(l)
		else:
			raise '%s - syntax error'%filename

	fi.close()
	med.setGraph(g)
	med.setDefaultSpringStrength(1.0 / max(degree))
