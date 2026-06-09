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
public class MO_Translation extends MouseOperation {
    public String getName(){
        return "Translate";
    }

	private int lastX;
	private int lastY;
	private boolean dragging;

	public void mouseDown(int x, int y) {
		lastX = x;
		lastY = y;
		dragging = true;
	}
	public void mouseMove(int x, int y) {
		ViewportTransformer vp = Infrastructure.getViewportTransformer();
		if(dragging){
			vp.center[0] -=(x - lastX) / vp.scale;
			vp.center[1] -=(y - lastY) / vp.scale;
			lastX = x;
			lastY = y;
		}
	}
	public void mouseUp(int x, int y) {
		dragging = false;
	}
}
