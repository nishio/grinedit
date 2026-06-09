/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/04/30
 *
 */
package org.nishiohirokazu.grinEdit.menuAction;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;
import org.nishiohirokazu.grinEdit.Infrastructure;
import org.nishiohirokazu.grinEdit.Mediator;

/**
 * @author nishio
 *
 */
public class MA_Capture extends MenuAction {
    public MA_Capture(Mediator med) {
		super(med);
	}

	public void widgetSelected(SelectionEvent arg0) {
	    med.pause=true;
	    FileDialog dlg = new FileDialog(Infrastructure.getShell() ,SWT.SAVE);
	    String filename = dlg.open();
	    if(filename != null){
	        Infrastructure.getDoubleBufferer().saveImage(filename);
	    }
	    med.pause=false;
	}

    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

}
