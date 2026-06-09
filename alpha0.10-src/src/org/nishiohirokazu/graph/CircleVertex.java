package org.nishiohirokazu.graph;

import java.awt.Graphics;
import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.nishiohirokazu.grinEdit.UtilXMLRPC;
import org.nishiohirokazu.swt.ColorHolder;


public class CircleVertex extends RenderableVertex {
	Color bgcolor;
	Point bound;
	Color frameColor;
	private int diameter;
	
	public CircleVertex() {
        bgcolor=ColorHolder.GRINGREEN;
		frameColor=ColorHolder.BLACK;
		diameter = 15;
	}
	public void rpc_bgcolor(Object rgb){
		bgcolor = ColorHolder.get((Vector) rgb);
	}
	public void rpc_frame_color(Object rgb){
		frameColor = ColorHolder.get((Vector) rgb);
	}
	public void rpc_diameter(Object d){
		diameter = UtilXMLRPC.getIntValue(d);
	}
	public Hashtable rpc_getParams(){
		Hashtable result = super.rpc_getParams();
		result.put("bgcolor", UtilXMLRPC.Color2Vector(bgcolor));
		result.put("frame_color", UtilXMLRPC.Color2Vector(frameColor));
		result.put("diameter", Integer.valueOf(diameter));
		return result;
	}
	

	public void render(Object target){
		if(target instanceof GC){
			GC gc = (GC) target;
			if(selected){
				gc.setBackground(ColorHolder.SELECTED_VERTEX);
			}else{
				gc.setBackground(bgcolor);
			}
			gc.fillOval(
					(int) (screenPos[0] - diameter / 2), 
					(int) (screenPos[1] - diameter / 2), 
					diameter + 1,
					diameter + 1
			);
			gc.setForeground(frameColor);
			gc.drawOval(
					(int) (screenPos[0] - diameter / 2), 
					(int) (screenPos[1] - diameter / 2), 
					diameter,
					diameter
			);
			gc.setBackground(ColorHolder.WHITE);
		}else if(target instanceof Graphics){
			Graphics g = (Graphics) target;
			// TODO: êFÇÃawtëŒâû
//			if(selected){
//				gc.setBackground(ColorHolder.SELECTED_VERTEX);
//			}else{
//				gc.setBackground(bgcolor);
//			}
			g.setColor(java.awt.Color.blue);
			g.fillOval(
					(int) (screenPos[0] - diameter / 2), 
					(int) (screenPos[1] - diameter / 2), 
					diameter,
					diameter
			);

//gc.setForeground(frameColor);
			g.setColor(java.awt.Color.black);
			g.drawOval(
					(int) (screenPos[0] - diameter / 2), 
					(int) (screenPos[1] - diameter / 2), 
					diameter,
					diameter
			);
//			gc.setBackground(ColorHolder.WHITE);
		}
		
		
	}

	public double calcOffset(double[] ndir){
		double offset;
		Point b = bound;
		if(b==null || b.x == 0 || b.y == 0){
			offset=0.0;
		}else{
			if(Math.abs(b.x * ndir[1]) < Math.abs(b.y * ndir[0])){
				offset=Math.abs(b.x / ndir[0] / 2);
			}else{
				offset=Math.abs(b.y / ndir[1] / 2);
			}
		}
		return offset;
	}

}
