#
# script to make setter / getter
# alpha

import re

PATTERN = re.compile("(private |protected |public )?(.+) (.+);")

fields = """
	Vector keys;
"""

for line in fields.split("\n"):
    line = line.strip()

    if line == "": continue
    items = line.split()
    typ = items[0]
    name = items[1].strip(";")
    if typ == "Vector":
        print """                
	public void set%s(Object o){
		%s = (Vector) o;
	}""" % (name.capitalize(), name)
