package org.nishiohirokazu.narVisualizer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Hashtable;

import org.nishiohirokazu.awt.ColorHolder;
import org.nishiohirokazu.graph.RenderableEdge;
import org.nishiohirokazu.graph.RenderableVertex;
import org.nishiohirokazu.grinEdit.UtilCast;

public class TarminalEdge extends RenderableEdge {

	private Color color;
	/**
	 * @param bg
	 */
	public TarminalEdge() {
		color = Color.BLACK;
	}
	public TarminalEdge(RenderableVertex v1, RenderableVertex v2) {
		super(v1, v2);
		color = Color.BLACK;
	}

	public TarminalEdge(RenderableVertex v1, RenderableVertex v2, int r, int g, int b) {
		super(v1, v2);
		color = ColorHolder.get(r, g, b);
	}

	public void render(Object target) {
		double[] p1=v1.getTerminal(this.id), p2=v2.getTerminal(this.id);
		if(p1 == null || p2 == null){
			return;
		}
		
		
		Graphics g = (Graphics) target;
		if(selected){
			g.setColor(ColorHolder.SELECTED_EDGE);
		}else{
			g.setColor(color);
		}

		g.drawLine(
				(int)p1[0],(int)p1[1],
				(int)p2[0],(int)p2[1]);

	}
	public void setColor(int r, int g, int b) {
		color = ColorHolder.get(r, g, b);
	}
	
	public void setColor(Object rgb){
		color = UtilCast.o2awtColor(rgb);
	}

	public Hashtable getParams(){
		Hashtable result = super.getParams();
		result.put("color", UtilCast.Color2Vector(color));
		return result;
	}

}
