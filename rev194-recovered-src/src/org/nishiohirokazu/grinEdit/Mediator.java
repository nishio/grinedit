/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/04/08
 *
 */
package org.nishiohirokazu.grinEdit;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.xmlrpc.WebServer;
import org.eclipse.swt.widgets.Shell;
import org.nishiohirokazu.graph.DefaultGraph;
import org.nishiohirokazu.graph.Graph;
import org.nishiohirokazu.graph.IEdge;
import org.nishiohirokazu.graph.IHasScreenPos;
import org.nishiohirokazu.graph.IVertex;
import org.nishiohirokazu.grinEdit.mouseOperation.MouseMediator;
import org.nishiohirokazu.layout.PL_SpringEdge;
import org.nishiohirokazu.vector.Vec;

/**
 * @author nishio
 * 
 */
public class Mediator {
	private static Mediator instance;

	public static Mediator getInstance() {
		if (instance == null) {
			instance = new Mediator();
		}
		return instance;
	}

	private static ILayout layoutEngine;
	
	public void setLayoutEngine(String classname) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		layoutEngine = (ILayout) loadClass(classname).newInstance();
	}
	public ILayout getLayoutEngine() {
		if(layoutEngine == null){
			setLayoutEngine(new SimpleLayout());
		}
		return layoutEngine;
	}

	public static void setLayoutEngine(ILayout layoutEngine) {
		Mediator.layoutEngine = layoutEngine;
	}

	public Vector getSelectedEdges() {
		return selection.getEdges();
	}

	static String VERSION_STR = "GRINEdit alpha 0.20";

	private Shell shell;

	public List getSelectedVertex() {
		return selection.getVertex();
	}

	public Vector getMarginalEdges() {
		return selection.getMarginalEdges();
	}

	public void setSelection(Vector vertexes) {
		selection.set(vertexes);
	}

	public MouseMediator getMouseMediator() {
		if (mouseMed == null) {
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
		graph = new DefaultGraph();
	}

	static String[] classPrefix = {
		"",
		"org.nishiohirokazu.graph.",
		"org.nishiohirokazu.layout."
	};

	public Class loadClass(String classname) throws ClassNotFoundException{
		Class cls = null;
		for(int i = 0; i < classPrefix.length; i++){
			try {
				cls = Class.forName(classPrefix[i] + classname);
			}catch(ClassNotFoundException e){
				continue;
			}
			break;
		}
		if(cls == null){
			if(pluginClassLoader == null){
				pluginClassLoader = new PluginClassLoader();
			}
			cls = pluginClassLoader.loadClass(classname);
			if(cls == null){
				throw new ClassNotFoundException(classname);
			}
		}
		return cls;
	}
	
	public void setGraph(Graph g) {
		graph = g;
	}

	public Shell getShell() {
	    if(shell == null){
	        shell = new Shell(Infrastructure.getDisplay());
	    }
	    return shell;
	}

	public Graph getGraph() {
		return graph;
	}

	public Hashtable getEdgeDict() {
		return graph.getEdgeDict();
	}

	public Hashtable getVertexDict() {
		return graph.getVertexDict();
	}

	public IVertex getVertex(String name) {
		return graph.getVertexDict().get(name);
	}

	public IEdge getEdge(String name) {
		return graph.getEdgeDict().get(name);
	}

	public Collection getVertexList() {
		return graph.getVertexDict().values();
	}

	public boolean pause;

	public boolean autoLayout = true;

	public boolean rendering = true;

	public Graph graph;

	public MouseMediator mouseMed;

	/**
	 * 選択範囲内に存在する頂点のリストを返す
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @return
	 */
	public List getVertexInRange(int left, int top, int right, int bottom) {
		Vector<IVertex> result = new Vector<IVertex>();

		for(IVertex v: graph.getVertexDict().values()){
			IHasScreenPos sv = (IHasScreenPos) v;
			double[] pos = sv.getScreenPos();
			if ((left - pos[0]) * (right - pos[0]) < 0) {
				if ((top - pos[1]) * (bottom - pos[1]) < 0) {
					result.add(v);
				}
			}
		}
		return result;
	}

	/**
	 * 指定された点(スクリーン座標系)に最も近い頂点を返す
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public IVertex getNearestVertex(double x, double y) {
		return getNearestVertex(x, y, Double.MAX_VALUE);
	}

	/**
	 * 指定された点(スクリーン座標系)に最も近い頂点を返す。 半径radius以内に頂点が存在しない場合はnullを返す。
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 * @return
	 */
	public IVertex getNearestVertex(double x, double y, double radius) {
		double[] tpos = { x, y };
		double minDist = radius;
		IVertex result = null;
		for (IVertex v : graph.getVertexDict().values()) {
			IHasScreenPos sv = (IHasScreenPos) v;
			double[] pos = sv.getScreenPos();
			if (pos != null) {
				double dist = Vec.sqDistance(tpos, pos);
				if (dist < minDist) {
					minDist = dist;
					result = v;
				}
			}
		}
		return result;
	}

	private CommonGateway xmlrpc_handler;

	int[] canvasSize = { 0, 0 };

	private ClassLoader pluginClassLoader;

	private DefaultRenderer renderer;

	public int[] getCanvasSize() {
		return canvasSize;
	}

	public String[] getMouseOperationNames() {
		String result[] = new String[mouseMed.oparations.size()];
		Iterator iter = mouseMed.oparations.keySet().iterator();
		int i = 0;
		while (iter.hasNext()) {
			String name = (String) iter.next();
			result[i] = name;
			i++;
		}
		return result;
	}

	public void setServer(WebServer server) {
		Infrastructure.server = server;
	}

	public CommonGateway getCommonGateway() {
		if (xmlrpc_handler == null) {
			xmlrpc_handler = CommonGateway.getInstance();
		}
		return xmlrpc_handler;
	}

	public void setDefaultSpringStrength(double d) {
		Hashtable law = getNamedDict("Law");
		PL_SpringEdge sp = (PL_SpringEdge) law.get("PL_SpringEdge");
		sp.defaultSpringStrength = d;
	}

	public double getDefaultSpringStrength() {
		Hashtable law = getNamedDict("Law");
		PL_SpringEdge sp = (PL_SpringEdge) law.get("PL_SpringEdge");
		return sp.defaultSpringStrength;
	}

	public void render(Object bg) {
		if(renderer == null){
			renderer = new DefaultRenderer();
		}
		renderer.render(bg);
	}

	public void setRenderer(String classname) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		renderer = (DefaultRenderer) loadClass(classname).newInstance();
	}
	
	public Hashtable getNamedDict(String dictName){
		return graph.getNamedDict(dictName);
	}
	
	public Object getObject(String objName) {
		Hashtable namespace = graph.getNamedDict("All");
		Object result = namespace.get(objName);
		if(result == null){
			throw new RuntimeException(objName + "という名前のオブジェクトはありません");
		}
		return result;
	}
	
	public Set getDictNames(){
		return graph.getDictNames();
	}
}
