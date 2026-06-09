/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/04/30
 *
 */
package org.nishiohirokazu.grinEdit.menuAction;

import org.eclipse.swt.events.SelectionEvent;
import org.nishiohirokazu.grinEdit.Infrastructure;
import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.swt.SWTUtil;

/**
 * @author nishio
 *
 */
public class MA_RunScript extends MenuAction {
    String defaultScript;
    public MA_RunScript(Mediator med) {
		super(med);
	}
    public MA_RunScript(Mediator med, String script) {
		super(med);
		defaultScript = script;
	}

	public void widgetSelected(SelectionEvent arg0) {
	    med.pause=true;
		String file;
		if(defaultScript != null){
		    file = defaultScript;
		}else{
			file = SWTUtil.getFilename();
		}
		if(file != null){
		    Infrastructure.getPyi().execfile(file);
		}
	    med.pause=false;
	}

    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

}
