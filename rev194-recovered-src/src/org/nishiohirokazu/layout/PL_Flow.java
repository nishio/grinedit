/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/05/22
 *
 */
package org.nishiohirokazu.layout;

import java.util.Hashtable;
import java.util.Map;

import org.nishiohirokazu.graph.IEdge;
import org.nishiohirokazu.graph.IVertex;
import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.grinEdit.UtilCast;
import org.nishiohirokazu.vector.Vec;

/**
 * 子が親の下に来るフローレイアウト用の物理法則
 * @author nishio
 */

public class PL_Flow extends PhysicalLaw {
	private double kx = 0.01;
	private double ky1 = 0.3;
	private double ky2 = 0.1;
	private double length = 3.0;
	private Map<IEdge, Double> normalLength = new Hashtable<IEdge, Double>();
	private Map<IEdge, Double> springStrength = new Hashtable<IEdge, Double>();
	
	private String targetName;

	public PL_Flow() {
		targetName = "Edge";
	}
	/**
	 * 
	 * @param target: Vector of Edge
	 */
	public PL_Flow(String targetName){
		this.targetName = targetName;
	}
	

	public boolean apply(int iter) {
		Hashtable<String, IEdge> target;
		target = Mediator.getInstance().getNamedDict(targetName);
		if(iter == 0){
			for (IEdge e: target.values()) {
				IVertex v1 = e.getV1();
				IVertex v2 = e.getV2();
				
				if(v1 == null || v2 == null){
					continue;
				}
				
				IMassPoint mv1 = (IMassPoint) v1;
				IMassPoint mv2 = (IMassPoint) v2;
				double[] dir = Vec.sub(mv2.getPosition(), mv1.getPosition());
//				double dist = Vec.mag(dir);

//				double k = getSpringStrength(e);
//				double length = getNormalLength(e);
							
//				double power = (length - dist) * k;
//				double force[] = Vec.scale(ndir, power);
				double fx = calcFx(dir[0]);
				double fy = calcFy(dir[1]);
				double[] force = {fx, fy};
				mv1.getDVelList().add(force);
				mv2.getDVelList().add(Vec.inv(force));
			}
		}
		return true;
	}

	private double calcFy(double d) {
		if(d > length){
			return (d - length) * ky1;
		}
		return (d - length) * ky2;
	}
	private double calcFx(double d) {
		return kx * d;
	}

	
	
	public void setKx(Object o){
		kx = UtilCast.o2double(o); 
	}
	public void setKy1(Object o){
		ky1 = UtilCast.o2double(o); 
	}
	public void setKy2(Object o){
		ky2 = UtilCast.o2double(o); 
	}

	
	public Hashtable getParams(){
		Hashtable result = super.getParams();
		result.put("kx", Double.valueOf(kx));
		result.put("ky1", Double.valueOf(ky1));
		result.put("ky2", Double.valueOf(ky2));
		return result;
	}

	public void setTargetName(Object o){
		targetName = o.toString();
	}
	
	public void putNormalLength(String name, Object value) {
		normalLength.put(
				(IEdge) Mediator.getInstance().getObject(name),
				UtilCast.o2double(value));
	}

	public void putNormalLength(IEdge o, Object value) {
		normalLength.put(o, UtilCast.o2double(value));
	}

	public void removeNormalLength(String name) {
		normalLength.remove(Mediator.getInstance().getObject(name));
	}

	public void setNormalLength(Object o) {
		normalLength = new Hashtable<IEdge, Double>();
		Map map = UtilCast.maplike2map(o);
		Mediator med = Mediator.getInstance();
		for (Object key : map.keySet()) {
			normalLength.put(
				med.getEdge(key.toString()),
				UtilCast.o2double(map.get(key)));
		}
	}

	public void putSpringStrength(String name, Object value) {
		springStrength.put(
				(IEdge) Mediator.getInstance().getObject(name),
				UtilCast.o2double(value));
	}

	public void putSpringStrength(IEdge o, Object value) {
		springStrength.put(o, UtilCast.o2double(value));
	}

	public void removeSpringStrength(String name) {
		springStrength.remove(Mediator.getInstance().getObject(name));
	}

	public void setSpringStrength(Object o) {
		springStrength = new Hashtable<IEdge, Double>();
		Map map = UtilCast.maplike2map(o);
		Mediator med = Mediator.getInstance();
		for (Object key : map.keySet()) {
			springStrength.put(
				med.getEdge(key.toString()),
				UtilCast.o2double(map.get(key)));
		}
	}

	
}
