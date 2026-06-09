package org.nishiohirokazu.grinEdit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

/**
 * @author nishio
 *
 */
public class UtilXMLRPC {
	public static Object getParams(Object target) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchMethodException{
		try{
			Class[] signature = {};
			Method m = target.getClass().getMethod("getParams", signature);
			Object[] m_params = {};
			return m.invoke(target, m_params);
		}catch(InvocationTargetException e){
			e.printStackTrace();
			System.out.println("cause:");
			e.getCause().printStackTrace();
			return null;
		}
	}
	static void modParams(Object target, Map params) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
		Iterator keys = params.keySet().iterator();
		while(keys.hasNext()){
			String key = (String) keys.next();
			// キーからメソッド名を作成、例：label→setLabel
			String methodName = "set" + String.valueOf(key.charAt(0)).toUpperCase() + key.substring(1);
			
			try {
				invoke(target, methodName, params.get(key));
			} catch (NoSuchMethodException e) {
				// 存在しないメソッドに値を入れようとした場合には
				// propertiesに入れる。
				System.out.println(methodName + " not found.");
				
				if(target instanceof IHasProperty){
					IHasProperty mp = (IHasProperty) target;
					mp.putProperty(key, params.get(key));
				}else{
					throw new NoSuchMethodException(target.getClass() + "#" + methodName);
				}
			}
		}
	}
	
	static void modID(Object target, String id) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
		invoke(target, "setId", id);
	}
	
	/**
	 * invoke a method.
	 * @param target an object
	 * @param methodName 
	 * @param param
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchMethodException 
	 */
	static private void invoke(Object target, String methodName, Object param) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
		Class[] signature = {Object.class};
		Method m = target.getClass().getMethod(methodName, signature);
		Object[] m_params = {param};
		m.invoke(target, m_params);
	}

	private static Object _makeObjForName(String classname) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, IllegalArgumentException{
		Class cls = Mediator.getInstance().loadClass(classname);
		Object o = cls.newInstance();
		return o;
	}
	
	static Object makeObjForName(String classname, Map params) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
		Object o = _makeObjForName(classname);
		UtilXMLRPC.modParams(o, params);
		return o;
	}
	
	static Object makeObjForName(String classname, Map params, String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
		Object o = _makeObjForName(classname);
		UtilXMLRPC.modID(o, name);
		UtilXMLRPC.modParams(o, params);
		return o;
	}
	
}
