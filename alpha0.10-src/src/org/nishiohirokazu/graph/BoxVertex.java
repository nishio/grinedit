package org.nishiohirokazu.graph;

import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.nishiohirokazu.grinEdit.UtilXMLRPC;
import org.nishiohirokazu.swt.ColorHolder;

public class BoxVertex extends RenderableVertex{

	private Point size;
	int margin=3;
	Color bgcolor;
	Point bound;
	Color frameColor;
	boolean hasSelflink;
	Color letterColor;
	public String label="";
	
	public BoxVertex() {
        bgcolor=ColorHolder.GRINGREEN;
		frameColor=ColorHolder.BLACK;
		letterColor=ColorHolder.BLACK;

	}

	public void rpc_label(Object o){
		label = o.toString();
		bound = null; // to be re-calculated
	}
	public void rpc_bgcolor(Object rgb){
		bgcolor = ColorHolder.get((Vector) rgb);
	}
	public void rpc_frame_color(Object rgb){
		frameColor = ColorHolder.get((Vector) rgb);
	}
	public void rpc_letter_color(Object rgb){
		letterColor = ColorHolder.get((Vector) rgb);
	}
	public void rpc_self_link(Object o){
		hasSelflink = ((Boolean) o).booleanValue();
	}
	public void rpc_bound(Object o){
		bound = UtilXMLRPC.Vector2Point((Vector) o);
	}
	public Hashtable rpc_getParams(){
		Hashtable result = new Hashtable();
		result.put("bgcolor", UtilXMLRPC.Color2Vector(bgcolor));
		result.put("frame_color", UtilXMLRPC.Color2Vector(frameColor));
		result.put("letter_color", UtilXMLRPC.Color2Vector(letterColor));
		result.put("self_link", Boolean.valueOf(hasSelflink));
		result.put("bound", UtilXMLRPC.Point2Vector(bound));
		result.put("label", label);
		return result;
	}
	

	private void drawSelfLink(GC gc){
		int radius=(int)(bound.y/2);
		gc.drawOval(
				(int)(screenPos[0] + bound.x/2 - radius),
				(int)(screenPos[1] - bound.y/2 - radius),
				radius*2,
				radius*2);
		double[] end={(screenPos[0] + bound.x/2 - radius),
				(screenPos[1] - bound.y/2)};
		double[] ndir={0.312,-0.95};
		Arrow.draw(gc, end, ndir);
	}

	public void render(Object target){
		GC gc = (GC) target;
		if(bound==null){
			if(label!=""){
				size=gc.textExtent(label);
			}else{
				size=new Point(0,0);
			}
			bound=new Point(size.x+margin*2, size.y+margin*2);
		}
		if (hasSelflink) {
		    drawSelfLink(gc);
	    }
		
		if(selected){
			gc.setBackground(ColorHolder.SELECTED_VERTEX);
		}else{
			gc.setBackground(bgcolor);
		}
		gc.fillRectangle(
				(int) (screenPos[0] - bound.x/2), 
				(int) (screenPos[1] - bound.y/2), 
				bound.x,
				bound.y
		);
		gc.setForeground(frameColor);
		gc.drawRectangle(
				(int) (screenPos[0] - bound.x/2), 
				(int) (screenPos[1] - bound.y/2), 
				bound.x,
				bound.y
		);
		gc.setForeground(letterColor);
		gc.drawString(
				label, 
				(int)(screenPos[0] - size.x/2),
				(int)(screenPos[1] - size.y/2));
		gc.setBackground(ColorHolder.WHITE);		
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
	public String toString() {
		if(label != ""){
			return label;
		}else{
			return super.toString();
		}
	}
}
