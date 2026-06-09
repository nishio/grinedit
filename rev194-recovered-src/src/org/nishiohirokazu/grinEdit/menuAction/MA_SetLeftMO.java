/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/04/10
 *
 */
package org.nishiohirokazu.grinEdit.menuAction;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.nishiohirokazu.grinEdit.mouseOperation.MouseMediator;
import org.nishiohirokazu.grinEdit.mouseOperation.MouseOperation;

/**
 * @author nishio
 * 
 */
public class MA_SetLeftMO implements SelectionListener {

	private MouseMediator mouseMed;

	private MouseOperation mo;

	/**
	 * 
	 */
	public MA_SetLeftMO(MouseMediator mouseMed, MouseOperation mo) {
		this.mouseMed = mouseMed;
		this.mo = mo;
	}

	public void widgetSelected(SelectionEvent arg0) {
		mouseMed.MOLeft = mo;
	}

	public void widgetDefaultSelected(SelectionEvent arg0) {
	}

}
