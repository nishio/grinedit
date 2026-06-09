package org.nishiohirokazu.grinEdit.mouseOperation;

/**
 * Utility class to judge whether metakeys (shift, ctrl, alt) are pressed.
 * どのメタキーが押されているかを判断するためのユーティリティクラス。
 * @author nishio
 *
 */
public class UtilMetaKey {
	public static boolean shift, ctrl, alt;
	public static String type = "AWT"; // "AWT" or "SWT"
	public static void setMask(int mask){
		if(type == "AWT"){
			shift = ((mask & 1) > 0);
			ctrl = ((mask & 2) > 0);
			alt = ((mask & 8) > 0);
		}else if(type == "SWT"){
			shift = ((mask & 0x20000) > 0);
			ctrl = ((mask & 0x40000) > 0);
		}
	}
}
