/*
 * nishio 2005/02/26
 *
 */
package org.nishiohirokazu.awt;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * ダブルバッファリングを管理するクラス。
 * http://www.javainthebox.net/laboratory/JDK1.4/Graphics/BufferStrategy/BufferStrategy.html
 * を使えばもっと高速にできるようだが、現状で描画は律速段階じゃないので保留。
 * @author nishio
 *
 */
public class DoubleBufferer{
	private Image bgImage;
	int width, height;
    public Canvas canvas;
	private Graphics gc, bg;
	public Color bgcolor = ColorHolder.WHITE;
	public Color fgcolor = ColorHolder.BLACK;

    public DoubleBufferer(Canvas canvas) {
        setCanvas(canvas);
    }
    public void setCanvas(Canvas canvas){
	    this.canvas = canvas;

		canvas.addComponentListener(new ComponentListener(){
			public void componentResized(ComponentEvent e) {
				initBackgroundImage();
			}

			public void componentMoved(ComponentEvent e) {
			}

			public void componentShown(ComponentEvent e) {
			}

			public void componentHidden(ComponentEvent e) {
			}
			
		});

		gc = canvas.getGraphics();
	}
	public void initBackgroundImage(){
	    if(canvas.getSize().width != 0){
			bgImage = canvas.createImage(canvas.getWidth(), canvas.getHeight());
			bg = bgImage.getGraphics();
	    }
	}

	public void draw() {
		if(bgImage==null){
			initBackgroundImage();
		}
		synchronized (gc) {
			gc.drawImage(bgImage, 0, 0, canvas);
		}
	}
	public void drawOnBackground() {
		// write drawing code here 
	}


//	protected void finalize() throws Throwable {
//		super.finalize();
//		bgImage.dispose();
//		gc.dispose();
//		bg.dispose();
//	}
	/**
	 * @return bg を戻します。
	 */
	public Graphics getBG() {
		return bg;
	}
	/**
	 * @return gc を戻します。
	 */
	public Graphics getGC() {
		return gc;
	}
	/**
	 * 
	 */
	public void clearBG() {
		if(bg != null){
			bg.setColor(bgcolor);
			bg.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
			bg.setColor(fgcolor);
		}
	}
	
//	public void saveImage(String filename){
//	    ImageLoader il = new ImageLoader();
//	    il.data = new ImageData[] {bgImage.getImageData()};
//	    il.save(filename, SWT.BITMAP);	    
//	}
	
}
