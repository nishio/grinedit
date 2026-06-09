/*
 * nishio 2005/02/26
 *
 */
package org.nishiohirokazu.swt;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;


/**
 * @author nishio
 * PaintListener which call draw() when paint event occur.
 */
public final class DrawOnPaint implements PaintListener {
	private final IDrawable parent;

	/**
	 * @param d
	 */
	public DrawOnPaint(IDrawable d) {
		this.parent = d;
	}

	public void paintControl(PaintEvent e) {
		this.parent.draw();
	}
}