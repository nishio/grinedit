package org.nishiohirokazu.narVisualizer;

public interface IHasTerminal {
	/**
	 * return screen position to connect the edge.
	 * 頂点が接続すべき位置をスクリーン座標系で返す。
	 * 従来の頂点に接続する辺が全部secreenPosにつながっていたのの代わり。
	 * もしよい実装が思いつかない場合は、return screenPos;すること。
	 * @param vertex_id
	 * @return
	 */
	public double[] getTerminal(String vertex_id);

}
