/*
 * nishio 2005/02/26
 *
 */
package org.nishiohirokazu.grinEdit;

import org.python.util.PythonInterpreter;

/**
 * @author nishio
 *  
 */

public class GRINEdit{
    public PythonInterpreter pyi;

	public GRINEdit() {
		
		Infrastructure.getPyi();
		Mediator.getInstance();

		GRINEditDefaultGUI grinedit = new GRINEditDefaultGUI();

		Infrastructure.execPythonScript("start.py");

		Infrastructure.getDoubleBufferer();
		Infrastructure.getViewportTransformer();

		grinedit.start();
		
		if(Infrastructure.server != null){
			Infrastructure.server.shutdown();
		}
		Infrastructure.console.killCurrentThread();
		System.exit(0);
	}


    public static void main(String[] args) {
		new GRINEdit();
	}
}