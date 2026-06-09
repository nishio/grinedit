package org.nishiohirokazu.graph.swt;

import java.util.Hashtable;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.nishiohirokazu.graph.RenderableEdge;
import org.nishiohirokazu.graph.RenderableVertex;
import org.nishiohirokazu.grinEdit.UtilCast;
import org.nishiohirokazu.swt.ColorHolder;
import org.nishiohirokazu.vector.Vec;

public class TriangleEdge_SWT extends RenderableEdge {
	private Color color;
	private double width = 10;
	/**
	 * @param bg
	 */
	public TriangleEdge_SWT() {
		color = ColorHolder.BLACK;
	}
	public TriangleEdge_SWT(RenderableVertex v1, RenderableVertex v2) {
		super(v1, v2);
		color = ColorHolder.BLACK;
	}

	public TriangleEdge_SWT(RenderableVertex v1, RenderableVertex v2, int r, int g, int b) {
		super(v1, v2);
		color = ColorHolder.get(r, g, b);
	}

	public void render(Object target) {
		double[] p1=v1.getScreenPos(), p2=v2.getScreenPos();
		GC gc = (GC) target;

		if(selected){
		    gc.setBackground(ColorHolder.SELECTED_EDGE);
		}else{
			gc.setBackground(color);
		}

		double[] ndir=Vec.normalize(Vec.sub(p1, p2));
		double[] yoko = Vec.rot90(ndir);
		double w = width / 2.0;

		int[] points = {
				(int) p2[0], (int) p2[1],
				(int) (p1[0] + yoko[0] * w), (int) (p1[1] + yoko[1] * w),
				(int) (p1[0] - yoko[0] * w), (int) (p1[1] - yoko[1] * w)
		};
		gc.fillPolygon(points);

	}

	public void setColor(Object rgb){
		color = UtilCast.o2swtColor(rgb);
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
