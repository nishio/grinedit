//package org.nishiohirokazu.graph;
//
//import java.util.List;
//import java.util.Vector;
//
//import org.eclipse.swt.graphics.GC;
//import org.eclipse.swt.graphics.Point;
//import org.nishiohirokazu.grinEdit.Infrastructure;
//import org.nishiohirokazu.swt.ColorHolder;
//import org.nishiohirokazu.swt.ViewportTransformer;
//
///**
// * ì¸ÇÍéqÇ…èoóàÇÈí∏ì_
// * @author nishio
// *
// */
//public class NestableVertex extends BoxVertex_SWT2{
//	private List<NestableVertex> children = new Vector();
//	private Point selfSize;
//	public NestableVertex parent = null;
//	
//	public void addChild(NestableVertex v){
//		children.add(v);
//		v.parent = this;
//		size = null;
//	}
//
//	public NestableVertex() {
//		super();
//	}
//
//	
//	public void render(Object target){
//		GC gc = (GC) target;
//		if(size == null || bound == null){
//			if(label!=""){
//				size = gc.textExtent(label);
//			}else{
//				size = new Point(0,0);
//			}
//			selfSize = new Point(size.x, size.y);
//			for(NestableVertex v: children){
//				if(v.bound.x > size.x){
//					size.x = v.bound.x; 
//				}
//				size.y += margin + v.bound.y;
//			}
//			bound=new Point(size.x+margin*2, size.y+margin*2);
//		}
//
//		if (hasSelflink) {
//		    drawSelfLink(gc);
//	    }
//		
////		if(selected){
////			gc.setBackground(ColorHolder.SELECTED_VERTEX);
////		}else{
////			gc.setBackground(bgcolor);
////		}
////		gc.fillRectangle(
////				(int) (screenPos[0] - bound.x/2), 
////				(int) (screenPos[1] - bound.y/2), 
////				bound.x,
////				bound.y
////		);
////		gc.setForeground(frameColor);
////		gc.drawRectangle(
////				(int) (screenPos[0] - bound.x/2), 
////				(int) (screenPos[1] - bound.y/2), 
////				bound.x,
////				bound.y
////		);
////
//		
////		double innerLeft = screenPos[0] - size.x / 2;
////		double innerTop = screenPos[1] - size.y / 2;
////		
////		double childTop = innerTop + selfSize.y + margin;
////		for(NestableVertex v: children){
////			double[] b = {v.bound.x, v.bound.y}; 
////			v.screenPos = Vec.add(childpos, Vec.scale(b, 0.5));
////			v.render(gc, innerLeft, childTop);
////			childTop += b[1] + margin; 
////		}
////
////		gc.setBackground(ColorHolder.WHITE);
//		if(parent == null){
//			render(gc, screenPos[0] - bound.x / 2, screenPos[1] - bound.y / 2);
//		}
//	}
//	public void render(GC gc, double left, double top){
//		int ileft = (int) left;
//		int itop = (int) top;
//		
//		if(selected){
//			gc.setBackground(ColorHolder.SELECTED_VERTEX);
//		}else{
//			gc.setBackground(bgcolor);
//		}
//		gc.fillRectangle(ileft, itop, bound.x, bound.y);
//		gc.setForeground(frameColor);
//		gc.drawRectangle(ileft, itop, bound.x, bound.y);
//		gc.setForeground(letterColor);
//		gc.drawString(label, ileft + margin, itop + margin);
//		
//		double innerLeft = ileft + margin;
//		double innerTop = itop + margin;
//		
//		double childTop = innerTop + selfSize.y + margin;
//		
////		bound.x / 2.0, bound.y / 2.0
//		ViewportTransformer vp = Infrastructure.getViewportTransformer();
//		for(NestableVertex v: children){
//			
//			v.render(gc, innerLeft, childTop);
//			v.position =  vp.invViewportTransform(
//					new double[]{innerLeft + v.bound.x / 2.0, childTop + v.bound.y / 2.0}); 
//			childTop += v.bound.y + margin; 
//		}
//
//		gc.setBackground(ColorHolder.WHITE);		
//
//	}
//}
