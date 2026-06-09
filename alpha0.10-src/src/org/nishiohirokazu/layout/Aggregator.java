/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/05/22
 *
 */
package org.nishiohirokazu.layout;

import java.util.Vector;

import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.vector.Vec;

/**
 *
 * @author nishio
 */
public class Aggregator{
    public Vector law=new Vector();
    public void addLaw(PhysicalLaw l){
        law.add(l);
    }
	public void aggregate(){
	    initialize();

	    boolean isAllSatisfied = true;
	    for (int i = 0; i < law.size(); i++) {
            PhysicalLaw p = (PhysicalLaw) law.get(i);
            boolean isSatisfied = p.apply();
            if(!isSatisfied){
            	isAllSatisfied = false;
            }
        }
	    updatePos();
	    
	    // apply constrain till all satisfied
	    int MAX_ITER = 10;
	    for(int iter = 0; iter < MAX_ITER; iter++){
	    	initialize();
	    	isAllSatisfied = true;
		    for (int i = 0; i < law.size(); i++) {
	            PhysicalLaw p = (PhysicalLaw) law.get(i);
	            if(p.tolerance() > i){
		            boolean isSatisfied = p.apply();
		            if(!isSatisfied){
		            	isAllSatisfied = false;
		            }
	            }
	        }
		    updatePos();
		    if(isAllSatisfied){
		    	break;
		    }
	    }
	}
	
	private void initialize(){
		Mediator med = Mediator.getInstance();
	    Vector vs = med.graph.vertexList;
		for(int i=0; i<vs.size(); i++){
			MassPoint v=(MassPoint) vs.get(i);
			v.velocityList = new Vector();
		}
	}
	
	private void updatePos() {
		Mediator med = Mediator.getInstance();
	    Vector vs = med.graph.vertexList;
	    for(int i=0; i<vs.size(); i++){
			MassPoint v=(MassPoint) vs.get(i);
			double[] vel = new double[2];
			for(int j=0; j < v.velocityList.size(); j++){
				double[] f = (double[]) v.velocityList.get(j);
				Vec.addD(vel, f);
			}
			v.position=Vec.add(v.position, vel);
		}
		
		
	}
	public Object getLaw(int index) {
		return law.get(index);
	}
}
