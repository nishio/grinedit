#
# load *.pot file and output template.py
#

import sys
import re
pat = re.compile('"(.*)"\n')

WRITE_COMMENT = False

def loadPot(filename):
    result = []
    comment = ""
    msgid = ""
    msgstr = ""
    phase = "HEADER"
    for line in open(filename).readlines():
        if phase == "HEADER" and line == "\n":
            phase = "BODY"
        elif phase == "BODY" and line[0] == "\n":
            if msgid != "":
                result.append((msgid, msgstr, comment))
            msgid = ""
            msgstr = ""
        elif phase == "BODY" and line[0] == "#":
            comment = line
        elif phase == "BODY" and line[:5] == "msgid":
            m = pat.match(line[6:])
            if msgid != "":
                msgid += "\n"
            msgid += m.groups()[0] 
        elif phase == "BODY" and line[:6] == "msgstr":
            m = pat.match(line[7:])
            if msgstr != "":
                msgstr += "\n"
            msgstr += m.groups()[0] 
    return result

def writeAsMap(filename, map, notUsed = None):
    fo = open(filename, "w")
    fo.write("map = {\n")
    if WRITE_COMMENT:
        for (msgid, msgstr, comment) in map:
            fo.write('\t"%s": "%s", %s' % (msgid, msgstr, comment))
    else:
        for (msgid, msgstr, comment) in map:
            fo.write('\t"%s": "%s",\n' % (msgid, msgstr))
    
    fo.write('\t"":"space for extra information (in future)"\n')
    fo.write("}\n")

    if notUsed != None:
        fo.write("\n") 
        fo.write("notUsed = {\n")
        for k in notUsed.keys():
            fo.write('\t"%s": "%s",\n' % (k, notUsed[k]))
        
        fo.write('\t"": "space for extra information (in future)"\n')
        fo.write("}\n")
        

    fo.close()

def merge(map, oldMap):
    result = []
    notUsed = oldMap.copy()
    notUsed.pop("")
    for (msgid, msgstr, comment) in map:
        if oldMap.has_key(msgid):
            result.append((msgid, oldMap[msgid], comment))
            notUsed.pop(msgid)
        else:
            result.append((msgid, msgstr, comment))
    return (result, notUsed)
        
    
filename = sys.argv[1]
result = loadPot(filename)
writeAsMap("template.py", map = result)

import ja
(ja_map, ja_notUsed) = merge(result, ja.map)
writeAsMap("ja_template.py", map = ja_map, notUsed = ja_notUsed)

