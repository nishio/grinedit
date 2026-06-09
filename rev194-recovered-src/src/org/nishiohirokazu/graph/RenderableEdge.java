/*
 * nishio 2005/02/26
 *
 */
package org.nishiohirokazu.graph;

import java.util.Hashtable;

import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.layout.PL_SpringEdge;



/**
 * @author nishio
 *
 */
public class RenderableEdge implements IRenderable, IGRINObject, IEdge {
	protected RenderableVertex v1 = NullVertex.getInstance();
	protected RenderableVertex v2 = NullVertex.getInstance();

//	public double k = -1.0; // negative value means "undef"
	public boolean selected;
//	public double length = -1.0; // negative value means "undef"
	public String id;
	
	public RenderableEdge() {}
		
	public RenderableEdge(RenderableVertex _v1, RenderableVertex _v2) {
		v1=_v1;
		v2=_v2;
	}

	public String toString() {
		assert v1 != null && v2 != null;
		return  "(" + v1.toString() + ", " + v2.toString() + ")";
	}
	
	public void render(Object target){
		// do nothing
	}
//	public void setK(Object value){
//		k = UtilCast.o2double(value);
//	}
	public void setLength(Object value){
		PL_SpringEdge sp = (PL_SpringEdge) Mediator.getInstance().getObject("PL_SpringEdge");
		sp.putNormalLength(this, value);
	}
	public void setV1(Object value){
		v1 = Mediator.getInstance().graph.getVertex(value.toString());
	}
	public void setV2(Object value){
		v2 = Mediator.getInstance().graph.getVertex(value.toString());
	}

	public Hashtable getParams(){
		Hashtable<String, Object> result = new Hashtable<String, Object>();
//		result.put("k", Double.valueOf(k));
//		result.put("length", Double.valueOf(length));
		result.put("v1", v1.getId());
		result.put("v2", v2.getId());
		result.put("id", id);
		return result;
	}

	public String getId() {
		return id;
	}

	public void setId(Object o) {
		id = o.toString();
	}

	public IVertex getV1() {
		return v1;
	}

	public IVertex getV2() {
		return v2;
	}

	
}
