package org.nishiohirokazu.grinEdit;

import java.util.Hashtable;
import java.util.Vector;

import org.nishiohirokazu.graph.Graph;
import org.nishiohirokazu.graph.IEdge;
import org.nishiohirokazu.graph.IVertex;
import org.nishiohirokazu.layout.IMassPoint;

/**
 * ことなる連結成分を離れた位置に配置する1ステップレイアウト
 * @author nishio
 *
 */

public class SeparateComponentLayout implements ILayout{
	private int width;
	private int x;
	private int y;
	private int phase;
	private int i;

	public void layoutStep(Graph g) {
		Hashtable<String, IVertex> vertexDict = g.getVertexDict();
		Hashtable<String, IEdge> edgeDict = g.getEdgeDict();
		synchronized (vertexDict) {
			synchronized (edgeDict) {
				// 隣接リストを作る
				Hashtable<IVertex, Vector> adjDict = new Hashtable();
				for(IVertex v: vertexDict.values()){
					adjDict.put(v, new Vector());
				}
				for(IEdge e: edgeDict.values()){
					IVertex v1 = e.getV1();
					IVertex v2 = e.getV2();
					adjDict.get(v1).add(v2);
					adjDict.get(v2).add(v1);
				}
				// 連結成分を求める
				Vector<IVertex> visited = new Vector();
				Vector<Vector<IVertex>> components = new Vector();
				for(IVertex v: vertexDict.values()){
					if(visited.contains(v)){
						continue;
					}
					Vector<IVertex> aComp = new Vector();
					Vector<IVertex> queue = new Vector();
					queue.add(v);
					while(queue.size() > 0){
						Vector<IVertex> newQueue = new Vector();
						for(IVertex current: queue){
							visited.add(current);
							aComp.add(current);
							Vector<IVertex> neighbors = adjDict.get(current);
							for(IVertex neighbor: neighbors){
								if(!visited.contains(neighbor) && !newQueue.contains(neighbor)){
									newQueue.add(neighbor);
								}
							}
						}
						queue = newQueue;
					}
					components.add(aComp);
				}
				// 頂点のX座標を変えてみる
				int SCALE = 10;
				init();
				for(Vector<IVertex> aComp: components){
					next();
					for(IVertex v: aComp){
						IMassPoint mv = (IMassPoint) v;
						double[] position = mv.getPosition();
						position[0] = x * SCALE + Math.random() - 0.5;
						position[1] = y * SCALE + Math.random() - 0.5;
					}
				}
			}
		}
	}
	
	private void init(){
		phase = 0;
	}
	
	private void next(){
		if(phase == 0){
			x = 0;
			y = 0;
			phase = 1;
			width = 1;
			i = 0;
		}else if(phase == 1){
			x += 1;
			i += 1;
			if(i == width){
				phase = 2;
				i = 0;
			}
		}else if(phase == 2){
			y += 1;
			i += 1;
			if(i == width){
				phase = 3;
				width += 1;
				i = 0;
			}
		}else if(phase == 3){
			x -= 1;
			i += 1;
			if(i == width){
				phase = 4;
				i = 0;
			}
		}else if(phase == 4){
			y -= 1;
			i += 1;
			if(i == width){
				phase = 1;
				i = 0;
				width += 1;
			}
		}
	}
	
	public static void main(String[] args) {
		// test
		SeparateComponentLayout s = new SeparateComponentLayout();
		s.init();
		for(int i = 0; i < 10; i++){
			s.next();
			System.out.println(s.x + ", " + s.y);
		}
	}
}
