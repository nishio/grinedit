gettext.addMap({
"ja":{
  "SamplePlugin_AddMenu": "メニュー追加プラグイン(SamplePlugin_AddMenu)"
}})

def func():
    print "Hello! It's SamplePlugin_Hello!"

menuMediator.addMenu(
  _("SamplePlugin_AddMenu"),
  func,
  parentName = "File"
)
