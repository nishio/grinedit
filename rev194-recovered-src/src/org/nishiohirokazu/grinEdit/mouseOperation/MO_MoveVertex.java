/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/03/25
 *
 */
package org.nishiohirokazu.grinEdit.mouseOperation;

import java.util.HashMap;
import java.util.Map;

import org.nishiohirokazu.graph.Graph;
import org.nishiohirokazu.graph.RenderableVertex;
import org.nishiohirokazu.grinEdit.Infrastructure;
import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.layout.PL_Anchor;
import org.nishiohirokazu.swt.ViewportTransformer;

/**
 * A concrete MouseOperation: move vertex and anchor it.
 * If you press shift key when your mouse button up,
 *  target vertex isn't anchored.
 * @author nishio
 *
 */
public class MO_MoveVertex extends MouseOperation {
    public String getName(){
        return "Move vertex";
    }
    public Map getDesc(){
    	Map result = new HashMap();
    	result.put("ja", "ドラッグで頂点をつかんで移動する。" +
    		"離すときにシフトキーが押されていなければその場に固定する。");
    	result.put("en", "To move vertex by drag." +
    		"Releasing mouse button without shift-key, the vertex is anchored.");
    	
        return result;
    }

	private RenderableVertex target;

	private void setAnchor(){
		Graph g = med.getGraph();
		PL_Anchor a = (PL_Anchor) g.getLawDict().get("PL_Anchor");
		a.putTarget(target.getId(), target.getPosition().clone());
	}
	private void removeAnchor(){
		Graph g = med.getGraph();
		PL_Anchor a = (PL_Anchor) g.getLawDict().get("PL_Anchor");
		a.removeTarget(target.getId());
	}
	
	public void mouseDown(int x, int y, int mask) {
		int RADIUS = 20;
		int SQ_RADIUS = RADIUS * RADIUS;
		med = Mediator.getInstance();
		target = (RenderableVertex) med.getNearestVertex(x, y, SQ_RADIUS);
        if(target != null){
        	setAnchor();
        	mouseMove(x, y, mask);
        }
	}

	public void mouseMove(int x, int y, int mask) {
		ViewportTransformer vp = Infrastructure.getViewportTransformer();
		if(target != null){
			double[] screenPos = {x, y};
			double[] anchorPos = vp.invViewportTransform(screenPos);
			target.setPosition(anchorPos);
        	setAnchor();
		}
	}

	public void mouseUp(int x, int y, int mask) {
		UtilMetaKey.setMask(mask);

		if(target != null){
//			Hashtable ft = med.getNamedDict("forceTable");
			if(UtilMetaKey.ctrl){
				// if ctrl pressed, add force
				// if ctrl+shift pressed, remove force
				removeAnchor();

//				if(UtilMetaKey.shift){ // shift
//					ft.remove(target);
//				}else{
//					ViewportTransformer vp = Infrastructure.getViewportTransformer();
//					double[] screenPos={x, y};
//					double[] force = vp.invScaling(
//							Vec.sub(screenPos, target.screenPos));
//					ft.put(target, force);
//				}
			}else if(UtilMetaKey.shift){ // shift
				// if shift pressed, remove anchor
				removeAnchor();

//				ft.remove(target);

			}
		}
		target = null;
	}
}
