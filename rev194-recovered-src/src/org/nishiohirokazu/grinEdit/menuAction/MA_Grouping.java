package org.nishiohirokazu.grinEdit.menuAction;

import java.util.Hashtable;

import org.eclipse.swt.events.SelectionEvent;
import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.grinEdit.UtilUniqName;
import org.nishiohirokazu.layout.PL_RigidBody;

public class MA_Grouping extends MenuAction {

	public void widgetSelected(SelectionEvent arg0) {
		med = Mediator.getInstance();
		String name = UtilUniqName.getUniqName("group");
		Hashtable law = med.getNamedDict("Law");

		law.put(name,
				new PL_RigidBody(med.getSelectedVertex()));
	}


}
