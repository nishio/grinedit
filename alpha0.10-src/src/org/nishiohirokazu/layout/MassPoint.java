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

import org.nishiohirokazu.grinEdit.UtilXMLRPC;
// TODO: MassPointとVertexの違いは？
/**
 * 質点クラス：位置、速度、加速度を持つ
 * @author nishio
 */
public class MassPoint {
	public Vector velocityList = new Vector();
	public double[] position;
	public double[] velocity;
//	public double[] acceleration;
//    public boolean anchored;

    public MassPoint() {

    }

    public Hashtable rpc_getParams(){
    	Hashtable result = new Hashtable();
    	if(position != null){
	    	result.put("position",
	    			UtilXMLRPC.Vec2Vector(position));
    	}
    	if(velocity != null){
        	result.put("velocity",
        			UtilXMLRPC.Vec2Vector(velocity));
    	}
    	return result;
    }
    
    public void rpc_position(Object o){
    	position = UtilXMLRPC.Vector2Vec((Vector) o);
    }
    public void rpc_velocity(Object o){
    	velocity = UtilXMLRPC.Vector2Vec((Vector) o);
    }
}
