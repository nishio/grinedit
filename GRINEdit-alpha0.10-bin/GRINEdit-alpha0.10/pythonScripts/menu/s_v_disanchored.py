import org.nishiohirokazu.grinEdit.menuAction as ma
mw = med.getMenuWrapper()

def func():
    for v in med.getSelectedVertex():
        v.anchored = False
        
m = ma.PyMenuAction(func)
#mw.addPush(_("make selected vertex dis-anchored"), m)
