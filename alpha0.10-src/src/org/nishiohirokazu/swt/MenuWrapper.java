
package org.nishiohirokazu.swt;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.*;

/*
 * nishio 2005/01/30
 *
 */

/**
 * @author nishio
 *  
 */
public class MenuWrapper{
	private Menu bar;

	private Menu lastCascade;

	Shell shell;

	public MenuWrapper(Shell shell) {
		this.shell = shell;
		bar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(bar);
	}

	public MenuItem addCascade(String name) {
		MenuItem item = new MenuItem(bar, SWT.CASCADE);
		item.setText(name);
		lastCascade = new Menu(item);
		item.setMenu(lastCascade);
		return item;
	}

	public MenuItem addPush(String name, SelectionListener listener) {
		MenuItem item = new MenuItem(lastCascade, SWT.PUSH);
		item.setText(name);
		if(listener!=null){
			item.addSelectionListener(listener);
		}
		return item;
	}

	public MenuItem addRadio(String name, SelectionListener listener) {
		MenuItem item = new MenuItem(lastCascade, SWT.RADIO);
		item.setText(name);
		if(listener!=null){
			item.addSelectionListener(listener);
		}
		return item;
	}

	public MenuItem addSeparator() {
		MenuItem item = new MenuItem(lastCascade, SWT.SEPARATOR);
		return item;
	}
	

}