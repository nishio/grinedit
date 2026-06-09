//package org.nishiohirokazu.layout;
//
//import java.util.Hashtable;
//import java.util.List;
//
//import org.nishiohirokazu.grinEdit.Infrastructure;
//import org.nishiohirokazu.swt.ViewportTransformer;
//import org.nishiohirokazu.vector.Vec;
//
///**
// * 入れ子になった頂点の間の「形を保つ」制約
// * @author nishio
// *
// */
//public class PL_NestedVertex extends PhysicalLaw {
//	private double[][] dirList;
//	private MassPoint[] vertexList;
//	private int tolerance = 7;
//	private int N;
//	private double[] magList;
//
//	public PL_NestedVertex(){
//		
//	}
//	public PL_NestedVertex(List target) {
//		N = target.size();
//		vertexList = new MassPoint[N];
//		target.toArray(vertexList);
//		double[] gp = {0, 0};
//		for(int i = 0; i < N; i++){
//			MassPoint v = vertexList[i];
//			Vec.addD(gp, v.position);
//		}
//		gp = Vec.scale(gp, 1.0 / N);
//		dirList = new double[N][];
//		magList = new double[N];
//
//		for(int i = 0; i < N; i++){
//			MassPoint v = vertexList[i];
//			double[] diff = Vec.sub(v.position, gp);
//			dirList[i] = Vec.normalize(diff);
//			magList[i] = Vec.mag(diff);
//		}
//	}
//
//	public boolean apply(int iter) {
//		boolean isSatisfied = true;
//		if(iter == 0){
//			return false; // not to satisfy on first iter
//		}
//		if(iter < tolerance){
//			double[] gp = {0, 0};
//			for(int i = 0; i < N; i++){
//				MassPoint v = vertexList[i];
//				Vec.addD(gp, v.position);
//			}
//			gp = Vec.scale(gp, 1.0 / N);
//
//			for(int i = 0; i < N; i++){
//				MassPoint v = vertexList[i];
//				double[] diff = Vec.sub(v.position, gp);
//				double[] dir = Vec.normalize(diff);
//				
//				double[] oldDir = dirList[i];
////				double[] trueDir = 
////					Vec.add(
////							Vec.scale(ax, oldDir[0]),
////							Vec.scale(ay, oldDir[1]));
//
//			}
//
////			double[] nax = Vec.normalize(Vec.add(ax, Vec.scale(ay, avel)));
////			ay = Vec.normalize(Vec.add(ay, Vec.scale(ax, - avel)));
////			ax = nax;
//
//            ViewportTransformer vp = Infrastructure.getViewportTransformer();
//			for(int i = 0; i < N; i++){
//				MassPoint v = vertexList[i];
//
//				double[] oldDiff = dirList[i];
////				double[] truePos = Vec.add(
////						gp,
////						Vec.scale(
////						Vec.add(
////							Vec.scale(ax, oldDiff[0]),
////							Vec.scale(ay, oldDiff[1])), magList[i]));
////
////				double[] updateVec = Vec.sub(truePos, v.position);
//				
////				if(Vec.mag(vp .scaling(updateVec)) > 0.1){
////	            	isSatisfied = false;
////	                v.dVelList.add(updateVec);
////	            }
//
//			}
//
//		}
//		return isSatisfied;
//	}
//
//
//	public Hashtable getParams() {
//		Hashtable result = super.getParams();
//		return result;
//	}
//	
//
//}
