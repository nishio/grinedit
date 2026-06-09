/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/10/07
 *
 */
package org.nishiohirokazu.grinEdit;

import java.awt.Canvas;

import javax.swing.JFrame;

import org.apache.xmlrpc.WebServer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.nishiohirokazu.awt.DoubleBufferer;
import org.nishiohirokazu.swt.ViewportTransformer;
import org.python.util.PythonInterpreter;

import com.artenum.jyconsole.JyConsole;

/**
 * Mediator for Java, not Python.
 * @author nishio
 */
public class Infrastructure {
	public static String SCRIPT_PATH = "pythonScripts";
    static PythonInterpreter pyi;
    private static Display display;
	static Object drawTarget;
	public static JyConsole console;
    
    public static Display getDisplay() {
        if(display == null){
            display = new Display();
        }
        return display;
    }

    public static PythonInterpreter getPyi() {
        if(pyi == null){
//            pyi = new PythonInterpreter();
        	console = new JyConsole();
        	pyi = console.getPythonInterpreter();
        	
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
			pyi.execfile(SCRIPT_PATH + "/" + filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static java.awt.Canvas canvas;
	static DoubleBufferer dbuf;
	static ViewportTransformer vp;
	static public Image bgImage;
	public static WebServer server;
	public static JFrame jyConsoleFrame;

	/**
	 * @return
	 */
	public static Canvas getCanvas() {
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
			dbuf.draw();
			drawTarget = dbuf.getBG();
		}
		return dbuf;
	}

}
