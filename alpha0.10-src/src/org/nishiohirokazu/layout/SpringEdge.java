/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/05/22
 *
 */
package org.nishiohirokazu.layout;

import java.util.Hashtable;
import java.util.Vector;

import org.nishiohirokazu.graph.RenderableEdge;
import org.nishiohirokazu.grinEdit.UtilXMLRPC;
import org.nishiohirokazu.vector.Vec;

/**
 * 
 * @author nishio
 */

public class SpringEdge extends PhysicalLaw {
	private Vector target;
	public double defaultNormalLength = 1.0;
	public double defaultSpringStrength = 0.1;

	/**
	 * 
	 * @param target: Vector of Edge
	 */
	public SpringEdge(Vector target){
		this.target = target;
	}

	public boolean apply() {
		Vector edgeList = target;
		for (int i = 0; i < edgeList.size(); i++) {
			RenderableEdge e = (RenderableEdge) edgeList.get(i);
			double[] dir = Vec.sub(e.v2.position, e.v1.position);
			double dist = Vec.mag(dir);
			double[] ndir = Vec.normalize(dir);
			double k = e.k;
			if (k < 0) {
				k = defaultSpringStrength;
			}
			double length = e.length;
			if (length < 0) {
				length = defaultNormalLength;
			}
						
			double power = (length - dist) * k;
			double force[] = Vec.scale(ndir, power);
			e.v2.velocityList.add(force);
			e.v1.velocityList.add(Vec.inv(force));
		}
		return true;
	}
	public int tolerance(){
		return -1;
	}
	public void rpc_defaultNormalLength(Object o){
		defaultNormalLength = UtilXMLRPC.ToDouble(o); 
	}
	public void rpc_defaultSpringStrength(Object o){
		defaultSpringStrength = UtilXMLRPC.ToDouble(o); 
	}
	public Hashtable rpc_getParams(){
		Hashtable result = new Hashtable();
		result.put("defaultNormalLength", Double.valueOf(defaultNormalLength));
		result.put("defaultSpringStrength", Double.valueOf(defaultSpringStrength));
		return result;
	}

}
