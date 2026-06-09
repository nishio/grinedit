/*
 * nishio 2005/02/26
 *
 */
package org.nishiohirokazu.graph;

import java.util.Hashtable;

import org.nishiohirokazu.grinEdit.IHasProperty;
import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.layout.MassPoint;
import org.nishiohirokazu.layout.PL_Anchor;
import org.nishiohirokazu.narVisualizer.IHasTerminal;
import org.nishiohirokazu.vector.Vec;

/**
 * @author nishio
 * Simple vertex, with no size 
 *
 */
public class RenderableVertex extends MassPoint implements IHasProperty, IRenderable, IHasTerminal, IHasScreenPos{
	public boolean selected;
	protected double[] screenPos;
	public Hashtable properties = new Hashtable();
	public RenderableVertex() {
		super();
		
		double[] position = new double[2];
		position[0] = Math.random() - 0.5;
		position[1] = Math.random() - 0.5;
		setPosition(position);
	}

	public Hashtable<String, Object> getParams(){
		Hashtable<String, Object> result = super.getParams();
		result.put("id", getId());
		return result;
	}
	public void setAnchored(Object value){
		Graph g = Mediator.getInstance().getGraph();
		PL_Anchor a = (PL_Anchor) g.getLawDict().get("PL_Anchor");
		a.putTarget(this, value);
	}

	public void setDisanchored(){
		Graph g = Mediator.getInstance().getGraph();
		PL_Anchor a = (PL_Anchor) g.getLawDict().get("PL_Anchor");
		a.removeTarget(getId());
	}
	public void setDisanchored(Object value){
		setDisanchored();
	}

	
	public void render(Object target){
	}
	
	/**
	 * 
	 * @param ndir direction of edge
	 * @return offset: distance between center of vertex
	 * 	 and crosspoint of edge and bound.
	 */
	public double calcOffset(double[] ndir){
		return 0.0;
	}

	public void putProperty(String key, Object value) {
		properties.put(key, value);
	}

	public double[] getTerminal(String edge_id) {
		RenderableEdge e = (RenderableEdge) Mediator.getInstance().getObject(edge_id);
		double[] ndir;
		if(e.v1 == this){
			ndir = Vec.normalize(Vec.sub(e.v2.screenPos, e.v1.screenPos));
		}else if(e.v2 == this){
			ndir = Vec.normalize(Vec.sub(e.v1.screenPos, e.v2.screenPos));
		}else{
			throw new RuntimeException();
		}
		double offset = calcOffset(ndir);

		return Vec.add(Vec.scale(ndir, offset), screenPos);
	}

	public double[] getScreenPos() {
		return screenPos;
	}

	public void setScreenPos(double[] pos) {
		screenPos = pos;
	}

}
