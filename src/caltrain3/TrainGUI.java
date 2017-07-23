package caltrain3;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

public class TrainGUI extends Thread{

	Popup father;
	Rectangle body;
	Simulation sims;
	int index;
	
	public TrainGUI(Popup father, int index, Simulation sims){
		this.father = father;
		this.index = index;
		this.sims = sims;
		
	}
	
	public void run(){
		body = new Rectangle(sims.)
	}
	
	/*
	public void run(){
		for(;;) {
		       body   = new Rectangle2D(x, y + 10, 60, 10);   
	          
			
			            //father.repaint();
			
			            try {
			
			                Thread.sleep(100L);
			
			            }
			
			            catch(Exception e) {
			
			            }
			
			        }
	}
	
	public void paint(Graphics2D g2) {
		   g2.draw(body);
	       g2.draw(frontTire);
           g2.draw(rearTire);
    }
    */
}
