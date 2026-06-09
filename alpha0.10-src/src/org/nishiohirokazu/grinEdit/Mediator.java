/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/04/08
 *
 */
package org.nishiohirokazu.grinEdit;

import java.util.Vector;
import java.util.Iterator;

import org.apache.xmlrpc.WebServer;
import org.nishiohirokazu.graph.RenderableGraph;
import org.nishiohirokazu.graph.RenderableVertex;
import org.nishiohirokazu.grinEdit.mouseOperation.MouseMediator;
import org.nishiohirokazu.layout.SpringEdge;
import org.nishiohirokazu.swt.MenuWrapper;

/**
 * @author nishio
 * 
 */
public class Mediator {
	private static Mediator instance;
	public static Mediator getInstance() {
		if(instance == null){
			instance = new Mediator();
		}
		return instance;
	}

	public Vector getSelectedEdges() {
		return selection.getEdges();
	}

	public Vector getSelectedVertex() {
		return selection.getVertex();
	}

	public Vector getMarginalEdges() {
		return selection.getMarginalEdges();
	}

	public void setSelection(Vector vertexes) {
		selection.set(vertexes);
	}

	public MouseMediator getMouseMediator() {
		if(mouseMed == null){
			mouseMed = new MouseMediator();
		}
		return mouseMed;
	}

	public Selection selection;

	/**
	 * 
	 */
	private Mediator() {
		super();
		selection = new Selection(this);
		graph = new RenderableGraph();
	}

	public void setGraph(RenderableGraph g) {
		graph = g;
	}
	public RenderableGraph getGraph() {
		return graph;
	}

	public Vector getEdges() {
		return graph.edgeList;
	}

	public Vector getVertexList() {
		return graph.vertexList;
	}

	public boolean pause;

	public RenderableGraph graph;

	public MouseMediator mouseMed;

	public Vector getVertexInRange(int left, int top, int right, int bottom) {
		Vector result = new Vector();
		for (int i = 0; i < graph.vertexList.size(); i++) {
			RenderableVertex v = (RenderableVertex) graph.vertexList.get(i);
			double[] pos = v.screenPos;
			if ((left - pos[0]) * (right - pos[0]) < 0) {
				if ((top - pos[1]) * (bottom - pos[1]) < 0) {
					result.add(v);
				}
			}
		}
		return result;
	}

	public MenuWrapper getMenuWrapper() {
	    if(menuWrapper == null){
	        menuWrapper = new MenuWrapper(Infrastructure.getShell());
	    }
	    return menuWrapper;
	}

	private MenuWrapper menuWrapper;
	private XMLRPCHandler xmlrpc_handler;
	


	public String[] getMouseOperationNames(){
		String result[] = new String[mouseMed.oparations.size()]; 
	    Iterator iter = mouseMed.oparations.keySet().iterator();
	    int i = 0;
	    while(iter.hasNext()) {
	        String name=(String) iter.next();
	        result[i] = name;
	        i++;
        }
	    return result;
	}
	
	public void setServer(WebServer server){
		Infrastructure.server = server;
	}
	
	public XMLRPCHandler getXMLRPCHandler(){
		if(xmlrpc_handler == null){
			xmlrpc_handler = new XMLRPCHandler();
		}
		return xmlrpc_handler;
	}

	public Vector getLawList() {
		return graph.aggregator.law;
	}
	
	public void setDefaultSpringStrength(double d){
		if(graph.isDefaultSetting()){
			SpringEdge sp = (SpringEdge) graph.aggregator.getLaw(0);
			sp.defaultSpringStrength = d;
		}
	}
	public double getDefaultSpringStrength(){
		if(graph.isDefaultSetting()){
			SpringEdge sp = (SpringEdge) graph.aggregator.getLaw(0);
			return sp.defaultSpringStrength;
		}
		return -1.0;
	}
	
}
