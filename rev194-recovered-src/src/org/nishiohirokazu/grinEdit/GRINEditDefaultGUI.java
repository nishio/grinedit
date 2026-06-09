package org.nishiohirokazu.grinEdit;

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.nishiohirokazu.awt.DoubleBufferer;
import org.nishiohirokazu.grinEdit.mouseOperation.MouseMediator;
import org.nishiohirokazu.swt.ColorHolder;

public class GRINEditDefaultGUI {

	Display display;
	Shell shell;
	private long prevUpdateTime = 0;
	private long updateTime = 1000;
	private int fpsCount = 0;
	private DecimalFormat fpsFormat = new DecimalFormat(" 0.00 frames/sec");
	private Timer timer;

	public GRINEditDefaultGUI(){
    	display = Infrastructure.getDisplay();

		Mediator med = Mediator.getInstance();
		shell = med.getShell();
	    shell.setLayout(new FillLayout());
		
		Composite comp = new Composite(shell, SWT.EMBEDDED);

		Frame frame = SWT_AWT.new_Frame(comp);
		Canvas canvas = new Canvas();
		frame.add(canvas);
		Infrastructure.canvas = canvas;

		MouseMediator mm = med.getMouseMediator();
		Infrastructure.execPythonScript("initMouseMediator.py");

		canvas.addMouseListener(mm);
		canvas.addMouseMotionListener(mm);

		canvas.addComponentListener(new ComponentListener(){
			public void componentResized(ComponentEvent e) {
				java.awt.Rectangle b = Infrastructure.getCanvas().getBounds();
				Mediator.getInstance().canvasSize = new int[]{b.width, b.height}; 
			}
			public void componentMoved(ComponentEvent e) {}
			public void componentShown(ComponentEvent e) {}
			public void componentHidden(ComponentEvent e) {}
		});

	    Infrastructure.execPythonScript("initMenu.py");

	    shell.pack();
		ColorHolder.initialize(display);
		Infrastructure.execPythonScript("configDefaultGUI.py");

		shell.open();

		acceptDragDrop();
		
		timer = new Timer();
		TimerTask tt = new TimerTask() {
			public void run() {
			    updateScreen();
			}
		};
		updateTime = System.currentTimeMillis();
		timer.schedule(tt, 0, 10);
	}
	
	private void acceptDragDrop() {
		DropTarget dndtarget = new DropTarget(
		        shell, DND.DROP_DEFAULT|DND.DROP_COPY);
		FileTransfer transfer = FileTransfer.getInstance();
		Transfer[] types = new Transfer[]{transfer};
		dndtarget.setTransfer(types);
		dndtarget.addDropListener(new DragDropMediator());
	}
	
	public void start(){
		while (!shell.isDisposed()) {
			try{
				updateTime = System.currentTimeMillis();
				long diff = updateTime - prevUpdateTime;
				if(diff > 1000){
					double fps = 1000.0 * fpsCount / diff; 
					shell.setText(Mediator.VERSION_STR + " - " + fpsFormat.format(fps));
					
					prevUpdateTime = updateTime;
					fpsCount = 0;
				}
	
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}catch(Exception e){
				showErrorDialog(e);
			}
		}
		finish();
	}
	
	private void showErrorDialog(Exception e) {
		JFrame f = new JFrame("Exception");
		f.setSize(600, 200);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JTextArea textArea = new JTextArea();
		e.printStackTrace();
		String msg = e.getLocalizedMessage();
		if (msg == null){
			msg = e.getMessage();
		}
		if (msg == null){
			msg = e.toString();
		}
		textArea.setText(msg);
		
		f.getContentPane().add(new JScrollPane(textArea));
		f.setVisible(true);
	}

	private void finish() {
		timer.cancel();
		display.dispose();
	}
	public void updateScreen() {
		Mediator med = Mediator.getInstance();
		if(shell.isDisposed()){
			med.pause=true;
			ColorHolder.dispose();
	    }else{
	    	DoubleBufferer dbuf = Infrastructure.getDoubleBufferer();
			if(!med.pause){
				if(med.autoLayout){
					med.getLayoutEngine().layoutStep(med.getGraph());
				}
				if(med.rendering){
					dbuf.clearBG();
//	FIXME: îwåiÇ…âÊëúÇï`Ç≠ã@î\Ç™SWTàÀë∂. AWTÇ≈Ç‚ÇÈï˚ñ@Çí≤Ç◊ÇÈÅB					
//					if(Infrastructure.bgImage!=null){
//						dbuf.getBG().drawImage(Infrastructure.bgImage, 0, 0);
//					}
	
					Object bg = dbuf.getBG();
					med.render(bg);
					med.mouseMed.draw();
					dbuf.draw();
				}
		    }
	    }
		fpsCount++;
	
	}
}
