print "test"

import thread
import time


def test_legacyLoader():
    import legacyLoader
    legacyLoader.load("GRINEditAlpha/sampleData/legacy/sqr10.txt");
    time.sleep(1)

def test_legacyLoader2():
    import legacyLoader
    legacyLoader.load("GRINEditAlpha/sampleData/legacy/petersen_colored.txt");
    time.sleep(1)

def test_legacyLoader3():
    import legacyLoader
    legacyLoader.load("GRINEditAlpha/sampleData/legacy/petersen_colored_negaposi.txt");
    time.sleep(1)

def test_legacyLoader3():
    import legacyLoader
    legacyLoader.load("GRINEditAlpha/sampleData/legacy/concentrate.txt");
    time.sleep(1)

def test_legacyLoader():
    import legacyLoader
    legacyLoader.load("GRINEditAlpha/sampleData/legacy/sqr10.txt");
    time.sleep(1)

def test_makeConcentratedGraph():
    from org.nishiohirokazu.graph import RenderableGraph

    g=RenderableGraph()
    center=g.addVertex()
    DEGREE = 1000
    for i in range(DEGREE):
        v = g.addVertex()
        g.addEdge(center, v)
    med.setGraph(g)
    med.setDefaultSpringStrength(1.0 / DEGREE)
    time.sleep(2)

def test_jsonLoad():
    import org.nishiohirokazu.grinEdit.menuAction.MA_LoadJSON as lo
    lo().loadFile("GRINEditAlpha/sampleData/legacy/tmp.txt")


def allTest():
    for g in globals().keys():
        if len(g) > 5 and g[:5] == "test_":
            f = globals()[g]
            print "start", g, f.__doc__ or ""
            f()
            print "end", g

def do_allTest():
    time.sleep(1)
    args = ()
    thread.start_new_thread(allTest, args)

def do_test(names):
    for g in names:
        print g
        f = __import__("test." + g)
        print f
        print "start", g, f.__doc__ or ""
        f()
        print "end", g
        