/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/03/25
 *
 */
package org.nishiohirokazu.grinEdit.mouseOperation;


import org.nishiohirokazu.grinEdit.Infrastructure;
import org.nishiohirokazu.swt.DoubleBufferer;

/**
 * @author nishio
 *
 */
public class MO_SelectRange extends MouseOperation {
    public String getName(){
        return "Select vertexes in range";
    }

//	private MouseEvent dragStart;
//    private MouseEvent lastEvent;
	private boolean dragging;
	private int startX;
	private int startY;
	private int lastY;
	private int lastX;
	public void mouseDown(int x, int y) {
		dragging = true;
		startX = x;
		startY = y;
		lastX = x;
		lastY = y;
	}
	public void mouseMove(int x, int y) {
		if(dragging){
			lastX = x;
			lastY = y;
		}
	}
	public void mouseUp(int x, int y) {
	    if(dragging){
			med.selection.set(med.getVertexInRange(startX, startY, x, y));

			dragging = false;
	    }
		
		
	}
	public void draw(){
		// TODO: support awt using reflection
		DoubleBufferer dbuf = Infrastructure.getDoubleBufferer();
	    if(dragging){
			int dx = lastX - startX;
			int dy = lastY - startY;
			dbuf.getBG().drawRectangle(
			        startX, startY,
			        dx, dy
			);
	    }
	}
}
