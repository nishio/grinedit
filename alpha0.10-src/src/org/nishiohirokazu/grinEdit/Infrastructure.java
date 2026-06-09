/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/10/07
 *
 */
package org.nishiohirokazu.grinEdit;

import org.apache.xmlrpc.WebServer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.nishiohirokazu.swt.DoubleBufferer;
import org.nishiohirokazu.swt.ViewportTransformer;
import org.python.util.PythonInterpreter;

/**
 * Mediator for Java, not Python.
 * @author nishio
 */
public class Infrastructure {
    static PythonInterpreter pyi;
    private static Display display;
    private static Shell shell;
	static Object drawTarget;
    
    public static Display getDisplay() {
        if(display == null){
            display = new Display();
        }
        return display;
    }

    public static Shell getShell() {
        if(shell == null){
            shell = new Shell(display);
        }
        return shell;
    }

    public static PythonInterpreter getPyi() {
        if(pyi == null){
            pyi = new PythonInterpreter();
            pyi.exec("import __builtin__");
            pyi.exec("__builtin__.__dict__['True'] = 1");
            pyi.exec("__builtin__.__dict__['False'] = 0");
            pyi.execfile("config.py");
		    Infrastructure.execPythonScript("init.py");
        }

        return pyi;
    }

    public static void execPythonScript(String filename){
		try {
			getPyi();
			 
			
			pyi.execfile(pyi.get("SCRIPT_PATH").toString() + "/" + filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Canvas canvas;
	static DoubleBufferer dbuf;
	static ViewportTransformer vp;
	static public Image bgImage;
	public static WebServer server;

	/**
	 * @return
	 */
	public Canvas getCanvas() {
		return canvas;
	}

	public static void setBgImage(String filename) {
		if (filename != null) {
			Infrastructure.bgImage = new Image(Infrastructure.getDisplay(), filename);
		} else {
			Infrastructure.bgImage = null;
		}
	}

	public static ViewportTransformer getViewportTransformer() {
		if(vp == null){
			vp = new ViewportTransformer();
		}
		return vp;
	}

	static public DoubleBufferer getDoubleBufferer() {
		if(dbuf == null){
			dbuf = new DoubleBufferer(canvas);
			dbuf.initBackgroundImage();
			dbuf.drawOnBackground();
			drawTarget = dbuf.getBG();
		}
		return dbuf;
	}

}
