package org.nishiohirokazu.grinEdit;

import java.util.Hashtable;

import org.nishiohirokazu.graph.Graph;
import org.nishiohirokazu.graph.IVertex;
import org.nishiohirokazu.layout.IMassPoint;

/**
 * 頂点を十分にまばらに配置する1ステップレイアウト TODO: 綴りが正しいかチェック
 * 
 * @author nishio
 * 
 */

public class SparceLayout implements ILayout {
	public void layoutStep(Graph g) {
		Hashtable<String, IVertex> vertexDict = g.getVertexDict();
		synchronized (vertexDict) {
			double sumX = 0, sumY = 0, sumXX = 0, sumYY = 0;
			for (IVertex _v : vertexDict.values()) {
				IMassPoint v = (IMassPoint) _v;
				double[] pos = v.getPosition();
				sumX += pos[0];
				sumY += pos[1];
				sumXX += pos[0] * pos[0];
				sumYY += pos[1] * pos[1];
			}
			int num = vertexDict.size();
			double aveX = sumX / num;
			double aveY = sumY / num;
			double varX = (sumXX - sumX * sumX / num) / (num - 1);
			double varY = (sumYY - sumY * sumY / num) / (num - 1);
			double sdX = Math.sqrt(varX);
			double sdY = Math.sqrt(varY);
			double TARGET_SD = 10;

			for (IVertex _v : vertexDict.values()) {
				IMassPoint v = (IMassPoint) _v;
				double[] pos = v.getPosition();
				double x = (pos[0] - aveX) / sdX * TARGET_SD + aveX;
				double y = (pos[0] - aveY) / sdY * TARGET_SD + aveY;
				v.setPosition(new double[] { x, y });
			}
		}
	}
}
