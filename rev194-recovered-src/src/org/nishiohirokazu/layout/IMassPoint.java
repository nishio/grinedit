package org.nishiohirokazu.layout;

import java.util.Vector;

public interface IMassPoint {
	public Vector getDVelList();
	public void setDVelList(Vector velList);
	public double[] getPosition();
	public void setPosition(double[] position);
	public double[] getVelocity();
	public void setVelocity(double[] velocity);
}
