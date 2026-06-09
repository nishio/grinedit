package org.nishiohirokazu.layout;

import java.util.Hashtable;
import java.util.Iterator;

import org.nishiohirokazu.graph.RenderableVertex;
import org.nishiohirokazu.grinEdit.Infrastructure;
import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.grinEdit.UtilCast;
import org.nishiohirokazu.swt.ViewportTransformer;

public abstract class PL_AbstructWall extends PhysicalLaw {
	protected boolean top = true;
	protected boolean bottom = true;
	protected boolean left = true;
	protected boolean right = true;
	protected int tolerance = 8;
	protected ViewportTransformer vp;
	private String targetName;

	protected abstract boolean wall(double a, double b, double dir, int xy, boolean isSatisfied, MassPoint v);
	
	public Hashtable getParams() {
		Hashtable result = super.getParams();
		result.put("top", new Boolean(top));
		result.put("bottom", new Boolean(top));
		result.put("left", new Boolean(left));
		result.put("right", new Boolean(right));
		return result;
	}

	public void setTop(Object o){
		top = UtilCast.o2bool(o);
	}
	public void setBottom(Object o){
		bottom = UtilCast.o2bool(o);
	}
	public void setLeft(Object o){
		left = UtilCast.o2bool(o);
	}
	public void setRight(Object o){
		right = UtilCast.o2bool(o);
	}

	public PL_AbstructWall(String targetName) {
		this.targetName = targetName;
	}

	public boolean apply(int iter) {
		boolean isSatisfied = true;
		if (iter == 0) {
			return false; // not to satisfy on first iter
		}
		if (iter < tolerance) {
			vp = Infrastructure.getViewportTransformer();
			int[] size;
			try {
				size = Mediator.getInstance().getCanvasSize();
			} catch (Exception e) {
				e.printStackTrace();
				size = new int[] { 500, 500 };
			}
			int width = size[0], height = size[1];
			Hashtable target = Mediator.getInstance().getNamedDict(targetName);
			for (Iterator e = target.values().iterator(); e.hasNext();) {
				RenderableVertex v = (RenderableVertex) e.next();
				double[] screenPos = vp.viewportTransform(v.getPosition());
	
				double x = screenPos[0];
				double y = screenPos[1];
				if (left) {
					isSatisfied = wall(x, 0, 1, 0, isSatisfied, v);
				}
				if (right) {
					isSatisfied = wall(x, width, -1, 0, isSatisfied, v);
				}
				if (top) {
					isSatisfied = wall(y, 0, 1, 1, isSatisfied, v);
				}
				if (bottom) {
					isSatisfied = wall(y, height, -1, 1, isSatisfied, v);
				}
	
			}
		}
		return isSatisfied;
	}

}
