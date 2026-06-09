def func():
	import java.util.Vector as Vector
	result = Vector()
	for v in med.getSelectedVertex():
		result.add(v)

	for e in med.getMarginalEdges():
		if not(e.getV1() in result):
			result.add(e.getV1())
		if not(e.getV2() in result):
			result.add(e.getV2())

	med.setSelection(result)
