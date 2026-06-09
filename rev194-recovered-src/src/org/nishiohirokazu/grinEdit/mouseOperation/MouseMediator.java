/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/03/25
 *
 */
package org.nishiohirokazu.grinEdit.mouseOperation;

import java.awt.event.MouseMotionListener;
import java.util.Hashtable;

import org.eclipse.swt.events.MouseEvent;

/**
 * MouseMediator: mediate mouse event
 * @author nishio
 */
public class MouseMediator implements 
	java.awt.event.MouseListener, MouseMotionListener
	{

//	private MouseEvent downEvent;
//	private MouseEvent lastEvent;
	public MouseOperation MOLeft;
	public MouseOperation MORight;
    public Hashtable oparations;

	public MouseMediator() {
		oparations=new Hashtable();
	}


    public void draw() {
		MOLeft.draw();
		MORight.draw();
    }

	public void add(String name, MouseOperation mo){
	    oparations.put(name, mo);
	}
	public MouseOperation get(String name){
	    return (MouseOperation) oparations.get(name);
	}
	public void setLeft(String name){
	    MOLeft = (MouseOperation) oparations.get(name);
	}
	public void setRight(String name){
	    MORight = (MouseOperation) oparations.get(name);
	}

	// SWT mouse event handling
	public void mouseDoubleClick(MouseEvent e) {
		System.out.println(e);
		if(e.button==1){
			MOLeft.mouseDoubleClick(e.x, e.y, e.stateMask);
		}else if(e.button==3){
			MORight.mouseDoubleClick(e.x, e.y, e.stateMask);
		}
	}

	public void mouseDown(MouseEvent e) {
		if(e.button==1){
			MOLeft.mouseDown(e.x, e.y, e.stateMask);
		}else if(e.button==3){
			MORight.mouseDown(e.x, e.y, e.stateMask);
		}
	}

	public void mouseUp(MouseEvent e) {
		if(e.button==1){
			MOLeft.mouseUp(e.x, e.y, e.stateMask);
		}else if(e.button==3){
			MORight.mouseUp(e.x, e.y, e.stateMask);
		}
	}

	public void mouseMove(MouseEvent e) {
		MOLeft.mouseMove(e.x, e.y, e.stateMask);
		MORight.mouseMove(e.x, e.y, e.stateMask);
	}

	
	// awt mouse event handling
	public void mouseClicked(java.awt.event.MouseEvent e) {
		if(e.getClickCount() == 2){
			if(e.getButton() == 1){
				MOLeft.mouseDoubleClick(e.getX(), e.getY(), e.getModifiers());
			}else if(e.getButton() == 3){
				MORight.mouseDoubleClick(e.getX(), e.getY(), e.getModifiers());
			}
		}
	}

	public void mousePressed(java.awt.event.MouseEvent e) {
		if(e.getButton() == 1){
			MOLeft.mouseDown(e.getX(), e.getY(), e.getModifiers());
		}else if(e.getButton() == 3){
			MORight.mouseDown(e.getX(), e.getY(), e.getModifiers());
		}
	}

	public void mouseReleased(java.awt.event.MouseEvent e) {
		if(e.getButton() == 1){
			MOLeft.mouseUp(e.getX(), e.getY(), e.getModifiers());
		}else if(e.getButton() == 3){
			MORight.mouseUp(e.getX(), e.getY(), e.getModifiers());
		}
	}

	public void mouseEntered(java.awt.event.MouseEvent arg0) {
	}

	public void mouseExited(java.awt.event.MouseEvent arg0) {
	}

	public void mouseDragged(java.awt.event.MouseEvent e) {
		MOLeft.mouseMove(e.getX(), e.getY(), e.getModifiers());
		MORight.mouseMove(e.getX(), e.getY(), e.getModifiers());
	}

	public void mouseMoved(java.awt.event.MouseEvent e) {
		MOLeft.mouseMove(e.getX(), e.getY(), e.getModifiers());
		MORight.mouseMove(e.getX(), e.getY(), e.getModifiers());
	}
}
