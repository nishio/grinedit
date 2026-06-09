gettext.addMap({
"ja":{
  "auto-layout": "©“®®Œ`"
}})

class Menu:
    def __init__(self):
        self.menu = menuMediator.addMenu(
            _("auto-layout"),
            self, parentName = "File",
            type = SWT.CHECK)
        self.menu.selection = True
    def __call__(self):
        med.autoLayout = self.menu.selection

Menu()

