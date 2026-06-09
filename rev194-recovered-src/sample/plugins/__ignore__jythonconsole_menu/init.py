gettext.addMap({
"ja":{
  "Open Jython Console": "Jython Console‚ðŠJ‚­"
}})

def func():
	import sys
	sys.path.append("plugins/jythonconsole_menu")
	from jythonconsole import console
	console.runWithNamespace(globals())

menuMediator.addMenu(
	_("Open Jython Console"),
	func, parentName = "File")
