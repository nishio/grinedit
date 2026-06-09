package org.nishiohirokazu.layout;

import java.util.Hashtable;

import org.nishiohirokazu.graph.RenderableVertex;
import org.nishiohirokazu.grinEdit.UtilCast;
import org.nishiohirokazu.vector.Vec;

public class PL_Inertia extends PhysicalLaw {
	public double K = 0.0;
	private Hashtable target;

	public PL_Inertia() {
		this.target = new Hashtable();
	}
	public PL_Inertia(Hashtable target) {
		this.target = target;
	}

	public boolean apply(int iter) {
		if (iter == 0) {
			Object[] vs = target.values().toArray();
			for (int i = 0; i < target.size(); i++) {
				RenderableVertex v = (RenderableVertex) vs[i];
				v.getDVelList().add(Vec.scale(v.getVelocity(), K));
			}
		}
		return true;
	}

	public void setK(Object o) {
		K = UtilCast.o2double(o);
	}

	public Hashtable getParams() {
		Hashtable result = super.getParams();
		result.put("K", Double.valueOf(K));
		return result;
	}
	

}
