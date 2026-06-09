#
# initialize menu
#
print "initMenu.py"

import org.nishiohirokazu.grinEdit.menuAction as ma
mw=med.getMenuWrapper()

from org.eclipse.swt import SWT
def accelerate(menu, i):
	menu.setAccelerator(i)
	doc = "\t"

	if i & SWT.SHIFT:
		doc += "Shift+"
		i -= SWT.SHIFT

	if i & SWT.CTRL:
		doc += "Ctrl+"
		i -= SWT.CTRL
	
	if i == SWT.F5:
		doc += "F5"
	else:
		doc += chr(i)

	menu.setText(menu.getText() + doc)

mw.addCascade(_('File'))

import legacyLoader
accelerate(
mw.addPush(_("Load LegacyFormat Data"), 
	ma.MA_Load(med, legacyLoader.load))
		,SWT.CTRL + ord("O"))
		
import pairedLoader
mw.addPush(_("Load PairedFormat Data"), 
	ma.MA_Load(med, pairedLoader.load))

mw.addPush(_("Load from JSON"), 
	ma.MA_LoadJSON())

mw.addPush(_("Save as JSON"), 
	ma.MA_SaveAsJSON())

mw.addSeparator()
mw.addPush(_("Run Script"), ma.MA_RunScript(med))

def func():
	print sys.path
	from jythonconsole import console
	console.runWithNamespace(globals())
		
m = ma.PyMenuAction(func)
mw.addPush(_("Open Jython Console"), m)

mw.addPush(_("Load Background Image"), ma.MA_LoadBGImage(med))
mw.addPush(_("Capture"), ma.MA_Capture(med))

#
# "edit" menu
import menu.edit

def func():
	from org.nishiohirokazu.grinEdit import InputBox
	dss = med.getDefaultSpringStrength
	v = InputBox.openEvaluable(
		_("set default spring strength"), 
		_("Input the default spring strength."), str(dss))
	if v:
		med.setDefaultSpringStrength = float(v)
		
m = ma.PyMenuAction(func)
mw.addPush(_("set default spring strength"), m)


#
# Mouse Setting

mm=med.getMouseMediator()
mw.addCascade(_("Mouse[&L]"))
for name in med.getMouseOperationNames():
	mo=mm.get(name)
	mw.addRadio(
		name,
		ma.MA_SetLeftMO(mm, mo)
    )

mw.addCascade(_("Mouse[&R]"))
for name in [_("&Translation"), _("&Scaling")]:
	mo=mm.get(name)
	mw.addRadio(
		name,
		ma.MA_SetRightMO(mm, mo)
    )


