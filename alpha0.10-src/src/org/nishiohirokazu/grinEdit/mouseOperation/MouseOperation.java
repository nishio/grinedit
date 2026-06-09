/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/03/25
 *
 */
package org.nishiohirokazu.grinEdit.mouseOperation;

import org.nishiohirokazu.grinEdit.Mediator;

/**
 * @author nishio
 *
 */
public class MouseOperation {
	protected Mediator med;
	public MouseOperation() {
		med = Mediator.getInstance();
	}

	public void mouseDoubleClick(int x, int y) {
	}

	public void mouseDown(int x, int y) {
	}

	public void mouseUp(int x, int y) {
	}

	public void mouseMove(int x, int y) {
	}

	public void mouseDrag(int x, int y){
	}

	public void draw() {
    }

    public String getName(){
        return "";
    }
}
