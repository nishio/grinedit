/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/05/22
 *
 */
package org.nishiohirokazu.layout;

import java.util.Hashtable;

import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.grinEdit.UtilCast;

/**
 * 
 * @author nishio
 */
public class PL_Repulsion extends PhysicalLaw {
	public double repulsionK = 0.02;
	public double repulsionRadius = 3.0;

	private String targetName;

	public PL_Repulsion() {
		targetName = "Vertex";
		
	}

	public PL_Repulsion(String targetName) {
		this.targetName = targetName;
	}

	public boolean apply(int iter) {
		// repulsion
		// I'd not prefer to use 1/r or 1/r^2 type force.
		if (iter == 0) {
			Hashtable target = Mediator.getInstance().getNamedDict(targetName);
			double SQ_REPULSION_RADIUS = repulsionRadius * repulsionRadius;
			double INV_SQ_REPULSION_RADIUS = 1.0 / SQ_REPULSION_RADIUS;
			double REPULSION_K = repulsionK;

			Object[] vs = target.values().toArray();
			IMassPoint[] mvs = new IMassPoint[vs.length];
			double[][] posList = new double[vs.length][];
			double[][] forceList = new double[vs.length][];
			for(int i = 0; i < vs.length; i++){
				IMassPoint v = (IMassPoint) vs[i];
				mvs[i] = v;
				posList[i] = v.getPosition();
				forceList[i] = new double[]{0.0, 0.0};
			}

			for (int i = 0; i < vs.length; i++) {
				double[] v1pos = posList[i];
				for (int j = 0; j < i; j++) {
					double v2pos[] = posList[j];
					//double[] dir = Vec.sub(v2pos, v1pos);
					// (inline)
					double dirx = v2pos[0] - v1pos[0];
					double diry = v2pos[1] - v1pos[1];
					
					if(dirx > repulsionRadius || dirx < -repulsionRadius){
						continue;
					}
					if(diry > repulsionRadius || diry < -repulsionRadius){
						continue;
					}

					// double dist = Vec.sqMag(dir) * INV_SQ_REPULSION_RADIUS;
					// (inline)
					double dist_sq = dirx * dirx + diry * diry;
					double dist_normalize = dist_sq * INV_SQ_REPULSION_RADIUS;
					if (dist_normalize < 1.0) {
						// ndir = Vec.normalize(dir);
						// (inline)
						double dist = Math.sqrt(dist_sq);
						double ndirx = dirx / dist;
						double ndiry = diry / dist;
						if(dist_sq == 0){
							double theta = Math.PI * 2 * Math.random();
							ndirx = Math.cos(theta);
							ndiry = Math.sin(theta);
						}
						
						double power = REPULSION_K * (1.0 - dist_normalize);

						//double force[] = Vec.scale(ndir, power);
						// (inline)
						double forcex = ndirx * power;
						double forcey = ndiry * power;

						//Vec.addD(forceList[j], force);
						//(inline)
						forceList[j][0] += forcex;
						forceList[j][1] += forcey;
						
						//Vec.subD(forceList[i], force);
						//(inline)
						forceList[i][0] -= forcex;
						forceList[i][1] -= forcey;
						
					}
				}
			}

			for(int i = 0; i < vs.length; i++){
				mvs[i].getDVelList().add(forceList[i]);
			}
		}

		return true;
	}
	

	public void setRepulsionK(Object o) {
		repulsionK = UtilCast.o2double(o);
	}

	public void setRepulsionRadius(Object o) {
		repulsionRadius = UtilCast.o2double(o);
	}

	public Hashtable getParams() {
		Hashtable result = super.getParams();
		result.put("repulsionK", Double.valueOf(repulsionK));
		result.put("repulsionRadius", Double.valueOf(repulsionRadius));
		return result;
	}

}
