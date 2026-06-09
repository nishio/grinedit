package org.nishiohirokazu.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Hashtable;

import org.nishiohirokazu.awt.ColorHolder;
import org.nishiohirokazu.grinEdit.UtilCast;
import org.nishiohirokazu.vector.Vec;

public class ArrowEdge extends RenderableEdge {

	private Color color;
	private double nagasa = 7.0;
	private double yokohaba = 4.0;
	private double kaesi = 5.0;

	public ArrowEdge() {
		color = Color.BLACK;
	}

	public void render(Object target) {
		double[] p1=v1.getTerminal(this.id), p2=v2.getTerminal(this.id);

		Graphics g = (Graphics) target;
		if(selected){
			g.setColor(ColorHolder.SELECTED_EDGE);
		}else{
			g.setColor(color);
		}

		g.drawLine(
				(int)p1[0],(int)p1[1],
				(int)p2[0],(int)p2[1]);

		double[] ndir=Vec.normalize(Vec.sub(p1, p2));
		drawArrow(g, p2, ndir);

	}
	private void drawArrow(Graphics g, double[] end, double[] ndir) {
		double[] cdir = Vec.rot90(ndir);

		double[] nagasaVec = Vec.scale(ndir, nagasa);
		double[] yokohabaVec = Vec.scale(cdir, yokohaba);
		double[] kaesiVec = Vec.scale(ndir, kaesi);

		double[] a0 = end;
		double[] a1 = Vec.add(a0, Vec.add(nagasaVec, yokohabaVec));
		double[] a2 = Vec.add(a0, kaesiVec);
		double[] a3 = Vec.add(a0, Vec.sub(nagasaVec, yokohabaVec));
		Polygon p = new Polygon();
		p.addPoint((int) a0[0], (int) a0[1]);

		p.addPoint((int) a1[0], (int) a1[1]);
		p.addPoint((int) a2[0], (int) a2[1]);
		p.addPoint((int) a3[0], (int) a3[1]);
		g.fillPolygon(p);
	}


	public void setColor(int r, int g, int b) {
		color = ColorHolder.get(r, g, b);
	}
	
	public void setColor(Object rgb){
		color = UtilCast.o2awtColor(rgb);
	}

	public Hashtable getParams(){
		Hashtable result = super.getParams();
		result.put("color", UtilCast.Color2Vector(color));
		return result;
	}

}
