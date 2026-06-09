/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/05/22
 *
 */
package org.nishiohirokazu.layout;

import java.util.Hashtable;

import org.nishiohirokazu.graph.IGRINObject;


/**
 *
 * @author nishio
 */

public class PhysicalLaw implements IGRINObject{
	private String id;

	/**
	 * apply physical law
	 * @return is satisfied?
	 */
	public boolean apply(int iter){
		return true;
	}
	
	
	public Hashtable getParams(){
		Hashtable result = new Hashtable();
		result.put("id", id);
		return result;
	}

	public String getId() {
		return id;
	}

	public void setId(Object value){
		id = value.toString();
	}



}
