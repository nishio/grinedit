package org.nishiohirokazu.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Hashtable;

import org.nishiohirokazu.awt.ColorHolder;
import org.nishiohirokazu.grinEdit.UtilCast;
import org.nishiohirokazu.vector.Vec;

public class TriangleEdge extends RenderableEdge {
	private Color color;
	private double width = 10;
	/**
	 * @param bg
	 */
	public TriangleEdge() {
		color = ColorHolder.BLACK;
	}
	public TriangleEdge(RenderableVertex v1, RenderableVertex v2) {
		super(v1, v2);
		color = ColorHolder.BLACK;
	}

	public TriangleEdge(RenderableVertex v1, RenderableVertex v2, int r, int g, int b) {
		super(v1, v2);
		color = ColorHolder.get(r, g, b);
	}

	public void render(Object target) {
		double[] p1=v1.screenPos, p2=v2.screenPos;
		Graphics gc = (Graphics) target;

		if(selected){
		    gc.setColor(ColorHolder.SELECTED_EDGE);
		}else{
			gc.setColor(color);
		}

		double[] ndir=Vec.normalize(Vec.sub(p1, p2));
		double[] yoko = Vec.rot90(ndir);
		double w = width / 2.0;

		Polygon p = new Polygon();
		p.addPoint((int) p2[0], (int) p2[1]);
		p.addPoint((int) (p1[0] + yoko[0] * w), (int) (p1[1] + yoko[1] * w));
		p.addPoint((int) (p1[0] - yoko[0] * w), (int) (p1[1] - yoko[1] * w));
		gc.fillPolygon(p);

	}

	public void setColor(Object rgb){
		color = UtilCast.o2awtColor(rgb);
	}

	public void setWidth(Object o){
		width = UtilCast.o2double(o);
	}

	public Hashtable getParams(){
		Hashtable result = super.getParams();
		result.put("color", UtilCast.Color2Vector(color));
		result.put("width", Double.valueOf(width));
		return result;
	}

}
