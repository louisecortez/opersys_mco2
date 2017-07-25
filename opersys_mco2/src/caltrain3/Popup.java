package caltrain3;

import java.util.ArrayList;
import java.util.HashMap;

import caltrain3.PathTransitionExample.LineToAbs;
import caltrain3.PathTransitionExample.MoveToAbs;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;
import javafx.util.Duration;
import model.Simulation;


public class Popup {
	
	// ComboBox destination; 
    int ctr; 								//to check if initial
	int num=2; 								//sample number of trains
    PathTransition transition; 				//transition
    										//ArrayList<Integer> destlist; //destination of trains
    										//ArrayList<Integer> origlist; //origin of trains or where they cme from
    HashMap currmap;
    
    
    private Simulation sims; 			//simulator of the popup, has th tracker
    private Controller controller;		//where you will get the simulator
    
    //constructor
    public Popup(){
    	ctr=0;
    	//destlist = new ArrayList<Integer>();
    	//origlist = new ArrayList<Integer>();
    	currmap = new HashMap();
    }
	
    public Popup(Simulation sims){
    	this.sims = sims;
    	ctr=0;
    	//destlist = new ArrayList<Integer>();
    	//origlist = new ArrayList<Integer>();
    	currmap = new HashMap();
    }
    
    
    //displays the rectangles on the scene
	public void display(Controller controller){
		
		this.controller = controller;
		sims = controller.getSimulation();
		sims.startSimulation();
		//window of the popup
		Stage popupwindow=new Stage();
		
		//group of elements in the window
		Group root = new Group();
		
		//the group of rectangles
		ArrayList<Rectangle> statlist = new ArrayList<>();
		ArrayList<Rectangle> rectlist = new ArrayList<>();
		//origlist.add(1);
		//origlist.add(2);
		//destlist.add(2);
		//destlist.add(3);
		
		//GET THE LIST OF CURRENT TRACKS OF THE TRAINS
		for (int i=0; i<sims.getSizeTrain(); i++){
			currmap.put(i,sims.getTrains().get(i).getCurrTrack().getId());
		}
		
		
		//add all rectangles made into the arraylist
		for(int i = 0; i<sims.getSizeTrain(); i++){
			Rectangle rect = new Rectangle(50, 50);
	        rect.setStroke(Color.BLUE); 
	        rect.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.3));
	        setPositionFixed( rect);
	       // rect.relocate(100, 80);
	        rectlist.add(rect);
	        root.getChildren().add(rectlist.get(i));
	        
	        // set initial position of the nodes
	        double toX = 0;
         	double toY = 0;
         	
         	toX = setInitPosX((Integer)currmap.get(i));
         	toY = setInitPosY((Integer)currmap.get(i));
         	
         	rectlist.get(i).relocate(toX, toY);
	         
		}
		ctr++;
		
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Simulation");
		
		//adding scene to window      
		Scene scene1= new Scene(root, 1280, 700);
		scene1.getStylesheets().add(Popup.class.getResource("sim.css").toExternalForm());
		
		/*
		 AnimationTimer timer = new AnimationTimer() {
		        @Override
		        public void handle(long now) {
		        	while (sims.getListPassSize() != 0)
		        		for (int i = 0; i<sims.getSizeTrain(); i++){
			         		double toX = 0;
			              	double toY = 0;
			              	
			              	toX = setInitPosX((Integer)currmap.get(i));
			              	toY = setInitPosY((Integer)currmap.get(i));
			              	
			              	rectlist.get(i).relocate(toX, toY);
			         	
			            }
		        }
		    };
    	
    	
    
	timer.start();
		*/
		
		
		//when mouse is clicked
		/*
		scene1.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {

            @Override
            public void handle(Event event) {

                transition.stop();
                Path path = new Path();
                

                //double toX = ((MouseEvent) event).getX();
                //double toY = ((MouseEvent) event).getY();

                for (int i=0; i<num; i++){
                	setPositionFixed( rectlist.get(i));
                	double toX = 0;
                	double toY = 0;
                	
                	toX = setDestPosX(destlist.get(i));
                	toY = setDestPosY(destlist.get(i));
                			
                	path = new Path();
                    path.getElements().add(new MoveToAbs(rectlist.get(i)));
                    path.getElements().add(new LineToAbs(rectlist.get(i), toX, toY));

                }
                
                transition.setPath(path);
                transition.play();

            }

		});    
		*/
		popupwindow.setScene(scene1);
		      
		popupwindow.showAndWait();
		
	}
	
	//put coordinate x in the if statements
	public double setInitPosX(int originNum){
		
		switch(originNum){
		case 0: return 60;
		case 1: return 80;
		case 2: return 100;
		case 3: return 120;
		case 4: return 140;
		case 5: return 150;
		case 6: return 180;
		case 7: return 200;
		case 8: return 220;
		case 9: return 200;
		case 10: return 180;
		case 11: return 160;
		case 12: return 140;
		case 13: return 120;
		case 14: return 100;
		case 15: return 80;
		default: return 0;
		}
		
	}


	public double setInitPosY(int originNum){
		switch(originNum){
		case 0: return 60;
		case 1: return 30;
		case 2: return 30;
		case 3: return 30;
		case 4: return 30;
		case 5: return 30;
		case 6: return 30;
		case 7: return 30;
		case 8: return 60;
		case 9: return 90;
		case 10: return 90;
		case 11: return 90;
		case 12: return 90;
		case 13: return 90;
		case 14: return 90;
		case 15: return 90;
		default: return 0;
		}
	}

	public double setDestPosX(int destNum){
		switch(destNum){
		case 1: return 20;
		case 2: return 20;
		case 3: return 20;
		case 4: return 20;
		case 5: return 20;
		case 6: return 20;
		case 7: return 20;
		case 8: return 20;
		case 9: return 20;
		case 10: return 20;
		case 11: return 20;
		case 12: return 20;
		case 13: return 20;
		case 14: return 20;
		case 15: return 20;
		case 16: return 20;
		default: return 0;
		}
	}
	
	public double setDestPosY(int destNum){
		switch(destNum){
		case 1: return 20;
		case 2: return 20;
		case 3: return 20;
		case 4: return 20;
		case 5: return 20;
		case 6: return 20;
		case 7: return 20;
		case 8: return 20;
		case 9: return 20;
		case 10: return 20;
		case 11: return 20;
		case 12: return 20;
		case 13: return 20;
		case 14: return 20;
		case 15: return 20;
		case 16: return 20;
		default: return 0;
		}
	}
	public void setOrigin(ArrayList<Integer> orig){
		
	}
	
	public void setDest(ArrayList<Integer> dest){
		
	}
		
	public void setNumTrain(int num){
		this.num = num;
	}
	
	public void setTrack(int track, String train){
		
	}
	
	 // change layout to current position, reset translate
    private void setPositionFixed( Node node) {
        double x = node.getLayoutX() + node.getTranslateX();
        double y = node.getLayoutY() + node.getTranslateY();
        node.relocate(x, y);
        node.setTranslateX(0);
        node.setTranslateY(0);
    }
}
    
