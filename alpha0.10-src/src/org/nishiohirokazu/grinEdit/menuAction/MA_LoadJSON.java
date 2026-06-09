package org.nishiohirokazu.grinEdit.menuAction;

import java.io.FileReader;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.swt.events.SelectionEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.swt.SWTUtil;

public class MA_LoadJSON extends MenuAction {

	public MA_LoadJSON() {
		super();
	}
	
	private Hashtable JSONObject2Hash(JSONObject jo){
		Hashtable result = new Hashtable();
		for (Iterator iter = jo.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			Object value = jo.get(key);
			if(value instanceof JSONObject){
				value = JSONObject2Hash((JSONObject) value);
			}else if(value instanceof JSONArray){
				value = JSONArray2Vector((JSONArray) value);
			}
			result.put(key, value);
		}
		return result;

	}


	private Object JSONArray2Vector(JSONArray ja) {
		Vector result = new Vector();
		for (Iterator iter = ja.iterator(); iter.hasNext();) {
			Object item = iter.next();
			if(item instanceof JSONObject){
				item = JSONObject2Hash((JSONObject) item);
			}else if(item instanceof JSONArray){
				item = JSONArray2Vector((JSONArray) item);
			}
			result.add(item);
		}
		return result;
	}

	public void widgetSelected(SelectionEvent arg0) {
		String filename = SWTUtil.getFilename();

		JSONObject root = new JSONObject();
		JSONParser jp = new JSONParser();
		
		try {
			root = (JSONObject) jp.parse(new FileReader(filename));

			med = Mediator.getInstance();
			JSONArray vertexList = (JSONArray) root.get("vertexList");
			for(int i = 0; i < vertexList.size(); i++){
				JSONObject v = (JSONObject) vertexList.get(i);
				String type = (String) v.get("name");
				Hashtable params = (JSONObject) v.get("params");//JSONObject2Hash((JSONObject) v.get("params"));
				med.getXMLRPCHandler().addVertex(type, params);
			}
			// TODO:
//			Vector edgeList = (Vector) root.get("edgeList");
//			for(int i = 0; i < vertexList.size(); i++){
//				
//			}
//			Vector lawList = (Vector) root.get("lawList");
//			for(int i = 0; i < vertexList.size(); i++){
//				
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
