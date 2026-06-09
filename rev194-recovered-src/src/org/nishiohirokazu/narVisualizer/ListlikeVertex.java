///**
// * $Id$
// * @version $Revision$
// * @author Nishio Hirokazu
// * 2005/07/27
// *
// */
//package org.nishiohirokazu.narVisualizer;
//
//import java.awt.Graphics2D;
//import java.awt.Point;
//import java.util.Vector;
//
//import org.nishiohirokazu.awt.ColorHolder;
//import org.nishiohirokazu.graph.BoxVertex;
//
///**
// * 
// * @author nishio
// */
//public class ListlikeVertex extends BoxVertex implements IHasTerminal {
//	Vector keys;
//	private Vector targets;
//	public void setKeys(Object o){
//		keys = (Vector) o;
//	}
//	
//	@Override
//	public void render(Object renderTarget) {
//		Graphics2D g = (Graphics2D) renderTarget;
//
//		
//		if (size == null || bound == null) {
//			if (label != "") {
//				int x = g.getFontMetrics().stringWidth(label);
//				int y = g.getFontMetrics().getHeight();
//				size = new Point(x, y);
//			} else {
//				size = new Point(0, 0);
//			}
//			bound = new Point(size.x + margin * 2, size.y + margin * 2);
//		}
//
//		if (selected) {
//			g.setColor(ColorHolder.SELECTED_VERTEX);
//		} else {
//			g.setColor(bgcolor);
//		}
//
//		int left = (int) (screenPos[0] - bound.x / 2);
//		int top = (int) (screenPos[1] - bound.y / 2);
//		int ascent = g.getFontMetrics().getAscent();
//
//		g.fillRect(left, top, bound.x, bound.y);
//
//		g.setColor(frameColor);
//		g.drawRect(left, top, bound.x, bound.y);
//
//		g.setColor(letterColor);
//		g.drawString(label, left + margin, top + margin + ascent);
//		
//
//		int arrayTop = (int) (screenPos[1] + bound.y / 2);
//
//		for(int i = 0; i < keys.size(); i++){
//			
////			String targetName = targets.get(i).toString();
////			Object target = Mediator.getInstance().getObject(targetName);
//			
////			if (target.bound != null) {
//				int boxTop = arrayTop + bound.y * i;
//				g.setColor(frameColor);
//				g.drawRect(left, boxTop, bound.x, bound.y);
//
//				String key = keys.get(i).toString();
//				
//				g.setColor(letterColor);
//				g.drawString(key, left + margin,
//						boxTop + g.getFontMetrics().getAscent() + margin);
//
//				
//				
//				
//				int startx = (int) (screenPos[0] + bound.x / 2);
//				int starty = (int) boxTop + 5;
////				int endx = (int) (target.screenPos[0] - target.bound.x / 2);
////				int endy = (int) target.screenPos[1];
////				g.drawLine(startx, starty, endx, endy);
//
////				double[] p1 = { startx, starty };
////				double[] p2 = { endx, endy };
////				double[] ndir = Vec.normalize(Vec.sub(p1, p2));
////
////				Arrow.draw(gc, p2, ndir);
////			}
//
//		}
//	}
//
//}
