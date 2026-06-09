package org.nishiohirokazu.grinEdit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;

public class UtilXMLRPC {
	public static Vector Point2Vector(Point p){
		Vector result = new Vector();
		result.add(new Integer(p.x));
		result.add(new Integer(p.y));
		return result;
	}
	public static Point Vector2Point(Vector v){
		int x = ((Integer)v.get(0)).intValue();
		int y = ((Integer)v.get(1)).intValue();
		return new Point(x, y);
	}
	public static Vector Color2Vector(Color c){
		Vector result = new Vector();
		result.add(new Integer(c.getRed()));
		result.add(new Integer(c.getGreen()));
		result.add(new Integer(c.getBlue()));
		return result;
	}
	public static Vector Vec2Vector(double[] v){
		Vector result = new Vector();
		result.add(Double.valueOf(v[0]));
		result.add(Double.valueOf(v[1]));
		return result;
	}
	public static double[] Vector2Vec(Vector v){
		double[] result = new double[2];
		result[0] = ((Double)v.get(0)).doubleValue();
		result[1] = ((Double)v.get(1)).doubleValue();
		return result;
	}
	public static double ToDouble(Object o) {
		return ((Double) o).doubleValue();
		
	}
	public static Object getParams(Object target) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
		Class[] signature = {};
		Method m = target.getClass().getMethod("rpc_getParams", signature);
	
		Object[] m_params = {};
		return m.invoke(target, m_params);
	}
	static void modParams(Object target, Hashtable params) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
		Enumeration keys = params.keys();
		while(keys.hasMoreElements()){
			String key = (String) keys.nextElement();
			Class[] signature = {Object.class};
			Method m = target.getClass().getMethod("rpc_" + key, signature);
			Object[] m_params = {params.get(key)};
			m.invoke(target, m_params);
		}
	}
	static Object makeObjForName(String name, Hashtable params) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
		Class cls;
		try {
			cls = Class.forName(name);
		} catch (ClassNotFoundException e) {
			cls = Class.forName("org.nishiohirokazu.graph." + name);
		}
		Object v = cls.newInstance();
		UtilXMLRPC.modParams(v, params);
		
		return v;
	}
	public static int getIntValue(Object o) {
		if(o instanceof Integer){
			return ((Integer) o).intValue();
		}else if(o instanceof Long){
			return ((Long) o).intValue();
		}else{
			System.out.println(o.getClass());
			return 0;
		}

	}
}
