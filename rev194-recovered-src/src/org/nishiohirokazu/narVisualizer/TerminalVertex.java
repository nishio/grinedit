/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/07/27
 *
 */
package org.nishiohirokazu.narVisualizer;

import org.nishiohirokazu.graph.BoxVertex;
import org.nishiohirokazu.vector.Vec;

/**
 * 
 * @author nishio
 */
public class TerminalVertex extends BoxVertex implements IHasTerminal {
	public TerminalVertex() {

	}
	
	public double[] getTerminal(String id) {
		if(bound == null){
			return null;
		}
		return Vec.addX(screenPos, bound.x / 2.0);
	}

}
