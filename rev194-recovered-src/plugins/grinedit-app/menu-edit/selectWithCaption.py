def func():
	import java.util.Vector as Vector
	from org.nishiohirokazu.grinEdit import InputBox
	key = InputBox.openEvaluable(
		_("Input keyword for search"), 
		_("Input keyword for search"), "")
	result = Vector()
	for v in med.getVertexList():
		if v.label == key:
			result.add(v)

	med.setSelection(result)
