package org.nishiohirokazu.grinEdit;

import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

import org.nishiohirokazu.graph.RenderableEdge;
import org.nishiohirokazu.graph.RenderableGraph;
import org.nishiohirokazu.graph.RenderableVertex;

public class XMLRPCHandler {
	private Mediator med;

	public XMLRPCHandler() {
		med = Mediator.getInstance();
	} 
	
	/**
	 * 
	 * @param vertexType
	 * @param params
	 * @return index of vertex
	 */
	public int addVertex(String vertexType, Hashtable params) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
		boolean bakPause = med.pause; 
		med.pause = true;
		Object v = UtilXMLRPC.makeObjForName(vertexType, params);

		int index = med.graph.vertexList.size();
		med.graph.vertexList.add(v);

		med.pause = bakPause;
		return index;
	}
	
	public Object modVertex(int index, Hashtable params) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Object v = med.graph.vertexList.get(index);
		UtilXMLRPC.modParams(v, params);
		return UtilXMLRPC.getParams(v);
	}
	
	public int addEdge(String edgeType, int v1, int v2, Hashtable params) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException, InstantiationException, ClassNotFoundException{
		boolean bakPause = med.pause; 
		med.pause = true;

		RenderableEdge e = (RenderableEdge) UtilXMLRPC.makeObjForName(edgeType, params);
		e.v1 = (RenderableVertex) med.graph.vertexList.get(v1);
		e.v2 = (RenderableVertex) med.graph.vertexList.get(v2);
		
		int index = med.graph.edgeList.size();
		med.graph.edgeList.add(e);
		
		med.pause = bakPause;
		return index;
	}
	
	public Object modEdge(int index, Hashtable params) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Object v = med.graph.edgeList.get(index);
		UtilXMLRPC.modParams(v, params);
		return UtilXMLRPC.getParams(v);
	}
	
	public int delVertex(int index){
		med.graph.vertexList.remove(index);
		return index;
	}

	public int delEdge(int index){
		med.graph.edgeList.remove(index);
		return index;
	}

	public Object getVertex(int index) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		return UtilXMLRPC.getParams(med.graph.vertexList.get(index));
	}

	public Object getEdge(int index) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		return UtilXMLRPC.getParams(med.graph.edgeList.get(index));
	}

	public Object getLaw(int index) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		return UtilXMLRPC.getParams(med.graph.aggregator.getLaw(index));
	}
	public Object modLaw(int index, Hashtable params) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Object v = med.graph.aggregator.getLaw(index);
		UtilXMLRPC.modParams(v, params);
		return UtilXMLRPC.getParams(v);
	}
	
	
	public boolean pause(boolean b){
		boolean result = med.pause;
		med.pause = b;
		return result;
	}

	public int initGraph(){
		med.graph = new RenderableGraph();
		return 0;
	}

}
