package org.nishiohirokazu.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Hashtable;

import org.nishiohirokazu.awt.ColorHolder;
import org.nishiohirokazu.grinEdit.UtilCast;


public class CircleVertex extends RenderableVertex {
	Color bgcolor;

	Point bound;

	Color frameColor;

	protected int diameter;

	public CircleVertex() {
		bgcolor = ColorHolder.GRINGREEN;
		frameColor = ColorHolder.BLACK;
		diameter = 15;
	}

	public void setBgcolor(Object rgb){
		bgcolor = UtilCast.o2awtColor(rgb);
	}

	public void setFrameColor(Object rgb) {
		frameColor = UtilCast.o2awtColor(rgb);
	}

	public void setDiameter(Object d) {
		diameter = UtilCast.getIntValue(d);
	}

	public Hashtable<String, Object> getParams() {
		Hashtable<String, Object> result = super.getParams();
		result.put("bgcolor", UtilCast.Color2Vector(bgcolor));
		result.put("frameColor", UtilCast.Color2Vector(frameColor));
		result.put("diameter", Integer.valueOf(diameter));
		return result;
	}

	public double calcOffset(double[] ndir){
		return diameter / 2;
	}

	public void render(Object target) {
		Graphics gc = (Graphics) target;
		if (selected) {
			gc.setColor(ColorHolder.SELECTED_VERTEX);
		} else {
			gc.setColor(bgcolor);
		}
		int left = (int) (screenPos[0] - diameter / 2);
		int top = (int) (screenPos[1] - diameter / 2);
		gc.fillOval(left, top, diameter, diameter);
		gc.setColor(frameColor);
		gc.drawOval(left, top, diameter, diameter);
	}
	

}
