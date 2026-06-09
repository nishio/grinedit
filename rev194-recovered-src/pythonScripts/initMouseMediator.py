#
# Initialize Mouse Mediator
#
print "initMouseMediator.py"

import org.nishiohirokazu.grinEdit.mouseOperation as mo

mm=med.getMouseMediator()
#mm.add(_("&Translation"), mo.MO_Translation())
mm.add(_("&Move Vertex"), mo.MO_MoveVertex())
mm.add(_("Select &Range"), mo.MO_SelectRange())
#mm.add(_("&Scaling"), mo.MO_Scaling())
mm.add(_("&Translate/Scale"), mo.MO_TranslationScaling())


class MO_OpenMeadowWhenDoubleClicked(mo.MouseOperation):
    def mouseDoubleClick(self, x, y, i):
        target = med.getNearestVertex(x, y);
        if target:
            label = target.getParams().get("label")
            print label
            self.openMeadow(target.getId(), label)
    
    def openMeadow(self, vertexId, contents):
        import os
        sexp = r'(switch-to-buffer \"grinedit\")'
        sexp += r'(erase-buffer)'
        sexp += r'(insert \"%s\n\")' % vertexId
        s = contents.replace("\n", r"\n")
        sexp += r'(insert \"%s\")' % s
        cmd = "gnudoit -F \"%s\"" % sexp
        print cmd
        os.system(cmd)

mm.add(_("Test_OpenMeadow"), MO_OpenMeadowWhenDoubleClicked())
    


mm.setLeft(_("&Move Vertex"))
mm.setRight(_("&Translate/Scale"))

__builtin__.__dict__["mouseMediator"] = mm

