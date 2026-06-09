package org.nishiohirokazu.layout;

import java.util.Hashtable;

import org.nishiohirokazu.graph.RenderableVertex;
import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.grinEdit.UtilCast;

public class PL_Gravity extends PhysicalLaw {
	public double[] f = {0.0, 0.03};
	private String targetName;

	public PL_Gravity() {
		this.targetName = "Vertex";
	}

	public boolean apply(int iter) {
		// repulsion
		// I'd not prefer to use 1/r or 1/r^2 type force.
		if (iter == 0) {
			Hashtable target = Mediator.getInstance().getNamedDict(targetName);
			Object[] vs = target.values().toArray();
			for (int i = 0; i < target.size(); i++) {
				RenderableVertex v = (RenderableVertex) vs[i];
				v.getDVelList().add(f);
			}
		}
		return true;
	}

	public void setF(Object o) {
		f = UtilCast.o2doubleArray(o);
	}

	public Hashtable getParams() {
		Hashtable result = super.getParams();
		result.put("f", UtilCast.Vec2Vector(f));
		return result;
	}
}
