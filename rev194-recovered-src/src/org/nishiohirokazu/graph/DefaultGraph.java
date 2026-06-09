/*
 * nishio 2005/02/26
 *
 */
package org.nishiohirokazu.graph;

import org.nishiohirokazu.layout.PL_Anchor;
import org.nishiohirokazu.layout.PL_Repulsion;
import org.nishiohirokazu.layout.PL_SpringEdge;

/**
 * @author nishio
 * 
 */
public class DefaultGraph extends Graph {
	public DefaultGraph() {
		super();
		addObj("Law", "PL_SpringEdge", new PL_SpringEdge());
		addObj("Law", "PL_Repulsion", new PL_Repulsion());
		addObj("Law", "PL_Anchor", new PL_Anchor());

	}
}
