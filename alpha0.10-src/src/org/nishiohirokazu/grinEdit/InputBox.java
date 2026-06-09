/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/09/11
 *
 */
package org.nishiohirokazu.grinEdit;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.python.core.PyObject;
import org.python.core.PyString;

/**
 *
 * @author nishio
 */
public class InputBox{

    public static PyObject openEvaluable(String title, String message, String defaultValue) {
        InputDialog dlg = new InputDialog(
                Infrastructure.getShell(), 
                title, 
                message,
                defaultValue, 
                null
            );

            if (dlg.open() == Dialog.OK){
                return new PyString(dlg.getValue());
            }else{
                return null;
            }
    }

    /**
     *
     */

    public void widgetDefaultSelected(SelectionEvent arg0) {
    }

}
