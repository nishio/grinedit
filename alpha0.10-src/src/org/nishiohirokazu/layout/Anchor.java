/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/09/20
 *
 */
package org.nishiohirokazu.layout;

import java.util.Enumeration;
import java.util.Hashtable;

import org.nishiohirokazu.graph.RenderableVertex;
import org.nishiohirokazu.grinEdit.Infrastructure;
import org.nishiohirokazu.swt.ViewportTransformer;
import org.nishiohirokazu.vector.Vec;



/**
 * “Á’èˆÊ’u‚É’¸“_‚ðŒÅ’è‚·‚é
 * @author nishio
 */
public class Anchor extends PhysicalLaw {
	private Hashtable target;
	public  Anchor(Hashtable target) {
		this.target = target;
	}
	public boolean apply(){
		boolean isSatisfied = true;
        ViewportTransformer vp = Infrastructure.getViewportTransformer();
		for (Enumeration e = target.keys(); e.hasMoreElements(); ) {
            RenderableVertex v = (RenderableVertex) e.nextElement();
            double[] position = (double[]) target.get(v);
            double[] diff = Vec.sub(position, v.position);
            if(Vec.mag(vp.scaling(diff)) > 0.1){
            	isSatisfied = false;
                v.velocityList.add(diff);
            }
        }
		return isSatisfied;
	}
	public int tolerance(){
		return 3;
	}
	public Hashtable rpc_getParams(){
		Hashtable result = new Hashtable();
		return result;
	}
}
