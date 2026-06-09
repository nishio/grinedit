/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/10/03
 *
 */
package org.nishiohirokazu.swt;

import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;

/**
 *
 * @author nishio
 */
public class ResizeListener implements ControlListener {

    private DoubleBufferer buf;

    /**
     * @param bufferer
     */
    public ResizeListener(DoubleBufferer buf) {
        this.buf = buf;
    }

    public void controlResized(ControlEvent arg0) {
        buf.initBackgroundImage();
        
    }

    public void controlMoved(ControlEvent arg0) {
    }

}
