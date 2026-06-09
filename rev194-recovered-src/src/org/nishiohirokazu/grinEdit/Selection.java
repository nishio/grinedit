/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/04/30
 *
 */
package org.nishiohirokazu.grinEdit;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.nishiohirokazu.graph.RenderableEdge;
import org.nishiohirokazu.graph.RenderableVertex;

/**
 * Manage selection of vertexes. Don't edit Vertex.selected directly.
 * 
 * @author nishio
 */
public class Selection {

	private List vertexList = new Vector();

	private Vector edgeList = new Vector();

	private Vector marginalList = new Vector();

	private Mediator med;

	/**
	 * @param mediator
	 */
	public Selection(Mediator mediator) {
		this.med = mediator;
	}

	public void set(List l) {
		for (int i = 0; i < vertexList.size(); i++) {
			RenderableVertex v = (RenderableVertex) vertexList.get(i);
			v.selected = false;
		}
		vertexList = l;
		for (int i = 0; i < vertexList.size(); i++) {
			RenderableVertex v = (RenderableVertex) vertexList.get(i);
			v.selected = true;
		}
		edgeList = new Vector();
		marginalList = new Vector();
		Hashtable allEdges = med.getEdgeDict();
		for(Iterator i = allEdges.values().iterator();i.hasNext();){
			RenderableEdge e = (RenderableEdge) i.next();
			RenderableVertex v1 = (RenderableVertex) e.getV1();
			RenderableVertex v2 = (RenderableVertex) e.getV2();
			if (v1.selected && v2.selected) {
				edgeList.add(e);
				e.selected = true;
			} else if (v1.selected || v2.selected) {
				marginalList.add(e);
				e.selected = false;
			} else {
				e.selected = false;
			}
		}
	}

	public List getVertex() {
		return vertexList;
	}

	public Vector getEdges() {
		return edgeList;
	}

	public Vector getMarginalEdges() {
		return marginalList;
	}

}
