/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/04/30
 *
 */
package org.nishiohirokazu.grinEdit.menuAction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Set;

import org.eclipse.swt.events.SelectionEvent;
import org.ho.yaml.Yaml;
import org.nishiohirokazu.graph.IGRINObject;
import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.swt.SWTUtil;

/**
 * @author nishio
 * 
 */
public class MA_SaveAsYaml extends MenuAction {
	public MA_SaveAsYaml(Mediator med) {
		super(med);
	}

	public void widgetSelected(SelectionEvent arg0) {

		Hashtable root = new Hashtable();
		root.put("version", "alpha0.20");
		Set<String> names = med.getDictNames();
		for (String name : names) {
			if (name == "All") {
				continue;
			}
			Hashtable<String, Object> dict = med.getNamedDict(name);
			Hashtable newDict = new Hashtable();
			for (String key : dict.keySet()) {
				Object value = dict.get(key);
				if (value instanceof IGRINObject) {
					Hashtable entry = new Hashtable();
					IGRINObject v = (IGRINObject) value;
					entry.put("classname", v.getClass().getName());
					entry.put("params", v.getParams());
					newDict.put(key, entry);
				} else {
					throw new RuntimeException();
				}
			}
			root.put(name, newDict);
		}

		String filename = SWTUtil.getSaveFilename();
		if (filename != null) {
			try {
				Yaml.dump(root, new File(filename), true);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

}
