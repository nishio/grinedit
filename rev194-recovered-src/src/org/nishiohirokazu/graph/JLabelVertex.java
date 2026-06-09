package org.nishiohirokazu.graph;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JLabel;

import org.nishiohirokazu.grinEdit.Infrastructure;
import org.nishiohirokazu.layout.IMassPoint;


/**
 * JLabelを継承した頂点のサンプル。ただしまだ描画がうまく行っていない。
 * @author nishio
 *
 */
public class JLabelVertex extends JLabel implements IGRINObject, IMassPoint, IHasScreenPos, IRenderable{
	private String id;

	public JLabelVertex() {
		setText("Hello");

		Infrastructure.getCanvas().getParent().add(this);
		
	}


	public String getId() {
		return id;
	}

	public void setId(Object o){
		id = o.toString();
	}

	public Hashtable getParams() {
		return new Hashtable();
	}


	private Vector dVelList = new Vector();
	private double[] position = new double[2];
	private double[] velocity = new double[2];

	public Vector getDVelList() {
		return dVelList;
	}

	public void setDVelList(Vector velList) {
		dVelList = velList;
	}

	public double[] getPosition() {
		return position;
	}

	public void setPosition(double[] position) {
		this.position = position;
	}

	public double[] getVelocity() {
		return velocity;
	}

	public void setVelocity(double[] velocity) {
		this.velocity = velocity;
	}

	private double[] screenPos;
	public double[] getScreenPos() {
		return screenPos;
	}

	public void setScreenPos(double[] pos) {
		Rectangle b = getBounds();
		//setBounds((int)pos[0], (int)pos[1], b.width, b.height);
		setBounds(10, 10, b.width, b.height);
		screenPos = pos;
	}


	public void render(Object target) {
		update((Graphics) target);
	}


}
