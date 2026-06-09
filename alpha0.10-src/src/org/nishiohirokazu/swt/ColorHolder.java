/**
 * $Id$
 * @version $Revision$
 * @author Nishio Hirokazu
 * 2005/04/08
 *
 */
package org.nishiohirokazu.swt;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.nishiohirokazu.grinEdit.UtilXMLRPC;

/**
 * swt.graphics.Color must be disposed
 * when it is no longer required.
 * It bothers me. So I decide to make single instance and
 * it manages all Colors.    
 * @author nishio
 *
 */
public class ColorHolder {

    public static Color BLACK;
    public static Color WHITE;
    public static Color GRINGREEN;
    public static Color SELECTED_VERTEX;
    static Hashtable USER_COLOR=new Hashtable();
    public static Color RED;
    static private boolean bDisposed;
    private static Device device;

    /**
     * 
     */
    public static void initialize (Device dev){
        device = dev;
        BLACK=new Color(dev, 0, 0, 0);
        WHITE=new Color(dev, 255, 255, 255);
        GRINGREEN= new Color(dev, 100, 200, 100);
        SELECTED_VERTEX= new Color(dev, 100, 100, 200);
        RED= new Color(dev, 255, 0, 0);
    }
    
    public static Color get(int r, int g, int b){
        Integer rgb=new Integer((b*256+g)*256+r);
        if(USER_COLOR.containsKey(rgb)){
            return (Color) USER_COLOR.get(rgb);
        }else{
            Color result=new Color(device, r, g, b);
            USER_COLOR.put(rgb, result);
            return result;
        }
    }
    public static Color get(Vector rgb){
    	return get(
    			UtilXMLRPC.getIntValue(rgb.get(0)),
    			UtilXMLRPC.getIntValue(rgb.get(1)),
    			UtilXMLRPC.getIntValue(rgb.get(2)));
    }

    public static void dispose(){
        bDisposed=true;
        BLACK.dispose();
        WHITE.dispose();
        GRINGREEN.dispose();
        SELECTED_VERTEX.dispose();
        RED.dispose();
        for (Iterator iter = USER_COLOR.keySet().iterator(); iter.hasNext();) {
            Object key = iter.next();
            Color c = (Color) USER_COLOR.get(key);
            c.dispose();
        }
    }
    public static boolean isDisposed(){
        return bDisposed;
    }
}
