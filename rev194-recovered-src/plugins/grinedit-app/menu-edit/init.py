import sys
sys.path.append("plugins/grinedit-app/menu-edit")

from blodenSelection import func
menuMediator.addMenu(_("bloden selection"), func, parentName = "Edit")

from selectWithCaption import func
menuMediator.addMenu(_("select vertex with its caption"), func, parentName = "Edit")
