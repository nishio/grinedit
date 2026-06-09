/*
 * nishio 2005/02/26
 *
 */
package org.nishiohirokazu.grinEdit;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.nishiohirokazu.grinEdit.mouseOperation.MouseMediator;
import org.nishiohirokazu.swt.ColorHolder;
import org.nishiohirokazu.swt.DoubleBufferer;
import org.python.util.PythonInterpreter;

/**
 * @author nishio
 *  
 */

public class GraphVisualizerTest{
	Display display;

	Shell shell;

	public Mediator med;

    public PythonInterpreter pyi;

    public GraphVisualizerTest() {
	    display = Infrastructure.getDisplay();
	    pyi = Infrastructure.getPyi();
		shell = Infrastructure.getShell();
		
	    med = Mediator.getInstance();
		initPythonScript();
		setContets(shell);
		initMenu(shell);

		shell.pack();
		
		ColorHolder.initialize(display);

		shell.setSize(600,600);
		shell.open();

		Infrastructure.getDoubleBufferer();
		Infrastructure.getViewportTransformer();
		
		String demoLegacyFile = System.getProperty("grinedit.demo.legacy");
		if(demoLegacyFile != null && demoLegacyFile.length() > 0){
			pyi.set("DEMO_LEGACY_FILE", demoLegacyFile);
		}
		Infrastructure.execPythonScript("start.py");
		
		DropTarget dndtarget = new DropTarget(
		        shell, DND.DROP_DEFAULT|DND.DROP_COPY);
		FileTransfer transfer = FileTransfer.getInstance();
		Transfer[] types = new Transfer[]{transfer};
		dndtarget.setTransfer(types);
		dndtarget.addDropListener(new DragDropListener());
		

		Timer t = new Timer();
		TimerTask tt = new TimerTask() {
			public void run() {
			    updateScreen();
			}
		};
		t.schedule(tt, 0, 10);

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		t.cancel();
		display.dispose();
		if(Infrastructure.server != null){
			Infrastructure.server.shutdown();
		}
	}

	/**
     * 
     */
    private void initPythonScript() {
		pyi.exec("import sys");
		pyi.exec("sys.path.append('pythonScripts')");
    }

    /**
	 * @param shell
	 */
	private void initMenu(Shell shell) {
		//MenuWrapper mw=
		Mediator med = Mediator.getInstance();
		med.getMenuWrapper();
	    Infrastructure.execPythonScript("initMenu.py");
	}

	/**
	 *  
	 */
	public void setContets(final Shell shell) {
	    shell.setLayout(new FillLayout());
		shell.setSize(600,600);
		Infrastructure.canvas = new Canvas(shell, SWT.BORDER);
		MouseMediator mm = med.getMouseMediator();
		Infrastructure.execPythonScript("initMouseMediator.py");
		Infrastructure.canvas.addMouseListener(mm);
		Infrastructure.canvas.addMouseMoveListener(mm);

	}

	public static void main(String[] args) {
		new GraphVisualizerTest();
	}


	public void updateScreen() {
	    if(shell.isDisposed()){
			med.pause=true;
			ColorHolder.dispose();
	    }else{
	    	DoubleBufferer dbuf = Infrastructure.getDoubleBufferer();
			if(!med.pause){
				dbuf.clearBG();
				if(Infrastructure.bgImage!=null){
					dbuf.getBG().drawImage(Infrastructure.bgImage, 0, 0);
				}
				med.graph.layoutStep();

				GC bg = dbuf.getBG();
				med.graph.render(bg);
		    }
			med.mouseMed.draw();
			dbuf.draw();
	    }
	}
}
