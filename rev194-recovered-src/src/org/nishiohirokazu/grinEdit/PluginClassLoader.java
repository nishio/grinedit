package org.nishiohirokazu.grinEdit;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Vector;

/**
 * ClassLoader to load from plugins. 
 * It search recursively in "plugins" folder and
 * remember files which filename ends ".jar"
 * when it is created.
 *  
 * プラグインをロードするためのクラスローダ。
 * インスタンス作成時にpluginsフォルダ以下を再帰的に探索し、
 * ".jar"で終わるファイル名のファイルが見つかれば登録する。
 * 注意点：JARでないファイルが".jar"という名前で終わらないようにすること。
 * パッケージも含めて同じ名前のクラスを作らないこと。
 * 
 * @author nishio
 *
 */
public class PluginClassLoader extends URLClassLoader {
	public PluginClassLoader() {
		super(findAllJar());
	}
	static private URL[] findAllJar(){
		String start = "plugins";
		Vector jars = recursiveFind(start);
		URL[] result = new URL[jars.size()];
		for(int i = 0; i < jars.size(); i++){
			try {
				result[i] = new URL("file:///" + jars.get(i).toString());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	static private Vector recursiveFind(String dir){
		Vector result = new Vector();
		File files[] = new File(dir).listFiles();
		for (int cnt = 0; cnt < files.length; cnt++) {
			File f = files[cnt];
	    	String filename = f.getAbsolutePath();
    		if(f.isDirectory()){
    			Vector result_ = recursiveFind(filename);
    			result.addAll(result_);
    		}else if(f.getName().toLowerCase().endsWith(".jar")){
    			result.add(filename);
    		}
		}
		return result;
	}
}
