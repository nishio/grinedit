package org.nishiohirokazu.grinEdit;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.nishiohirokazu.swt.ColorHolder;
import org.python.core.PyDictionary;
import org.python.core.PyFloat;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PySequence;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;


/**
 * utility class to cast an object to another type.
 * @author nishio
 *
 */
public class UtilCast {

	public static Vector Color2Vector(Color c){
		Vector<Integer> result = new Vector<Integer>();
		result.add(new Integer(c.getRed()));
		result.add(new Integer(c.getGreen()));
		result.add(new Integer(c.getBlue()));
		return result;
	}

	public static Object Color2Vector(java.awt.Color c) {
		Vector<Integer> result = new Vector<Integer>();
		result.add(new Integer(c.getRed()));
		result.add(new Integer(c.getGreen()));
		result.add(new Integer(c.getBlue()));
		return result;
	}

	public static int getIntValue(Object o) {
		if(o instanceof Number){
			return ((Number) o).intValue();
		}else if(o instanceof PyInteger){
			return ((PyInteger)o).getValue();
		}else if(o instanceof PyFloat){
			return (int) ((PyFloat)o).getValue();
		}else{
			System.out.println(o.getClass());
			throw new NotImplementedException();
		}
	
	}

	/**
	 * translate map-like object(Map, PyDictionary) to PyDictionary
	 * 
	 */
	public static Map maplike2map(Object m) {
		Map result;
		if(m instanceof Map){
			result = (Map) m;
		}else if(m instanceof PyDictionary){
			PyDictionary dict = (PyDictionary) m;
			result = new Hashtable();
			PyList keys = dict.keys();
			for(int i = 0; i < keys.__len__(); i++){
				PyObject k = keys.__getitem__(i);
				result.put(k.toString(), dict.__getitem__(k));
			}
		}else{
			throw new RuntimeException(m.getClass() + "is not PyDictionary nor Map");
		}

		return result;
		
	}

	public static boolean o2bool(Object o){
 		if (o instanceof Boolean) {
 			Boolean b = (Boolean) o;
 			return b.booleanValue();
 		}
 		if (o instanceof Integer) {
 			Integer i = (Integer) o;
 			return (i.intValue() != 0);
 		}
 		if (o instanceof PyInteger) {
 			PyInteger i = (PyInteger) o;
 			return (i.getValue() != 0);
 		}
 		if (o instanceof PyFloat) {
 			PyFloat i = (PyFloat) o;
 			return (i.getValue() != 0.0);
 		}
 		throw new NotImplementedException();
	}

	public static Color o2swtColor(Object o) {
		int[] c = o2intArray(o);
		return ColorHolder.get(c[0], c[1], c[2]);
	}

	public static java.awt.Color o2awtColor(Object o) {
		if(o instanceof java.awt.Color){
			return (java.awt.Color) o;
		}
		int[] c = o2intArray(o);
		return org.nishiohirokazu.awt.ColorHolder.get(c[0], c[1], c[2]);
	}

	public static double o2double(Object o) {
 		if (o instanceof Number) {
 			Number n = (Number) o;
 			return n.doubleValue();
 		}else if(o instanceof PyFloat){
 			PyFloat f = (PyFloat) o;
 			return f.getValue();
 		}else if(o instanceof PyInteger){
 			PyInteger i = (PyInteger) o;
 			return i.getValue();
 		}
 		throw new RuntimeException();
	}

	/**
	 * get Object and return double[] (Usually double[2] which represent position) 
	 * @param o Object(PyTuple, Vector, ArrayList)
	 * @return double[]
	 */
	public static double[] o2doubleArray(Object o) {
		double[] result;
		if(o instanceof PySequence){
			PySequence a = (PySequence) o;
			int n = a.__len__();
			result = new double[n];
			for(int i = 0; i < n; i++){
				PyObject v = a.__getitem__(i);
				result[i] = v.__float__().getValue();
			}
		}else if(o instanceof List){
			List a = (List) o;
			int n = a.size();
			result = new double[n];
			for(int i = 0; i < n; i++){
				Number v = (Number) a.get(i);
				result[i] = v.doubleValue();
			}
		}else if(o instanceof double[]){
			result = (double[]) o;
		}else{
			System.out.println(o.getClass());
			throw new NotImplementedException();
		}
		return result;
	}

	public static int[] o2intArray(Object o){
		int[] result;
		if(o instanceof PySequence){
			PySequence a = (PySequence) o;
			int n = a.__len__();
			result = new int[n];
			for(int i = 0; i < n; i++){
				PyObject v = a.__getitem__(i);
				result[i] = v.__int__().getValue();
			}
		}else if(o instanceof List){
			List a = (List) o;
			int n = a.size();
			result = new int[n];
			for(int i = 0; i < n; i++){
				Number v = (Number) a.get(i);
				result[i] = v.intValue();
			}
			
		}else{
			System.out.println(o.getClass());
			throw new NotImplementedException();
		}
		return result;
	}

	/**
	 * translate org.eclipse.swt.graphics.Point to java.util.Vector
	 * @param p Point
	 * @return Vector
	 */
	public static Vector point2Vector(Point p){
		if(p == null){
			return null;
		}
		Vector<Integer> result = new Vector<Integer>();
		result.add(new Integer(p.x));
		result.add(new Integer(p.y));
		return result;
	}

	/**
	 * translate org.eclipse.swt.graphics.Point to java.util.Vector
	 * @param p Point
	 * @return Vector
	 */
	public static Vector point2Vector(java.awt.Point p){
		if(p == null){
			return null;
		}
		Vector<Integer> result = new Vector<Integer>();
		result.add(new Integer(p.x));
		result.add(new Integer(p.y));
		return result;
	}

	
	public static Vector Vec2Vector(double[] v){
		Vector<Double> result = new Vector<Double>();
		result.add(Double.valueOf(v[0]));
		result.add(Double.valueOf(v[1]));
		return result;
	}

	/**
	 * java.util.Vector‚ðorg.eclipse.swt.graphics.Point‚É•ÏŠ·
	 * @param v
	 * @return
	 */
	public static Point vector2Point(Vector v){
		int x = getIntValue(v.get(0));
		int y = getIntValue(v.get(1));
		return new Point(x, y);
	}

	/**
	 * java.util.Vector‚ðDouble[2]‚É•ÏŠ·
	 * @param v
	 * @return
	 */
	public static double[] Vector2Vec(Vector v){
		double[] result = new double[2];
		result[0] = ((Double)v.get(0)).doubleValue();
		result[1] = ((Double)v.get(1)).doubleValue();
		return result;
	}

	public static java.awt.Point o2awtPoint(Object o) {
		if(o instanceof List){
			List v = (List) o;
			int x = getIntValue(v.get(0));
			int y = getIntValue(v.get(1));
			return new java.awt.Point(x, y);
		}else if (o instanceof PySequence) {
			PySequence v = (PySequence) o;
			int x = getIntValue(v.__getitem__(0));
			int y = getIntValue(v.__getitem__(1));
			return new java.awt.Point(x, y);
			
		}
		throw new NotImplementedException();
	}

	public static List o2list(Object o) {
		if(o instanceof List){
			return (List) o;
		}
		throw new NotImplementedException();
	}
}
