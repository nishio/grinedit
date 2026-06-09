package org.nishiohirokazu.layout;

import java.util.Hashtable;
import java.util.List;

import org.nishiohirokazu.grinEdit.Infrastructure;
import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.grinEdit.UtilCast;
import org.nishiohirokazu.swt.ViewportTransformer;
import org.nishiohirokazu.vector.Vec;

public class PL_RigidBody extends PhysicalLaw {
	private double[][] dirList;
	private MassPoint[] vertexList;
	private int tolerance = 7;
	private int N;
	private double[] magList;
	private double[] ax = {1, 0}, ay = {0, 1};
	private double avel = 0;
	private double dumperK = 0.9;
	private double moment = 10.0;

	public PL_RigidBody(){
		
	}
	public PL_RigidBody(List target) {
		initialize(target);
	}
	public void setTarget(Object target){
		initialize(UtilCast.o2list(target));
	}
	private void initialize(List target){
		N = target.size();
		vertexList = new MassPoint[N];
		if(target.get(0) instanceof String){
			// TODO: 将来的には選択してメニューから呼んでもStringで渡されるべき
			Mediator med = Mediator.getInstance();
			for(int i = 0; i < N; i++){
				vertexList[i] = (MassPoint) med.getVertex((String) target.get(i));
			}
		}else{
			target.toArray(vertexList);
		}
		double[] gp = {0, 0};
		for(int i = 0; i < N; i++){
			MassPoint v = vertexList[i];
			Vec.addD(gp, v.getPosition());
		}
		gp = Vec.scale(gp, 1.0 / N);
		dirList = new double[N][];
		magList = new double[N];

		for(int i = 0; i < N; i++){
			MassPoint v = vertexList[i];
			double[] diff = Vec.sub(v.getPosition(), gp);
			dirList[i] = Vec.normalize(diff);
			magList[i] = Vec.mag(diff);
		}
	}

	public boolean apply(int iter) {
		boolean isSatisfied = true;
		if(iter == 0){
			return false; // not to satisfy on first iter
		}
		if(iter < tolerance){
			double[] gp = {0, 0};
			for(int i = 0; i < N; i++){
				MassPoint v = vertexList[i];
				Vec.addD(gp, v.getPosition());
			}
			gp = Vec.scale(gp, 1.0 / N);

			avel *= dumperK;
			double rot = 0.0;
			for(int i = 0; i < N; i++){
				MassPoint v = vertexList[i];
				double[] diff = Vec.sub(v.getPosition(), gp);
				double[] dir = Vec.normalize(diff);
				
				double[] oldDir = dirList[i];
				double[] trueDir = 
					Vec.add(
							Vec.scale(ax, oldDir[0]),
							Vec.scale(ay, oldDir[1]));
				rot += (trueDir[0] * dir[1] - trueDir[1] * dir[0]) * magList[i];
			}
			avel += rot / moment;
			double[] nax = Vec.normalize(Vec.add(ax, Vec.scale(ay, avel)));
			ay = Vec.normalize(Vec.add(ay, Vec.scale(ax, - avel)));
			ax = nax;

            ViewportTransformer vp = Infrastructure.getViewportTransformer();
			for(int i = 0; i < N; i++){
				MassPoint v = vertexList[i];

				double[] oldDiff = dirList[i];
				double[] truePos = Vec.add(
						gp,
						Vec.scale(
						Vec.add(
							Vec.scale(ax, oldDiff[0]),
							Vec.scale(ay, oldDiff[1])), magList[i]));

				double[] updateVec = Vec.sub(truePos, v.getPosition());
				
				if(Vec.mag(vp .scaling(updateVec)) > 0.1){
	            	isSatisfied = false;
	                v.getDVelList().add(updateVec);
	            }

			}

		}
		return isSatisfied;
	}


	public Hashtable getParams() {
		Hashtable result = super.getParams();
		return result;
	}
	

}
