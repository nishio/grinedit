import org.nishiohirokazu.grinEdit.menuAction as ma
MO = med.loadClass(
    "org.nishiohirokazu.grinEdit." + 
    "mouseOperation.MO_RedBackgroundWhenDoubleClicked")

mo = MO()
mouseMediator.add(_("Red(Sample)"), mo)
menuMediator.addMenu(
    _("Red(Sample)"),
   ma.MA_SetLeftMO(mouseMediator, mo),
   type = SWT.RADIO,
   parentName = "MouseLeft")
menuMediator.addMenu(
    _("Red(Sample)"),
   ma.MA_SetRightMO(mouseMediator, mo),
   type = SWT.RADIO,
   parentName = "MouseRight")
