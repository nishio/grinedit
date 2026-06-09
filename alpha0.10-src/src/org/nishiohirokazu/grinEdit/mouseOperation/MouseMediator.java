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
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;

/**
 * MouseMediator: mediate mouse event
 * @author nishio
 */
public class MouseMediator implements 
	MouseListener, MouseMoveListener, //SWT
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
		if(e.button==1){
			MOLeft.mouseDoubleClick(e.x, e.y);
		}else if(e.button==3){
			MORight.mouseDoubleClick(e.x, e.y);
		}
	}

	public void mouseDown(MouseEvent e) {
		if(e.button==1){
			MOLeft.mouseDown(e.x, e.y);
		}else if(e.button==3){
			MORight.mouseDown(e.x, e.y);
		}
	}

	public void mouseUp(MouseEvent e) {
		if(e.button==1){
			MOLeft.mouseUp(e.x, e.y);
		}else if(e.button==3){
			MORight.mouseUp(e.x, e.y);
		}
	}

	public void mouseMove(MouseEvent e) {
		MOLeft.mouseMove(e.x, e.y);
		MORight.mouseMove(e.x, e.y);
	}

	
	// awt mouse event handling
	public void mouseClicked(java.awt.event.MouseEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	public void mousePressed(java.awt.event.MouseEvent e) {
		if(e.getButton() == 1){
			MOLeft.mouseDown(e.getX(), e.getY());
		}else if(e.getButton() == 3){
			MORight.mouseDown(e.getX(), e.getY());
		}
	}

	public void mouseReleased(java.awt.event.MouseEvent e) {
		if(e.getButton() == 1){
			MOLeft.mouseUp(e.getX(), e.getY());
		}else if(e.getButton() == 3){
			MORight.mouseUp(e.getX(), e.getY());
		}
	}

	public void mouseEntered(java.awt.event.MouseEvent arg0) {
	}

	public void mouseExited(java.awt.event.MouseEvent arg0) {
	}

	public void mouseDragged(java.awt.event.MouseEvent e) {
		MOLeft.mouseMove(e.getX(), e.getY());
		MORight.mouseMove(e.getX(), e.getY());
	}

	public void mouseMoved(java.awt.event.MouseEvent e) {
		MOLeft.mouseMove(e.getX(), e.getY());
		MORight.mouseMove(e.getX(), e.getY());
	}
}
