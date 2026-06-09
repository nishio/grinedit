/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/04/08
 *
 */
package org.nishiohirokazu.grinEdit.menuAction;


import org.eclipse.swt.events.SelectionEvent;
import org.nishiohirokazu.grinEdit.Infrastructure;
import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.swt.SWTUtil;

/**
 * @author nishio
 *
 */
public class MA_LoadBGImage extends MenuAction {
	/**
     * @param med
     */
    public MA_LoadBGImage(Mediator med) {
        super(med);
    }



	/**
	 *
	 */

	public void widgetSelected(SelectionEvent arg0) {
		Infrastructure.setBgImage(SWTUtil.getFilename());
	}
}
