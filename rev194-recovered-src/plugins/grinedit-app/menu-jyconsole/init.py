gettext.addMap({
"ja":{
  "Open JyConsole": "JyConsole‚ðŠJ‚­"
}})

import org.nishiohirokazu.grinEdit.menuAction.MA_OpenJyConsole as l
menuMediator.addMenu(
  _("Open JyConsole"), l(), parentName = "File")

