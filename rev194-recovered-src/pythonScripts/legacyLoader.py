##
# Loader for legacy format (samples are in /sampleData/legacy)
#
from org.nishiohirokazu.grinEdit import Mediator

def hex2rgb(h):
	color = eval("0x" + h)
	b = color & 0xFF
	gr = (color >> 8) & 0xFF
	r = color >> 16
	return (r, gr, b)

def load(filename):
	m = Mediator.getInstance()
	cg = m.getCommonGateway()
	cg.initGraph()

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
				vertex[int(sp[0])-1] = cg.addBoxVertex(sp[1])
			elif len(sp) == 3:
				raise NotImprementedError
				(r, gr, b) = hex2rgb(sp[2])
				vertex[int(sp[0])-1] = cg.addVertex(sp[1], r, gr, b)
			else:
				print "Warning: unrecognizable line", repr(l)
		elif phase=='phNode':
			sp = l.split()
			if len(sp) > 1:
				i=int(sp[0])-1
				j=int(sp[1])-1
				degree[i] += 1
				degree[j] += 1
				if vertex[i] == None:
					vertex[i] = cg.addBoxVertex("")
				if vertex[j]==None:
					vertex[j] = cg.addBoxVertex("")
				if len(sp) > 2:
					# "+" and "-" are not recommended
					if sp[2] == "+":
						cg.addEdge("LinearEdge", 
								   {"v1": vertex[i],
								    "v2": vertex[j],
								    "color": (255, 0, 0)})
					elif sp[2] == "-":
						cg.addEdge("LinearEdge", 
								   {"v1": vertex[i],
								    "v2": vertex[j],
								    "color": (0, 255, 0)})
					else:
						(r, gr, b) = hex2rgb(sp[2])
						cg.addEdge("LinearEdge", 
								   {"v1": vertex[i],
								    "v2": vertex[j],
								    "color": (r, gr, b)})
				else:
					cg.addLinearEdge(vertex[i], vertex[j])
			else:
				print "Warning: unrecognizable line", repr(l)
		else:
			raise '%s - syntax error'%filename

	fi.close()
	print max(degree)
	med.setDefaultSpringStrength(1.0 / max(degree))
