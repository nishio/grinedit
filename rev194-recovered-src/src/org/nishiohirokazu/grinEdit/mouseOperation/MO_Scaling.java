/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/03/25
 *
 */
package org.nishiohirokazu.grinEdit.mouseOperation;

import org.nishiohirokazu.grinEdit.Infrastructure;
import org.nishiohirokazu.swt.ViewportTransformer;

/**
 * @author nishio
 *
 */
public class MO_Scaling extends MouseOperation {
    public String getName(){
        return "Scale";
    }

	private boolean pressed;
	private int oldY;
	public void mouseDown(int x, int y, int mask) {
		pressed = true;
		oldY = y;
	}
	public void mouseMove(int x, int y, int mask) {
		ViewportTransformer vp = Infrastructure.getViewportTransformer();
		if(pressed){
			int dy =  oldY - y;
			vp.scale *= Math.exp(dy / 100.0);
			oldY = y;
		}
	}
	public void mouseUp(int x, int y, int mask) {
		pressed = false;
	}
}
