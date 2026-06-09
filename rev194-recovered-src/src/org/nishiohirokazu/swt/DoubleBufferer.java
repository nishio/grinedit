/*
 * nishio 2005/02/26
 *
 */
package org.nishiohirokazu.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Canvas;

/**
 * @author nishio
 *
 */
public class DoubleBufferer implements IDrawable{
	private Image bgImage;
	protected GC gc,bg;
	int width, height;
    public Canvas canvas;
	

    public DoubleBufferer(Canvas canvas) {
        setCanvas(canvas);
    }
    public void setCanvas(Canvas canvas){
	    this.canvas = canvas;
		//canvas.addPaintListener(new DrawOnPaint(this));
		canvas.addControlListener(new ResizeListener(this));
		gc = new GC(canvas);
	}
	public void initBackgroundImage(){
	    if(canvas.getClientArea().width != 0){
			bgImage = new Image(canvas.getDisplay(), canvas.getClientArea());
			bg=new GC(bgImage);
	    }
	}

	public void draw() {
		if(bgImage==null){
			initBackgroundImage();
		}
		synchronized (gc) {
			gc.drawImage(bgImage, 0, 0);
		}
	}
	public void drawOnBackground() {
		// write drawing code here 
	}


	protected void finalize() throws Throwable {
		super.finalize();
		bgImage.dispose();
		gc.dispose();
		bg.dispose();
	}
	/**
	 * @return bg ÇñﬂÇµÇ‹Ç∑ÅB
	 */
	public GC getBG() {
		return bg;
	}
	/**
	 * @return gc ÇñﬂÇµÇ‹Ç∑ÅB
	 */
	public GC getGC() {
		return gc;
	}
	/**
	 * 
	 */
	public void clearBG() {
		bg.setForeground(new Color(null, 255,255,255));
		bg.fillRectangle(bgImage.getBounds());
		bg.setForeground(new Color(null, 0,0,0));
	}
	
	public void saveImage(String filename){
	    ImageLoader il = new ImageLoader();
	    il.data = new ImageData[] {bgImage.getImageData()};
	    il.save(filename, SWT.BITMAP);	    
	}
	
}
