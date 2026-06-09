/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/05/25
 *
 */
package org.nishiohirokazu.grinEdit.menuAction;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.python.core.PyObject;

/**
 *
 * @author nishio
 */
public class PyMenuAction implements SelectionListener {
    private PyObject func;
	public PyMenuAction(PyObject func) {
		this.func=func;
	}
    public void widgetSelected(SelectionEvent arg0) {
        func.__call__();
    }
    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

}
