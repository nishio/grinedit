/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/09/20
 *
 */
package org.nishiohirokazu.layout;

import java.util.Hashtable;
import java.util.Vector;

import org.nishiohirokazu.graph.IGRINObject;
import org.nishiohirokazu.graph.IVertex;
import org.nishiohirokazu.grinEdit.UtilCast;
/**
 * 質点クラス：位置、速度、加速度を持つ
 * @author nishio
 */
public class MassPoint implements IGRINObject, IMassPoint, IVertex{
	private Vector dVelList = new Vector();
	private double[] position = new double[2];
	private double[] velocity = new double[2];
	private String id;

    public MassPoint() {

    }

    public Hashtable getParams(){
    	Hashtable result = new Hashtable();
    	if(position != null){
	    	result.put("position",
	    			UtilCast.Vec2Vector(position));
    	}
    	if(velocity != null){
        	result.put("velocity",
        			UtilCast.Vec2Vector(velocity));
    	}
    	return result;
    }
    
    public void setPosition(Object o){
    	position = UtilCast.o2doubleArray(o);
    }
    public void setVelocity(Object o){
    	velocity = UtilCast.o2doubleArray(o);
    }
	public void setId(Object o){
		id = o.toString();
	}
	public String getId(){
		return id;
	}

	public Vector getDVelList() {
		return dVelList;
	}

	public void setDVelList(Vector velList) {
		dVelList = velList;
	}

	public double[] getPosition() {
		return position;
	}

	public void setPosition(double[] position) {
		this.position = position;
	}

	public double[] getVelocity() {
		return velocity;
	}

	public void setVelocity(double[] velocity) {
		this.velocity = velocity;
	}

}
