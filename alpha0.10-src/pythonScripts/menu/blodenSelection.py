import org.nishiohirokazu.grinEdit.menuAction as ma
mw = med.getMenuWrapper()

def func():
	import java.util.Vector as Vector
	result = []
	for v in med.getSelectedVertex():
		result.append(v)

	for e in med.getMarginalEdges():
		if not(e.v1 in result):
			result.append(e.v1)
		if not(e.v2 in result):
			result.append(e.v2)

	med.setSelection(list2vector(result))
		
m = ma.PyMenuAction(func)
mw.addPush(_("bloden selection"), m)
