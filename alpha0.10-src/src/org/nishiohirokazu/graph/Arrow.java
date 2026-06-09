/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/04/08
 *
 */
package org.nishiohirokazu.graph;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.nishiohirokazu.swt.ColorHolder;
import org.nishiohirokazu.vector.Vec;

/**
 * @author nishio
 *
 */
public class Arrow {
	private static void drawGenerally(GC gc, double[] end, double[] ndir, double arg0, double arg1, double arg2){
		double[] cdir=Vec.rot90(ndir);
		Color oldBack = gc.getBackground();
		gc.setBackground(ColorHolder.BLACK);
		
		double[] kaesi=Vec.scale(ndir, arg0);
		double[] hure=Vec.scale(cdir, arg1);
		double[] mizo=Vec.scale(ndir, arg2);

		double[] a0=end;
		double[] a1=Vec.add(a0, Vec.add(kaesi, hure));
		double[] a2=Vec.add(a0, mizo);
		double[] a3=Vec.add(a0, Vec.sub(kaesi, hure));
		int[] pp = {
				(int) a0[0], (int) a0[1],
				(int) a1[0], (int) a1[1],
				(int) a2[0], (int) a2[1],
				(int) a3[0], (int) a3[1]};
		gc.fillPolygon(pp);
		gc.setBackground(oldBack);
	}
	
	public static void draw(GC gc, double[] end, double[] ndir) {
		drawGenerally(gc, end, ndir, 7.0, 4.0, 5.0);
	}
}
