#
# initialize menu
#
print "initMenu.py"

import org.nishiohirokazu.grinEdit.menuAction as ma
#from org.eclipse.swt.widgets import Menu, MenuItem
Menu = med.loadClass("org.eclipse.swt.widgets.Menu")
MenuItem = med.loadClass("org.eclipse.swt.widgets.MenuItem")

class MenuMediator:
	def __init__(self):
		shell = med.getShell()
		bar = Menu(shell, SWT.BAR)
		shell.setMenuBar(bar)
		self.bar = bar


	def addCascade(self, caption, name = None, parentName = "bar"):
		if name == None:
			name = caption

		parent = self.__dict__[parentName]
		item = MenuItem(parent, SWT.CASCADE)
		item.setText(caption)
		lastCascade = Menu(item)
		item.setMenu(lastCascade)
		self.__dict__[name] = lastCascade
		self.lastCascade = lastCascade
		return item
		
	def addMenu(self, caption, listener, accel = "", parentName =  None, type = SWT.PUSH, reflect = False):
		if callable(listener):
			listener = ma.PyMenuAction(listener)
		
		if parentName == None:
			parent = self.lastCascade
		else:
			parent = self.__dict__[parentName]
		
		menu = MenuItem(parent, type)
		menu.setText(caption)
		menu.addSelectionListener(listener);


		if reflect:
			listener.setMenu(menu)
	
		if accel != "":
			intAccel = 0
			for key in accel.split("+"):
				if SWT.__dict__.has_key(key):
					v = SWT.__dict__[key]._doget(SWT)
					intAccel += v
				else:
					intAccel += ord(key)
			
			menu.setAccelerator(intAccel)
			menu.setText(caption + " " + accel)

		return menu

	def addSeparator(self, parentName = None):
		MenuItem(self.lastCascade, SWT.SEPARATOR)


mmed = MenuMediator()

mmed.addCascade(_('File'), "File")

# mw.addCascade(_('File'))

import legacyLoader

mmed.addMenu(
	_("Load LegacyFormat Data"), 
	ma.MA_Load(med, legacyLoader.load))

		
#import pairedLoader
#mmed.addMenu(
#	_("Load PairedFormat Data"), 
#	ma.MA_Load(med, pairedLoader.load))

#mmed.addMenu(
#	_("Load from JSON"), 
#	ma.MA_LoadJSON(),
#	"CTRL+O")
#
#mmed.addMenu(
#	_("Save as JSON"), 
#	ma.MA_SaveAsJSON(),
#	"CTRL+S")

mmed.addSeparator()

mmed.addMenu(
	_("Run Script"),
	ma.MA_RunScript(med),
	"CTRL+R")

mmed.addMenu(
	_("Load Background Image"), 
	ma.MA_LoadBGImage(med))
	
mmed.addMenu(
	_("Capture"), 
	ma.MA_Capture(med))


#
# "edit" menu
mmed.addCascade(_("&Edit"), "Edit")

mmed.addMenu(_("make group selected vertex"), ma.MA_Grouping())

#
# Mouse Setting

if 0:
	mm=med.getMouseMediator()
	mmed.addCascade(_("Mouse[&L]"), "MouseLeft")
	mmed.addCascade(_("Mouse[&R]"), "MouseRight")
	def addMouseMenu(name):
		mo = mm.get(name)
		mmed.addMenu(
			name,
			ma.MA_SetLeftMO(mm, mo),
			type = SWT.RADIO,
			parentName = "MouseLeft"
	    )
		mmed.addMenu(
			name,
			ma.MA_SetRightMO(mm, mo),
			type = SWT.RADIO,
			parentName = "MouseRight"
	    )

	for name in med.getMouseOperationNames():
		addMouseMenu(name)


mm=med.getMouseMediator()
mmed.addCascade(_("Mouse[&L]"), "MouseLeft")
for name in med.getMouseOperationNames():
	mo = mm.get(name)
	mmed.addMenu(
		name,
		ma.MA_SetLeftMO(mm, mo),
		type = SWT.RADIO
    )

mmed.addCascade(_("Mouse[&R]"), "MouseRight")
for name in med.getMouseOperationNames():
	mo = mm.get(name)
	mmed.addMenu(
		name,
		ma.MA_SetRightMO(mm, mo),
		type = SWT.RADIO
    )

__builtin__.__dict__["menuMediator"] = mmed

