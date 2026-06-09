/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/09/20
 *
 */
package org.nishiohirokazu.layout;

import java.util.Hashtable;
import java.util.Map;

import org.nishiohirokazu.graph.IGRINObject;
import org.nishiohirokazu.graph.IVertex;
import org.nishiohirokazu.graph.RenderableVertex;
import org.nishiohirokazu.grinEdit.Infrastructure;
import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.grinEdit.UtilCast;
import org.nishiohirokazu.swt.ViewportTransformer;
import org.nishiohirokazu.vector.Vec;

/**
 * “Á’èˆÊ’u‚É’¸“_‚ðŒÅ’è‚·‚é
 * 
 * @author nishio
 */
public class PL_Anchor extends PhysicalLaw {
	private int tolerance = 9; // how many times it is applied in a step

	private Hashtable<IVertex, double[]> target;

	public PL_Anchor() {
		target = new Hashtable();
	}

	public boolean apply(int iter) {
		boolean isSatisfied = true;
		if (iter == 0) {
			return false; // not to satisfy on first iter
		}
		if (iter < tolerance) {
			ViewportTransformer vp = Infrastructure.getViewportTransformer();
			for (IVertex _v : target.keySet()) {
				IMassPoint v = (IMassPoint) _v;
				double[] position = target.get(v);
				double[] diff = Vec.sub(position, v.getPosition());
				if (Vec.mag(vp.scaling(diff)) > 0.1) {
					isSatisfied = false;
					v.getDVelList().add(diff);
				}
			}
		}
		return isSatisfied;
	}

	public Hashtable getParams() {
		Hashtable result = super.getParams();
		Hashtable target_ = new Hashtable();
		for (IVertex _v : target.keySet()) {
			IGRINObject v = (IGRINObject) _v;
			target_.put(v.getId(), UtilCast.Vec2Vector(target.get(v)));
		}
		result.put("target", target_);
		return result;
	}

	public void putTarget(String name, Object value) {
		target.put((RenderableVertex) Mediator.getInstance().getObject(name),
				UtilCast.o2doubleArray(value));
	}

	public void putTarget(RenderableVertex o, Object value) {
		target.put(o, UtilCast.o2doubleArray(value));
	}

	public void removeTarget(String name) {
		target.remove(Mediator.getInstance().getObject(name));
	}

	public void setTarget(Object o) {
		target = new Hashtable();
		Map map = UtilCast.maplike2map(o);
		Mediator med = Mediator.getInstance();
		for (Object key : map.keySet()) {
			target.put(
				med.getVertex(key.toString()),
				UtilCast.o2doubleArray(map.get(key)));

		}

	}

}
