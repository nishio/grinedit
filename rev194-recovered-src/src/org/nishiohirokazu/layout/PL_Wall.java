/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/09/20
 *
 */
package org.nishiohirokazu.layout;




/**
 * •Ç‚É•t’…‚·‚é
 * 
 * @author nishio
 */
public class PL_Wall extends PL_AbstructWall {
	public PL_Wall() {
		super("Vertex");
	}
	public PL_Wall(String targetName) {
		super(targetName);
	}

	protected boolean wall(double a, double b, double dir, int xy, boolean isSatisfied, MassPoint v) {
		double diff = b - a;
		if (diff * dir > 0) {
			double[] f = new double[2];
			f[xy] = diff;
			f = vp.invScaling(f);
			isSatisfied = false;
			v.getDVelList().add(f);
		}
		return isSatisfied;
	}

}
