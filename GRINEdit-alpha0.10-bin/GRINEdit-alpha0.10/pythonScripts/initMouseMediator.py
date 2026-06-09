#
# Initialize Mouse Mediator
#
print "initMouseMediator.py"

import org.nishiohirokazu.grinEdit.mouseOperation as mo

mm=med.getMouseMediator()
mm.add(_("&Translation"), mo.MO_Translation())
mm.add(_("&Move Vertex"), mo.MO_MoveVertex(True))
mm.add(_("&Move Vertex w/o anchor"), mo.MO_MoveVertex(False))
mm.add(_("Select &Range"), mo.MO_SelectRange())
mm.add(_("&Scaling"), mo.MO_Scaling())

mm.setLeft(_("&Move Vertex"))
mm.setRight(_("&Scaling"))


