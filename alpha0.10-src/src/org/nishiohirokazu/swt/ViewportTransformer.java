/*
 * nishio 2005/02/26
 *
 */
package org.nishiohirokazu.swt;


/**
 * @author nishio
 *
 */
public class ViewportTransformer{
	public double scale=100.0;
	public double halfWidth = 300;
	public double[] center={0.0,0.0};

	public double[] viewportTransform(double[] pos){
		double[] result={
		        halfWidth+(pos[0]-center[0])*scale,
		        halfWidth+(pos[1]-center[1])*scale};
		return result;
	}
	public double[] invViewportTransform(double[] pos){
		double[] result={
			(pos[0] - halfWidth) / scale + center[0],
			(pos[1] - halfWidth) / scale + center[1]};
		return result;
	}
	/**
	 * 差分ベクトルのスケーリング, scaling of differential vector.
	 * @param diff
	 * @return Vec
	 */
	public double[] scaling(double[] diff){
		double[] result={
				diff[0] * scale,
				diff[1] * scale};
			return result;
	}
	public double[] invScaling(double[] diff){
		double[] result={
				diff[0] / scale,
				diff[1] / scale};
			return result;
	}
}
