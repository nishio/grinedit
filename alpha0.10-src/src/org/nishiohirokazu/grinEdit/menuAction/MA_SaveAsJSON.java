package org.nishiohirokazu.grinEdit.menuAction;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.swt.events.SelectionEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.grinEdit.UtilXMLRPC;
import org.nishiohirokazu.swt.SWTUtil;


public class MA_SaveAsJSON extends MenuAction {

	public MA_SaveAsJSON() {
		super();
	}

	private JSONObject hash2json(Hashtable h){
		JSONObject result = new JSONObject();
		Enumeration keys = h.keys();
		while(keys.hasMoreElements()){
			String key = (String) keys.nextElement();
			result.put(key, h.get(key));
		}
		return result;
	}
	private JSONArray buildJSONArray(Vector vec) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		JSONArray result = new JSONArray();
		for(int i = 0; i < vec.size(); i++){
			Object o =  vec.get(i);
			Object params = UtilXMLRPC.getParams(o);
			String name = o.getClass().getName();
			JSONObject item = new JSONObject();
			item.put("name", name);
			item.put("params", hash2json((Hashtable) params));
			result.add(item);
		}
		return result;
	}
	public void widgetSelected(SelectionEvent arg0) {
		JSONObject root = new JSONObject();
		med = Mediator.getInstance();
		root.put("version", "alpha0.1");
		try {
			root.put("vertexList", buildJSONArray(med.getVertexList()));
			root.put("edgeList", buildJSONArray(med.getEdges()));
			root.put("lawList", buildJSONArray(med.getLawList()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		System.out.println(root);
		String filename = SWTUtil.getSaveFilename();
		try {
			FileWriter f = new FileWriter(filename);
			f.write(root.toString());
			f.close();
		} catch (IOException e) {
			// TODO Ž©“®¶¬‚³‚ê‚½ catch ƒuƒƒbƒN
			e.printStackTrace();
		}
	}

}
