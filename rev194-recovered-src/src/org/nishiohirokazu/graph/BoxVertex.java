package org.nishiohirokazu.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Hashtable;
import java.util.Vector;

import org.nishiohirokazu.awt.ColorHolder;
import org.nishiohirokazu.grinEdit.UtilCast;

public class BoxVertex extends RenderableVertex {

	protected Point size;

	protected int margin = 3;

	protected Color bgcolor;

	protected Point bound;

	protected Color frameColor;

	boolean hasSelflink;

	protected Color letterColor;

	public String label = "";

	public BoxVertex() {
		bgcolor = ColorHolder.GRINGREEN;
		frameColor = ColorHolder.BLACK;
		letterColor = ColorHolder.BLACK;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(Object o) {
		label = o.toString();
		size = null; // to be re-calculated
	}

	public void setBgcolor(Object rgb) {
		bgcolor = UtilCast.o2awtColor(rgb);
	}

	public void setBackgroundColor(Object rgb) {
		bgcolor = UtilCast.o2awtColor(rgb);
	}

	public void setFrameColor(Object rgb) {
		frameColor = UtilCast.o2awtColor(rgb);
	}

	public void setLetterColor(Object rgb) {
		letterColor = UtilCast.o2awtColor(rgb);
	}

	public void setSelfLink(Object o) {
		hasSelflink = UtilCast.o2bool(o);
	}

	public void setBound(Object o) {
		bound = UtilCast.o2awtPoint(o);
	}

	public Hashtable<String, Object> getParams() {
		Hashtable<String, Object> result = super.getParams();
		result.put("bgcolor", UtilCast.Color2Vector(bgcolor));
		result.put("frameColor", UtilCast.Color2Vector(frameColor));
		result.put("letterColor", UtilCast.Color2Vector(letterColor));
		result.put("selfLink", Boolean.valueOf(hasSelflink));

		Vector _bound = UtilCast.point2Vector(bound);
		if (_bound != null) {
			result.put("bound", _bound);
		}

		result.put("label", label);
		return result;
	}

	// protected void drawSelfLink(GC gc) {
	// int radius = bound.y / 2;
	// gc.drawOval((int) (screenPos[0] + bound.x / 2 - radius),
	// (int) (screenPos[1] - bound.y / 2 - radius), radius * 2,
	// radius * 2);
	// double[] end = { (screenPos[0] + bound.x / 2 - radius),
	// (screenPos[1] - bound.y / 2) };
	// double[] ndir = { 0.312, -0.95 };
	// Arrow.draw(gc, end, ndir);
	// }

	public void render(Object target) {
		Graphics gc = (Graphics) target;
		if (size == null || bound == null) {
			if (label != "") {
				int x = gc.getFontMetrics().stringWidth(label);
				int y = gc.getFontMetrics().getHeight();
				size = new Point(x, y);
			} else {
				size = new Point(0, 0);
			}
			bound = new Point(size.x + margin * 2, size.y + margin * 2);
		}
		if (hasSelflink) {
			// drawSelfLink(gc);
		}

		if (selected) {
			gc.setColor(ColorHolder.SELECTED_VERTEX);
		} else {
			gc.setColor(bgcolor);
		}

		int left = (int) (screenPos[0] - bound.x / 2);
		int top = (int) (screenPos[1] - bound.y / 2);
		int ascent = gc.getFontMetrics().getAscent();

		gc.fillRect(left, top, bound.x, bound.y);

		gc.setColor(frameColor);
		gc.drawRect(left, top, bound.x, bound.y);

		gc.setColor(letterColor);
		gc.drawString(label, left + margin, top + margin + ascent);

	}

	public double calcOffset(double[] ndir) {
		double offset;
		Point b = bound;
		if (b == null || b.x == 0 || b.y == 0) {
			offset = 0.0;
		} else {
			if (Math.abs(b.x * ndir[1]) < Math.abs(b.y * ndir[0])) {
				offset = Math.abs(b.x / ndir[0] / 2);
			} else {
				offset = Math.abs(b.y / ndir[1] / 2);
			}
		}
		return offset;
	}

	
	public String toString() {
		if (label != "") {
			return label;
		}
		return super.toString();
	}
}
