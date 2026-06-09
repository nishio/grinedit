/*
 * nishio 2005/03/04
 *
 */
package org.nishiohirokazu.vector;



/**
 * @author nishio
 *
 */
/**
 * @author nishio
 *
 */
/**
 * @author nishio
 *
 */
public class Vec2 {
	public double x,y;
	public Vec2(Vec2 v) {
		this.x=v.x;
		this.y=v.y;
	}

	/**
	 * @param x
	 * @param y
	 */
	public Vec2(double x, double y) {
		this.x=x;
		this.y=y;
	}

	/**
	 * @param v1
	 * @param v2
	 * @return distance
	 */
	public static double distance(Vec2 v1, Vec2 v2) {
		double dx=v1.x-v2.x;
		double dy=v1.y-v2.y;
		return Math.sqrt(dx*dx+dy*dy);
	}

	/**
	 * @param s 
	 * @return scaled vector
	 */
	public Vec2 scale(double s) {
		x*=s;
		y*=s;
		return this;
	}

	/**
	 * @param v
	 * @return this+v
	 */
	public Vec2 add(Vec2 v) {
		x+=v.x;
		y+=v.y;
		return this;
	}
}
