package org.nishiohirokazu.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.nishiohirokazu.grinEdit.Mediator;

public class SWTUtil {

	public static String getFilename() {
		FileDialog dlg = new FileDialog(
				Mediator.getInstance().getShell(),
				SWT.OPEN);
		String file = dlg.open();
		return file;
	}
	public static String getSaveFilename() {
		FileDialog dlg = new FileDialog(
				Mediator.getInstance().getShell(),
				SWT.SAVE);
		String file = dlg.open();
		return file;
	}

}
