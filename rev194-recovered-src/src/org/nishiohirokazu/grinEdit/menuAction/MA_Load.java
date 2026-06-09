/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/04/08
 *
 */
package org.nishiohirokazu.grinEdit.menuAction;


import org.eclipse.swt.events.SelectionEvent;
import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.swt.SWTUtil;
import org.python.core.PyObject;
import org.python.core.PyString;

/**
 * @author nishio
 *
 */
public class MA_Load extends MenuAction {
	private PyObject pyo;

    public MA_Load(Mediator med, PyObject pyo) {
        super(med);
        this.pyo = pyo;
    }

	public void widgetSelected(SelectionEvent arg0) {
	    med.pause = true;
	    String filename = SWTUtil.getFilename();
	    if(filename != null){
	        pyo.__call__(new PyString(filename));
	    }
	    med.pause = false;
	}
}
