/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/10/16
 *
 */
package org.nishiohirokazu.grinEdit;

import org.eclipse.jface.dialogs.IInputValidator;


final class EvalableValidator implements IInputValidator {
    public String isValid(String newText) {
        try{
            Infrastructure.pyi.eval(newText);
            return null;
        }catch(Exception e){
            String[] lines = e.toString().split("\n"); 
            int N = lines.length;
            String lastLine = lines[N-1];
            return lastLine;
        }
    }
}