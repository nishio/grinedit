package org.nishiohirokazu.grinEdit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.nishiohirokazu.graph.DefaultGraph;


/**
 * Common gateway for XML-RPC, JSON and Jython 
 * @author nishio
 *
 */
public class CommonGateway {
	public static CommonGateway getInstance() {
		return new CommonGateway();
	}
	private Mediator med;
	private Method methods[]; 
	
	private CommonGateway() {
		med = Mediator.getInstance();
		methods = getClass().getDeclaredMethods();
	}
	
	public String addEdge(String edgeType, Hashtable params) throws Throwable{
		return addObject("Edge", edgeType, params);
	}

	public String addEdge(String edgeType, Object params) throws Throwable{
		return addObject("Edge", edgeType, params);
	}

	public String addLaw(String className, Hashtable params) throws Throwable{
		return addObject("Law", className, params);
	}

	public String addLaw(String className, Object params) throws Throwable{
		return addObject("Law", className, params);
	}
	
	public String addVertex(String vertexType, Hashtable params) throws Throwable {
		return addObject("Vertex", vertexType, params);
	}

	public String addVertex(String vertexType, Object params) throws Throwable {
		return addObject("Vertex", vertexType, params);
	}
	
	public String addObject(String namespace, String typename, Hashtable params) throws Throwable{
		return addObject(namespace, typename, (Object) params);
	}

	public String addObject(String namespace, String typename, Object params) throws Throwable{
		Map _params = UtilCast.maplike2map(params);
		String id;

		if(_params.containsKey("id")){
			id = _params.get("id").toString();
		}else{
			id = UtilUniqName.getUniqName(namespace.toLowerCase());
		}
		
		
		Object v;
		try {
			v = UtilXMLRPC.makeObjForName(typename, _params, id);
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
		med.graph.addObj(namespace, id, v);

		return id;
		
	}

	public boolean autoLayout(boolean b){
		boolean result = med.autoLayout;
		med.autoLayout = b;
		return result;
	}


	/**
	 * クエリの複数一括受付
	 * @param v
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Object batch(Vector v) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Vector result = new Vector(v.size());
		for (Iterator i = v.iterator(); i.hasNext();) {
			Hashtable q = (Hashtable) i.next();

			String methodName = (String) q.get("method");
			Object[] params = ((Vector) q.get("params")).toArray();

			for(int j = 0; j < methods.length; j++){
				Method m = methods[j];
				if(methodName.equals(m.getName())){
					result.add(m.invoke(this, params));
					break;
				}
			}
		}
		
		return result;
	}


	/**
	 * frmNamespaceで指定された名前空間からnameで指定された名前のオブジェクトを削除する
	 * @param frmNamespace
	 * @param name
	 * @return
	 */
	public void delObject(String frmNamespace, String name){
		Hashtable namespace = med.getNamedDict(frmNamespace);
		if(namespace == null){
			throw new RuntimeException(frmNamespace + "という名前の名前空間はありません");
		}
		namespace.remove(name);
	}
	public String delEdge(String name){
		delObject("Edge", name);
		return name;
	}

	public String delLaw(String name){
		delObject("Law", name);
		return name;
	}

	public String delVertex(String name){
		delObject("Vertex", name);
		return name;
	}

	/**
	 * 指定された名前空間内のオブジェクトの一覧を返す。
	 * @param dictName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 */
	public Object getObjects(String dictName) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchMethodException{
		Hashtable<String, Object> dict = (Hashtable<String, Object>) med.getNamedDict(dictName);
		Hashtable result = new Hashtable();
		
		for(String name: dict.keySet()){
			result.put(name, UtilXMLRPC.getParams(dict.get(name)));
		}
		return result;
	}


	/**
	 * dictNameで指定された名前空間のnameという名前のオブジェクトの情報を取得する
	 * @param dictName
	 * @param name
	 * @return
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 */
	public Object getParams(String dictName, String name) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchMethodException{
		Hashtable dict = med.getNamedDict(dictName);
		return UtilXMLRPC.getParams(dict.get(name));
	}

	public Object getEdge(String name) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchMethodException{
		return getParams("Edge", name);
	}
	public Object getLaw(String name) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchMethodException{
		return getParams("Law", name);
	}

	public Object getVertex(String name) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchMethodException{
		return getParams("Vertex", name);
	}


	/**
	 * グラフを初期化する。0を返す。
	 * @return
	 */
	public int initGraph(){
		med.graph = new DefaultGraph();
		return 0;
	}
	
	public Object modObject(String namespace, String name, Object params) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Map _namespace = med.getNamedDict(namespace);
		Object target = _namespace.get(name);
		if(target == null){
			throw new RuntimeException(namespace + " named '" + name + "' doesn't exist");
		}
		UtilXMLRPC.modParams(target, UtilCast.maplike2map(params));
		return UtilXMLRPC.getParams(target);
	}
	
	public Object modEdge(String name, Hashtable params) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		return modObject("Edge", name, params);
	}

	public Object modLaw(String name, Hashtable params) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		return modObject("Law", name, params);
	}
	public Object modVertex(String name, Hashtable params) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		return modObject("Vertex", name, params);
	}
	
	
	public void addHandler(String name, String classname, Hashtable params) throws SecurityException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Infrastructure.server.addHandler(name, UtilXMLRPC.makeObjForName(classname, params));
	}

	public void removeHandler(String name) throws SecurityException, IllegalArgumentException{ 
		Infrastructure.server.removeHandler(name);
	}

	public boolean pause(boolean b){
		boolean result = med.pause;
		med.pause = b;
		return result;
	}

	public boolean rendering(boolean b){
		boolean result = med.rendering;
		med.rendering = b;
		return result;
	}
	
	
	/**
	 * make new dictionary
	 * @param dictName
	 */
	public void makeDict(String dictName){
		med.graph.makeDict(dictName);
	}
	
	/**
	 * add existing object to another dictionary.
	 * 既存のオブジェクトを他の辞書に追加する。
	 * @param dictName
	 * @param objName
	 */
	public void addObject(String dictName, String objName) {
		Object obj = med.getObject(objName);
		med.graph.addObj(dictName, objName, obj);
	}
	
	/**
	 * make shallow copy of given dictionary
	 * 与えられた辞書をコピー
	 * @param srcName
	 * @param newName
	 */
	public void copyTable(String srcName, String newName){
		Hashtable srcDict = med.getNamedDict(srcName);
		Hashtable newDict = new Hashtable();
		for(Object key: srcDict.keySet()){
			newDict.put(key, srcDict.get(key));
		}
	}
	
	
	
	/**
	 * Add labeled BoxVertex.
	 * API for simple access.
	 * @param label label shown on vertex
	 * @throws Throwable 
	 * 
	 */
	public String addBoxVertex(String label) throws Throwable{
		Hashtable params = new Hashtable();
		params.put("label", label);
		String result = addVertex("BoxVertex", params);
		return result;
	}
	/**
	 * Add simple black LinearEdge.
	 * API for simple access.
	 * @param v1 name of vertex 1
	 * @param v2 name of vertex 2
	 * @throws Throwable 
	 * 
	 */
	public String addLinearEdge(String v1, String v2) throws Throwable{
		Hashtable params = new Hashtable();
		params.put("v1", v1);
		params.put("v2", v2);
		String result = addEdge("LinearEdge", params);
		return result;
	}

}
