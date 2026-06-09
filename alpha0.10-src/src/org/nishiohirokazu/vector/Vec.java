/*
 * nishio 2005/01/31
 *
 */
package org.nishiohirokazu.vector;

/**
 * @author nishio
 *
 */
public class Vec {

	public static double[] scale(double[] v, double s){
		int N=v.length;
		double[] result=new double[N];
		for(int i=0;i<N;i++){
			result[i]=v[i]*s;			
		}
		return result;
	}
	public static double[] add(double[] v1, double[] v2){
		int N=v1.length;
//		assert N==v2.length;
		double[] result=new double[N];
		for(int i=0;i<N;i++){
			result[i]=v1[i]+v2[i];			
		}
		return result;
	}
	
	/**
	 * destructive add. param v1 is overriden
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double[] addD(double[] v1, double[] v2){
		int N=v1.length;
//		assert N==v2.length;
		for(int i=0;i<N;i++){
			v1[i] += v2[i];			
		}
		return v1;
	}

	public static double[] sub(double[] v1, double[] v2){
		int N=v1.length;
//		assert N==v2.length;
		double[] result=new double[N];
		for(int i=0;i<N;i++){
			result[i]=v1[i]-v2[i];			
		}
		return result;
	}
	/**
	 * calc invert vector
	 * @param v vector
	 * @return vector -v
	 */
	public static double[] inv(double[] v){
		int N=v.length;
		double[] result=new double[N];
		for(int i=0;i<N;i++){
			result[i]=-v[i];			
		}
		return result;
	}
	public static double distance(double[] v1, double[] v2){
		int N=v1.length;
		double result=0.0;
		for(int i=0;i<N;i++){
			result+=(v1[i]-v2[i])*(v1[i]-v2[i]);			
		}
		return Math.sqrt(result);
	}
	public static double sqDistance(double[] v1, double[] v2){
		int N=v1.length;
		double result=0.0;
		for(int i=0;i<N;i++){
			result+=(v1[i]-v2[i])*(v1[i]-v2[i]);			
		}
		return result;
	}
	
	
	/**
	 * calc dot product.
	 * @param v1
	 * @param v2
	 * @return dot product
	 */
	public static double dPro(double[] v1, double[] v2){
		int N=v1.length;
		double result=0.0;
		for(int i=0;i<N;i++){
			result+=(v1[i] * v2[i]);
		}
		return result;
	}

	/**
	 * calc square magnitude. use you don't want the overhead of Math.sqrt
	 * @param v vector
	 * @return square magnitude of v
	 */
	public static double sqMag(double[] v){
		int N=v.length;
		double result=0.0;
		for(int i=0;i<N;i++){
			result+=(v[i] * v[i]);			
		}
		return result;
	}

	public static double mag(double[] v){
		return Math.sqrt(sqMag(v));
	}
	public static double[] normalize(double[] v){
		double m = mag(v);
		if(m == 0){
			return normalize(new double[]{Math.random(), Math.random()});
		}
		return scale(v, 1.0/m);
	}
	
	public static double[] translate2DVec(double[] axis1, double v1, double[] axis2, double v2){
		return Vec.add(
				Vec.scale(axis1, v1),
				Vec.scale(axis2, v2));
	}
	public static void println(double[] v){
		System.out.print("(");
		for(int i=0;i<v.length-1;i++){
			System.out.print(v[i]);
			System.out.print(", ");
		}
		System.out.print(v[v.length-1]);
		System.out.println(")");
	}
	/**
	 * @param ndir
	 * @return return vector rorated 90 degrees counterclockwize
	 */
	public static double[] rot90(double[] ndir) {
		double[] result={-ndir[1], ndir[0]};
		return result;
	}
	
}
