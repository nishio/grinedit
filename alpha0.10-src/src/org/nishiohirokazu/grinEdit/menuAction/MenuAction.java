/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/04/08
 *
 */
package org.nishiohirokazu.grinEdit.menuAction;


import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.nishiohirokazu.grinEdit.Mediator;

/**
 * @author nishio
 *
 */
public class MenuAction implements SelectionListener {
    public Mediator med;
	public MenuAction(Mediator med) {
		this.med=med;
	}
	public MenuAction() {
	}
	public void widgetSelected(SelectionEvent arg0) {
	}
	public void widgetDefaultSelected(SelectionEvent arg0) {
	}
}
