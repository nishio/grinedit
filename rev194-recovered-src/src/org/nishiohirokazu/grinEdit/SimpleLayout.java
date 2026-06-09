package org.nishiohirokazu.grinEdit;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import org.nishiohirokazu.graph.Graph;
import org.nishiohirokazu.graph.IVertex;
import org.nishiohirokazu.layout.IMassPoint;
import org.nishiohirokazu.layout.PhysicalLaw;
import org.nishiohirokazu.vector.Vec;

/**
 * シンプルで高速なグラフを整形するための整形エンジン
 * @author nishio
 *
 */

public class SimpleLayout implements ILayout{
	public void layoutStep(Graph g) {
		Hashtable vertexDict = g.getVertexDict();
		Hashtable edgeDict = g.getEdgeDict();
		synchronized (vertexDict) {
			synchronized (edgeDict) {
				// apply constrain till all satisfied
				int MAX_ITER = 10;
				Mediator med = Mediator.getInstance();
				Hashtable<String, IVertex> vs = med.graph.getVertexDict();
				Vector oldPos = new Vector(vs.size());
			
				// backup old position
				for(IVertex o: vs.values()){
					IMassPoint v = (IMassPoint) o;
					double[] p = v.getPosition().clone();
					if(p[0] == Double.NaN){
						Mediator.getInstance().autoLayout = false;
						return;
					}
					oldPos.add(p);
				}
				
				for (int iter = 0; iter < MAX_ITER; iter++) {
			
					// initialize force list
					for(IVertex o: vs.values()){
						IMassPoint v = (IMassPoint) o;
						v.getDVelList().clear();
					}
					
					// apply physical laws
					boolean isAllSatisfied = true;
					Hashtable law = g.getLawDict();
			
					for(Iterator e = law.values().iterator(); e.hasNext();){
						PhysicalLaw p = (PhysicalLaw) e.next();
						boolean isSatisfied = p.apply(iter);
						if (!isSatisfied) {
							isAllSatisfied = false;
						}
					}
					
					// update potision
					for(Iterator e = vs.values().iterator(); e.hasNext();){
						IMassPoint v = (IMassPoint) e.next();
						double[] vel = new double[2];
						for (int j = 0; j < v.getDVelList().size(); j++) {
							double[] f = (double[]) v.getDVelList().get(j);
							Vec.addD(vel, f);
						}
						Vec.addD(v.getPosition(), vel);
					}
					
					if (isAllSatisfied) {
						break;
					}
				}
				// update velocity
				int i = 0;
				for(Iterator e = vs.values().iterator(); e.hasNext();){
					IMassPoint v = (IMassPoint) e.next();
					v.setVelocity(Vec.sub(v.getPosition(), (double[]) oldPos.get(i)));
					i++;
				}
			}
	
		}
	}

}
