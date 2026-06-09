package org.nishiohirokazu.graph;

import java.awt.Graphics;
import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.nishiohirokazu.grinEdit.UtilXMLRPC;
import org.nishiohirokazu.swt.ColorHolder;
import org.nishiohirokazu.vector.Vec;

public class LinearEdge extends RenderableEdge {

	private Color color;
	/**
	 * @param bg
	 */
	public LinearEdge() {
		color = ColorHolder.BLACK;
	};
	public LinearEdge(RenderableVertex v1, RenderableVertex v2) {
		super(v1, v2);
		color = ColorHolder.BLACK;
	}

	public LinearEdge(RenderableVertex v1, RenderableVertex v2, int r, int g, int b) {
		super(v1, v2);
		color = ColorHolder.get(r, g, b);
	}

	public void render(Object target) {
		double[] p1=v1.screenPos, p2=v2.screenPos;
		if(target.getClass() == GC.class){
			GC gc = (GC) target;
	
			if(selected){
			    gc.setForeground(ColorHolder.RED);
			}else{
				gc.setForeground(color);
			}
			gc.drawLine(
					(int)p1[0],(int)p1[1],
					(int)p2[0],(int)p2[1]);
			if(directed){
				double[] ndir=Vec.normalize(Vec.sub(p1, p2));
				
				double offset = v2.calcOffset(ndir);
		
				double[] end=Vec.add(v2.screenPos, Vec.scale(ndir, offset));
				Arrow.draw(gc, end, ndir);
			}
		}else if(target instanceof Graphics){
			// TODO: selected‚Å‚Ì•ªŠò
			Graphics g = (Graphics) target;
			g.drawLine(
					(int)p1[0],(int)p1[1],
					(int)p2[0],(int)p2[1]);

			// TODO: Arrow
			
		}
	}
	public void setColor(int r, int g, int b) {
		color = ColorHolder.get(r, g, b);
	}
	
	public void rpc_color(Object rgb){
		color = ColorHolder.get((Vector) rgb);
	}

	public Hashtable rpc_getParams(){
		Hashtable result = super.rpc_getParams();
		result.put("color", UtilXMLRPC.Color2Vector(color));
		return result;
	}

}
