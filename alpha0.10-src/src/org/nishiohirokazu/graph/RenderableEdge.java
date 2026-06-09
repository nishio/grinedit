/*
 * nishio 2005/02/26
 *
 */
package org.nishiohirokazu.graph;

import java.util.Hashtable;



/**
 * @author nishio
 *
 */
public class RenderableEdge {
	public RenderableVertex v1,v2;
	public double k = -1.0; // negative value means "undef"
	public boolean selected;
	public double length = -1.0; // negative value means "undef"
	public boolean directed = false;
	
	public RenderableEdge() {};
		
	public RenderableEdge(RenderableVertex _v1, RenderableVertex _v2) {
		v1=_v1;
		v2=_v2;
	}

	public String toString() {
		return  "(" + v1.toString() + ", " + v2.toString() + ")";
	}
	
	public void render(Object target){
		// do nothing
	}
	public void rpc_k(Object value){
		k = ((Double)value).doubleValue();
	}
	public void rpc_length(Object value){
		length = ((Double)value).doubleValue();
	}
	public Hashtable rpc_getParams(){
		Hashtable result = new Hashtable();
		result.put("k", Double.valueOf(k));
		result.put("length", Double.valueOf(length));
		return result;
	}

	
}
