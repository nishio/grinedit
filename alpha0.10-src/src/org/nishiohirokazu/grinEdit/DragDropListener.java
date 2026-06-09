/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/10/03
 *
 */
package org.nishiohirokazu.grinEdit;

import org.eclipse.swt.dnd.*;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

/**
 * 
 * @author nishio
 */
public class DragDropListener implements DropTargetListener {

    /**
     *  
     */
    public DragDropListener() {
        super();

    }

    /**
     *  
     */

    public void dragEnter(DropTargetEvent evt) {
        evt.detail = DND.DROP_COPY;
    }

    public void drop(DropTargetEvent evt) {
        String[] files = (String[]) evt.data;
        PythonInterpreter pyi = Infrastructure.getPyi();
        //FIXME: 決めうちではなくファイルの情報から適切なローダを選択するように
        PyObject loader = pyi.eval("legacyLoader.load");
        
        loader.__call__(new PyString(files[0]));
         
         //TODO: 追加読み込み
//        for (int i = 1; i < files.length; i++) {
//
//        }
    }

    /**
     *  
     */

    public void dragLeave(DropTargetEvent arg0) {
    }

    /**
     *  
     */

    public void dragOperationChanged(DropTargetEvent arg0) {
    }

    /**
     *  
     */

    public void dragOver(DropTargetEvent arg0) {
    }

    /**
     *  
     */

    public void dropAccept(DropTargetEvent arg0) {
    }

}