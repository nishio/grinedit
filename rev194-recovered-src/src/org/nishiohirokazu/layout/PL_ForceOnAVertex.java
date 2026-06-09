package org.nishiohirokazu.layout;

import java.util.Hashtable;

import org.nishiohirokazu.graph.RenderableVertex;

public class PL_ForceOnAVertex extends PhysicalLaw {
	private Hashtable<RenderableVertex, double[]> target;

	public PL_ForceOnAVertex() {
		this.target = new Hashtable();
	}

	public boolean apply(int iter) {
		if (iter == 0) {
			for(RenderableVertex v: target.keySet()){
				v.getDVelList().add(target.get(v));
			}
		}
		return true;
	}

	public Hashtable getParams() {
		Hashtable result = super.getParams();
		return result;
	}
	

}
