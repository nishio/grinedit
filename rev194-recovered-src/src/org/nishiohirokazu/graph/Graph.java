package org.nishiohirokazu.graph;

import java.util.Hashtable;
import java.util.Set;

public class Graph {
	
	public Graph(){
		namedDictionaries = new Hashtable<String, Object>();
//		namedDictionaries.put("All", new Hashtable());
		makeDict("All");
		makeDict("Vertex");
		makeDict("Edge");
		makeDict("Law");
	}
	
	protected Hashtable namedDictionaries;
	
	public Hashtable<String, IVertex> getVertexDict(){
		return (Hashtable<String, IVertex>) namedDictionaries.get("Vertex");
	}
	public Hashtable<String, IEdge> getEdgeDict(){
		return (Hashtable<String, IEdge>) namedDictionaries.get("Edge");
	}
	public Hashtable<String, Object> getLawDict(){
		return (Hashtable<String, Object>) namedDictionaries.get("Law");
	}

	public void addVertex(RenderableVertex v, String name){
		addObj("Vertex", name, v);
	}

	public void addEdge(RenderableEdge e, String name) {
		addObj("Edge", name, e);
	}

	public RenderableVertex getVertex(String value) {
		Hashtable vertexDict = (Hashtable) namedDictionaries.get("Vertex");
		RenderableVertex result = (RenderableVertex) vertexDict.get(value);
		assert result != null;
		return result;

	}

	public String addObj(String dictName, String name, Object obj){
		IGRINObject o = (IGRINObject) obj;
		o.setId(name);
		Hashtable dict = (Hashtable) namedDictionaries.get(dictName);
		synchronized (dict) {
			dict.put(name, o);
		}
		dict = (Hashtable) namedDictionaries.get("All");
		synchronized (dict) {
			dict.put(name, o);
		}
		return name;
	}

	/**
	 * 新しい空の辞書を作る
	 * Make new blank dictionary.
	 * @param dictName
	 * @return
	 */
	public Hashtable makeDict(String dictName){
		if(namedDictionaries.containsKey(dictName)){
			throw new RuntimeException(dictName + "という名前の辞書はすでに存在します。");
		}
		Hashtable result = new Hashtable();
		namedDictionaries.put(dictName, result);
		return result;
	}
	
	
	public Hashtable getNamedDict(String dictName){
		Hashtable namedDict = (Hashtable) namedDictionaries.get(dictName);
		if(namedDict == null){
			throw new RuntimeException(dictName + "という名前の辞書はありません");
		}
		return namedDict;
	}

	public Set getDictNames() {
		return namedDictionaries.keySet();

	}

}
