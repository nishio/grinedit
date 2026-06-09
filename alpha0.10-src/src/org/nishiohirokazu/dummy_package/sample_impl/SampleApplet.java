package org.nishiohirokazu.dummy_package.sample_impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.JApplet;

import org.nishiohirokazu.graph.RenderableVertex;
import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.grinEdit.mouseOperation.MO_MoveVertex;
import org.nishiohirokazu.grinEdit.mouseOperation.MO_Scaling;
import org.nishiohirokazu.grinEdit.mouseOperation.MouseMediator;

public class SampleApplet extends JApplet {
	private Mediator med;
	private Vector vertexList = new Vector();
	private Dimension size;
	private Image bgImg;
	private Graphics bg;

	public void start(){
		med = Mediator.getInstance();

		vertexList.add(med.graph.addVertex());
		for(int i = 1; i < 100; i++){
			RenderableVertex v = med.graph.addVertex();
			vertexList.add(v);
			med.graph.addEdge((RenderableVertex) vertexList.get(i / 2), v);
		}

		size = getSize();
		bgImg = createImage(size.width, size.height);
		bg = bgImg.getGraphics();


		MouseMediator mm = med.getMouseMediator();
		addMouseListener(mm);
		addMouseMotionListener(mm);
		
		mm.add("move", new MO_MoveVertex(true));
		mm.add("scale", new MO_Scaling());
		mm.setLeft("move");
		mm.setRight("scale");
		
		
		Timer t = new Timer();
		TimerTask tt = new TimerTask() {
			public void run() {
				updateScreen();
			}

		};
		t.schedule(tt, 0, 10);
	}
	public void updateScreen() {
		if (!med.pause) {
			med.graph.layoutStep();
			Graphics g = getGraphics();
			if(g != null){
				bg.setColor(Color.WHITE);
				bg.fillRect(0 , 0 , size.width , size.height);
				bg.setColor(Color.BLACK);
				med.graph.render(bg);
				g.drawImage(bgImg, 0, 0, this);
			}
		}
	}

}
