package org.nishiohirokazu.grinEdit.menuAction;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.eclipse.swt.events.SelectionEvent;
import org.nishiohirokazu.grinEdit.Infrastructure;
import org.nishiohirokazu.grinEdit.Mediator;

import com.artenum.jyconsole.JyConsole;
import com.artenum.util.PropertyLoader;

public class MA_OpenJyConsole extends MenuAction {
	public MA_OpenJyConsole() {
		try {
			PropertyLoader.loadProperties("plugins/grinedit-app/menu-jyconsole/jyconsole.properties");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		JFrame f = new JFrame("JyConsole by Artenum");
		Infrastructure.jyConsoleFrame = f;
		JyConsole console = Infrastructure.console;
		f.getContentPane().add(console, BorderLayout.CENTER);
//		console.setFont(new Font("Courier New", Font.PLAIN, 16));

		f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		f.setSize(600, 400);
		f.setLocationRelativeTo(null);
		
	}

	public void widgetSelected(SelectionEvent arg0) {
		med = Mediator.getInstance();
		Infrastructure.jyConsoleFrame.setVisible(true);
	}
}
