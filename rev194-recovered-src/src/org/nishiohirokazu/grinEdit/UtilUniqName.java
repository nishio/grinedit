package org.nishiohirokazu.grinEdit;

import java.util.Hashtable;

/**
 * @author nishio
 * 一意な名前を保証するクラス
 */
public class UtilUniqName {
	private static Hashtable names = new Hashtable();
	private static Hashtable counts = new Hashtable();
	
	/**
	 * 一意な名前を生成する
	 * @param prefix 名前の頭につける文字列、vertexなど。
	 * @return 一意な名前、vertex1など。
	 */
	public static String getUniqName(String prefix){
		String result ;
		if(counts.containsKey(prefix)){
			while(true){
				Integer i = (Integer) counts.get(prefix);
				counts.put(prefix, new Integer(i.intValue() + 1));
				result = prefix + i.toString();
				if(!names.containsKey(result)){
					break;
				}
			}
		}else{
			counts.put(prefix, new Integer(1));
			result = prefix + "0";
		}
		names.put(result, names);
		return result;
	}
	
	/**
	 * 名前を登録する。すでに存在する名前を登録した際にはAssertionErrorを投げる。
	 * @param name 名前
	 */
	public static void registerUsedName(String name){
		assert !names.containsKey(name);
		names.put(name, names);
	}
}
