
package org.nishiohirokazu.graph;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.program.Program;
import org.nishiohirokazu.grinEdit.Infrastructure;

public class FileIconVertex extends RenderableVertex{
	private Image icon;
	private String extension;
	private int w;
	private int h;
	
	public Hashtable<String, Object> getParams() {
		Hashtable<String, Object> result = super.getParams();
		result.put("extension", extension);
		return result;
	}
	
	public void setExtension(Object o){
		extension = o.toString();
		updateIcon();
	}

	private void updateIcon() {
		Program program = Program.findProgram(extension);
		ImageData imageData = program.getImageData();
		int transparentColor = imageData.getPixel(0, 0);
		BufferedImage tmpImage = mask(toAwtImage(imageData), transparentColor);
		icon = tmpImage.getScaledInstance(w * 2, h * 2, Image.SCALE_REPLICATE);
	}

	BufferedImage toAwtImage(ImageData imageData){
		BufferedImage result = null;
	    ImageLoader il = new ImageLoader();
	    il.data = new ImageData[] {imageData};
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    il.save(bos, SWT.IMAGE_BMP);
	    ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
	    try {
			result = ImageIO.read(bis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	BufferedImage mask(BufferedImage org, int transparentColor){
		w = org.getWidth();
		h = org.getHeight();
		BufferedImage dst = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				if(x == 0 && y == 0){
				}
				if (org.getRGB(x, y) % 16777216 == transparentColor) {
					dst.setRGB(x, y, 0);
				} else {
					dst.setRGB(x, y, org.getRGB(x, y));
				}
			}
		}
		return dst;
	}


	public void render(Object target) {
		Graphics g = (Graphics) target;
		
		g.drawImage(icon, 
				(int)screenPos[0] - w,
				(int)screenPos[1] - h,
				Infrastructure.canvas);
	}
}
