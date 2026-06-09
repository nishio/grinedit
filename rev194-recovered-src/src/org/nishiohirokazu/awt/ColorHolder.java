/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/04/08
 *
 */
package org.nishiohirokazu.awt;

import java.awt.Color;
import java.util.Hashtable;
import java.util.Vector;

import org.nishiohirokazu.grinEdit.UtilCast;

/**
 * 
 * @author nishio
 * 
 */
public class ColorHolder {

	public static final Color BLACK = Color.BLACK;
	public static final Color WHITE = Color.WHITE;
	public static final Color GRINGREEN = new Color(100, 200, 100);
	public static final Color SELECTED_VERTEX = new Color(100, 100, 200);
	public static final Color SELECTED_EDGE = new Color(200, 100, 100);

	static Hashtable<Integer, Color> USER_COLOR = new Hashtable<Integer, Color>();

	/**
	 * 
	 */
	public static void initialize() {
	}

	public static Color get(int r, int g, int b) {
		Integer rgb = new Integer((b * 256 + g) * 256 + r);
		if (USER_COLOR.containsKey(rgb)) {
			return USER_COLOR.get(rgb);
		}
		Color result = new Color(r, g, b);
		USER_COLOR.put(rgb, result);
		return result;
	}

	public static Color get(Vector rgb) {
		return get(UtilCast.getIntValue(rgb.get(0)),
				UtilCast.getIntValue(rgb.get(1)), UtilCast.getIntValue(rgb.get(2)));
	}

}
