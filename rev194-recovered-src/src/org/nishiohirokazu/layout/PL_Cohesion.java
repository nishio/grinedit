/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/09/20
 *
 */
package org.nishiohirokazu.layout;

import java.util.Hashtable;

import org.nishiohirokazu.grinEdit.UtilCast;

/**
 * •Ç‚É•t’…‚·‚é
 * 
 * @author nishio
 */
public class PL_Cohesion extends PL_AbstructWall {
	private int radius = 20;
	public PL_Cohesion() {
		super("Vertex");
	}
	public PL_Cohesion(String targetName) {
		super(targetName);
	}
	protected boolean wall(double a, double b, double dir, int xy, boolean isSatisfied, MassPoint v) {
		double diff = (b - a); 
		if(diff * dir + radius > 0){
			double[] f = new double[2];
			f[xy] = diff;
			f = vp.invScaling(f);
			if (diff > 0.4 || diff < -0.4) {
				isSatisfied = false;
			}
			v.getDVelList().add(f);
		}
		return isSatisfied;
	}

	public Hashtable getParams() {
		Hashtable result = super.getParams();
		result.put("radius", new Integer(radius));
		return result;
	}
	
	public void setRadius(Object o){
		radius = UtilCast.getIntValue(o);
	}


}
