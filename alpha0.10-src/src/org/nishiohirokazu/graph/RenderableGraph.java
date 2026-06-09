/*
 * nishio 2005/02/26
 *
 */
package org.nishiohirokazu.graph;

import java.util.Hashtable;
import java.util.Vector;

import org.nishiohirokazu.grinEdit.Infrastructure;
import org.nishiohirokazu.layout.Aggregator;
import org.nishiohirokazu.layout.Anchor;
import org.nishiohirokazu.layout.Repulsion;
import org.nishiohirokazu.layout.SpringEdge;
import org.nishiohirokazu.swt.ColorHolder;
import org.nishiohirokazu.swt.ViewportTransformer;

/**
 * @author nishio
 * 
 */
public class RenderableGraph extends Graph {
	public Hashtable anchorTable;
	private boolean isDefaultSetting = true;
	
	public RenderableGraph() {
		super();
		vertexList = new Vector();
		edgeList = new Vector();
		anchorTable = new Hashtable();

		aggregator = new Aggregator();
		aggregator.addLaw(new SpringEdge(edgeList));
		aggregator.addLaw(new Repulsion(vertexList));
		aggregator.addLaw(new Anchor(anchorTable));
	}

	public RenderableVertex addVertex() {
//		RenderableVertex v = new BoxVertex();
//		vertexList.add(v);
		RenderableVertex v = new CircleVertex();
		vertexList.add(v);
		return v;
	}

	public RenderableVertex addVertex(String label) {
		BoxVertex v = new BoxVertex();
		v.label = label;
		vertexList.add(v);
		return v;
	}

	public RenderableVertex addVertex(String name, int r, int gr, int b) {
		BoxVertex v = new BoxVertex();
		v.label = name;
		v.bgcolor = ColorHolder.get(r, gr, b);
		vertexList.add(v);
		return v;
	}

	public RenderableEdge addEdge(RenderableVertex v1, RenderableVertex v2) {
		RenderableEdge e = new LinearEdge(v1, v2);
		edgeList.add(e);
		return e;
	}

	public RenderableEdge addEdge(RenderableVertex v1, RenderableVertex v2,
			int r, int g, int b) {
		RenderableEdge e = new LinearEdge(v1, v2, r, g, b);
		edgeList.add(e);
		return e;
	}

	public void layoutStep() {
		aggregator.aggregate();
	}

	/**
	 * @param dbuf
	 * @param vp
	 */
	public void render(Object target) {
		int numVtx = vertexList.size();
		ViewportTransformer vp = Infrastructure.getViewportTransformer();

		for (int i = 0; i < numVtx; i++) {
			RenderableVertex v = (RenderableVertex) vertexList.get(i);
			v.screenPos = vp.viewportTransform(v.position);
		}

		for (int i = 0; i < edgeList.size(); i++) {
			RenderableEdge e = (RenderableEdge) edgeList.get(i);
			e.render(target);
		}

		//bg.setForeground(new Color(null, 0, 0, 0));
		// int SIZE=3;
		for (int i = 0; i < numVtx; i++) {
			RenderableVertex v = (RenderableVertex) vertexList.get(i);
			v.render(target);
		}
	}

	public Hashtable getAnchorTable() {
		return anchorTable;
	}

	public boolean isDefaultSetting() {
		return isDefaultSetting;
	}

}
