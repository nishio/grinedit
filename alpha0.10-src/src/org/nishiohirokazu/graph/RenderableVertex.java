/*
 * nishio 2005/02/26
 *
 */
package org.nishiohirokazu.graph;

import java.util.Hashtable;

import org.nishiohirokazu.layout.MassPoint;

/**
 * @author nishio
 * Simple vertex, with no size 
 *
 */
public class RenderableVertex extends MassPoint{
	public boolean selected;
	public double[] screenPos;
//	private IRenderer renderer; // TODO: support delegation?
	public Hashtable properties;
	public RenderableVertex() {
		super();
		
		position = new double[2];
		position[0] = Math.random() - 0.5;
		position[1] = Math.random() - 0.5;
	}

	public void render(Object target){
		//renderer.render(target, this);
	}
	
//	public void setRenderer(IRenderer renderer){
//		this.renderer = renderer;
//	}

	/**
	 * 
	 * @param ndir direction of edge
	 * @return offset: distance between center of vertex
	 * 	 and crosspoint of edge and bound.
	 */
	public double calcOffset(double[] ndir){
		return 0.0;
	}
}
