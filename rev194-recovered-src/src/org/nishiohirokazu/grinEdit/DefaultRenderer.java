package org.nishiohirokazu.grinEdit;

import java.util.Hashtable;
import java.util.Iterator;

import org.nishiohirokazu.graph.IHasScreenPos;
import org.nishiohirokazu.graph.IRenderable;
import org.nishiohirokazu.layout.IMassPoint;
import org.nishiohirokazu.swt.ViewportTransformer;

public class DefaultRenderer implements IRenderer {

	public void render(Object target) {
		ViewportTransformer vp = Infrastructure.getViewportTransformer();
		Hashtable<String, IRenderable> vertexDict = Mediator.getInstance().getVertexDict();
		Hashtable<String, IRenderable> edgeDict = Mediator.getInstance().getEdgeDict();

		synchronized (vertexDict) {
			synchronized (edgeDict) {
				for(Iterator i = vertexDict.values().iterator(); i.hasNext();){
					Object o = i.next();
					IMassPoint v = (IMassPoint) o;
					IHasScreenPos sv = (IHasScreenPos) o;
					sv.setScreenPos(vp.viewportTransform(v.getPosition()));
				}

				for(IRenderable o: edgeDict.values()){
					o.render(target);
				}

				for(IRenderable o: vertexDict.values()){
					o.render(target);
				}
			}
		}
	}

}
