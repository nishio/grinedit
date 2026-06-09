package org.nishiohirokazu.dummy_package.sample_impl;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.nishiohirokazu.graph.RenderableVertex;
import org.nishiohirokazu.grinEdit.Mediator;
import org.nishiohirokazu.layout.Repulsion;

public class SampleImpl extends JFrame implements ActionListener {
	private Mediator med;

	private ArrayList buttonList = new ArrayList();
	private ArrayList vertexList = new ArrayList();

	public static void main(String[] args) {
		JFrame frame = new SampleImpl();
		frame.setSize(300, 200);
		frame.setVisible(true);
	}

	public SampleImpl() {
		med = Mediator.getInstance();
		Repulsion rep = (Repulsion) med.graph.aggregator.getLaw(1);
		rep.repulsionK = 1;
		rep.repulsionRadius = 20;
		
		addNewVertex(50.0, 50.0);

		setLayout(null);

		Timer t = new Timer();
		TimerTask tt = new TimerTask() {
			public void run() {
				updateScreen();
			}
		};
		t.schedule(tt, 0, 10);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent arg) {
		if (arg.getActionCommand().equals("AddNewVertex")) {
			JButton b = (JButton) arg.getSource();
			Rectangle r = b.getBounds(); 
			addNewVertex(r.x, r.y);
		}
	}
	
	public void addNewVertex(double x, double y){
		Random r = new Random();
		JButton b = new JButton();
		b.setActionCommand("AddNewVertex");
		b.addActionListener(this);
		b.setBounds(
				(int)x,
				(int)y,
					10, 10);
		getContentPane().add(b);
		buttonList.add(b);

		RenderableVertex v = med.graph.addVertex();
		double[] pos = {x + r.nextDouble(), (y + r.nextDouble())}; 
		v.position = pos;
		vertexList.add(v);
	}
	
	public void updateButtonPos(){
		for(int i = 0; i < buttonList.size(); i++){
			JButton b = (JButton) buttonList.get(i);
			RenderableVertex v = (RenderableVertex) vertexList.get(i);
			b.setBounds(
				(int) v.position[0],
				(int) v.position[1], 10, 10);
			
		}
	}

	public void updateScreen() {
		if (!med.pause) {
			med.graph.layoutStep();
			updateButtonPos();
		}
	}
}