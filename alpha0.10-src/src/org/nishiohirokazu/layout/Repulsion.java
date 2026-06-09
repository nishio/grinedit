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

import org.nishiohirokazu.graph.RenderableVertex;
import org.nishiohirokazu.grinEdit.UtilXMLRPC;
import org.nishiohirokazu.vector.Vec;

/**
 * 
 * @author nishio
 */
public class Repulsion extends PhysicalLaw {
	private Vector target;
	public double repulsionK = 0.02;
	public double repulsionRadius = 3.0;
	/**
	 * 
	 * @param target: Vector of Vertex
	 */
	public Repulsion(Vector target) {
		this.target = target;
	}
	public boolean apply() {
		// repulsion
		// I'd not prefer to use 1/r or 1/r^2 type force.
		double REPULSION_RADIUS = repulsionRadius;
		double REPULSION_K = repulsionK;
		for (int i = 0; i < target.size(); i++) {
			RenderableVertex v1 = (RenderableVertex) target.get(i);
			for (int j = 0; j < i; j++) {
				RenderableVertex v2 = (RenderableVertex) target.get(j);
				double[] dir = Vec.sub(v2.position, v1.position);
				double dist = Vec.mag(dir);
				if (dist < REPULSION_RADIUS) {
					double[] ndir = Vec.normalize(dir);
					double power = REPULSION_K
							* (1.0 - dist / REPULSION_RADIUS);
					double force[] = Vec.scale(ndir, power);
					v2.velocityList.add(force);
					v1.velocityList.add(Vec.inv(force));
				}
			}
		}
		return true;
	}
	public int tolerance(){
		return -1;
	}
	public void rpc_repulsionK(Object o){
		repulsionK = UtilXMLRPC.ToDouble(o); 
	}
	public void rpc_repulsionRadius(Object o){
		repulsionRadius = UtilXMLRPC.ToDouble(o); 
	}
	public Hashtable rpc_getParams(){
		Hashtable result = new Hashtable();
		result.put("repulsionK", Double.valueOf(repulsionK));
		result.put("repulsionRadius", Double.valueOf(repulsionRadius));
		return result;
	}

}
