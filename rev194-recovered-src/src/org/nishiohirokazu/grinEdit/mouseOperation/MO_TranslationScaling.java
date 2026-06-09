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
 * ëSëÃÇÃïΩçsà⁄ìÆÇ∆ägëÂèkè¨ÇçsÇ§MouseOperation
 * @author nishio
 *
 */
public class MO_TranslationScaling extends MouseOperation {
    public String getName(){
        return "Translate";
    }

	private int lastX;
	private int lastY;
	private boolean dragging;
	private boolean transMode = false;

	public void mouseDown(int x, int y, int mask) {
		UtilMetaKey.setMask(mask);
		lastX = x;
		lastY = y;
		dragging = true;
		if(UtilMetaKey.shift){
			transMode = true;
		}else{
			transMode = false;
		}
	}
	public void mouseMove(int x, int y, int mask) {
		ViewportTransformer vp = Infrastructure.getViewportTransformer();
		if(dragging){
			if(transMode){
				// translation mode
				vp.center[0] -=(x - lastX) / vp.scale;
				vp.center[1] -=(y - lastY) / vp.scale;
			}else{
				// scale mode
				int dy =  lastY - y;
				vp.scale *= Math.exp(dy / 100.0);
			}
			lastX = x;
			lastY = y;
		}
	}

	
	public void mouseUp(int x, int y, int mask) {
		dragging = false;
	}
}
