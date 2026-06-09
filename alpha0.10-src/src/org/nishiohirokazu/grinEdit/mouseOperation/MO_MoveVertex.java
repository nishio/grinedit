/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/03/25
 *
 */
package org.nishiohirokazu.grinEdit.mouseOperation;

import java.util.Vector;

import org.nishiohirokazu.graph.RenderableVertex;
import org.nishiohirokazu.grinEdit.Infrastructure;
import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.swt.ViewportTransformer;
import org.nishiohirokazu.vector.Vec;

/**
 * @author nishio
 *
 */
public class MO_MoveVertex extends MouseOperation {
    public String getName(){
        return "Move vertex";
    }

	private RenderableVertex target;
//	private MouseEvent lastEvent;
	public boolean toAnchor = true;

	public MO_MoveVertex(boolean toAnchor) {
		super();
		this.toAnchor = toAnchor;
	}
	public void mouseDown(int x, int y) {
		int RADIUS=20;
//		ArrayList candidate = med.getVertexInRange(
//						new Point(x - RADIUS, y - RADIUS),
//						new Point(x + RADIUS, y + RADIUS));
		double[] pos={x, y};
		double minDist=RADIUS*2;
		Vector vs = med.graph.vertexList;
        for (int i = 0; i < vs.size(); i++) {
            RenderableVertex v = (RenderableVertex) vs.get(i);
            if(v.screenPos != null){
                double dist = Vec.distance(v.screenPos, pos);
                if(dist < minDist){
                    minDist=dist;
                    target=v;
                }
            }
        }
        if(target != null){
        	med.graph.anchorTable.put(
        			target,
        			target.position);
        }
//		lastEvent=e;
	}
	public void mouseMove(int x, int y) {
		ViewportTransformer vp = Infrastructure.getViewportTransformer();
		if(target != null){
			double[] screenPos={x, y};
			Mediator med = Mediator.getInstance();
			med.graph.anchorTable.put(target, 
					vp.invViewportTransform(screenPos));
			
			
//			lastEvent=e;
		}
	}
	public void mouseUp(int x, int y) {
		if(target != null && !toAnchor ){
			med.graph.anchorTable.remove(target);
		}
		
		target=null;
	}
}
