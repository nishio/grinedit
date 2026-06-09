import org.nishiohirokazu.grinEdit.menuAction as ma
mw = med.getMenuWrapper()

def func():
    for e in med.getSelectedEdges():
        e.setDirected(False)
        
m = ma.PyMenuAction(func)
#mw.addPush(_("make selected edge undirected"), m)
