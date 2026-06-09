package org.nishiohirokazu.graph;

/**
 * Null Object
 * @author nishio
 *
 */
public class NullVertex extends RenderableVertex {
	private static NullVertex instance;
	public static RenderableVertex getInstance(){
		if(instance == null){
			instance = new NullVertex();
		}
		return instance;
	}
}
