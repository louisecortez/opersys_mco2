package caltrain3;


import java.util.ArrayList;
import java.util.List;

import javafx.animation.TranslateTransition;
import javafx.animation.RotateTransition;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Simulation;
import values.ImageSources;

public class APopup {

	private Stage windowSim;
	private Pane layout;
	
	private Button btnTogglePause;
	private Button passengerWalk;
	private Button passengerAlight;
	
	// Represented by Threads
	private List<Label> listLblTrain;
	private List<Label> listLblPassenger;
	
	// Background
	private List<StackPane> listStckPassenger;
	private List<StackPane> listStckPTrack;
	private List<StackPane> listStckPStation;
	
	private List<HBox> listStationPassContainer;
	private List<HBox> listTrainPassContainer;
	private List<Label> listLblTrack;
	private List<Label> listLblStation;
	
	// Grid settings
	private final int COL_CNT = 9;
	private final int ROW_CNT = 8;
	
    private Simulation simulation; 		
    private Controller controller;	
    
    private static final int TRACK_CNT = 16;
    public int trainDuration = 400;
    public int passDuration = 500;
    
    public APopup(Controller c) {
    	controller = c;
    	simulation = controller.getSimulation();
    	
    	listLblPassenger = new ArrayList<Label>();
    	listLblTrain = new ArrayList<Label>();
    	
    	listStckPassenger = new ArrayList<StackPane>();
    	listStckPTrack = new ArrayList<StackPane>();
    	listStckPStation = new ArrayList<StackPane>();
    	
    	listLblTrack = new ArrayList<Label>();
    	listLblStation = new ArrayList<Label>();
    	
    	listStationPassContainer = new ArrayList<HBox>();
    	listTrainPassContainer = new ArrayList<HBox>();
    	
    	this.initComponents();
    }
    
    public void initComponents() {
		
		windowSim = new Stage();
		layout = new GridPane();
		layout.setStyle("-fx-background-color: #cce4ed");
		//((GridPane)layout).setGridLinesVisible(true);
		
		System.out.println("WIDTH: " + ImageSources.getImgTrack().getWidth());
		System.out.println("HEIGHT: " + ImageSources.getImgTrack().getHeight());
		
		// Initialize station labels and station passenger container
		for(int i = 0; i < 8; i++) {
			Label station = new Label("", new ImageView(ImageSources.getImgStation()));
			
			HBox stationPassContainer = new HBox(0);
			stationPassContainer.setMaxHeight(ImageSources.getImgPassenger(0).getHeight());
			stationPassContainer.setAlignment(Pos.BOTTOM_CENTER);
			StackPane stack = new StackPane();

			StackPane.setAlignment(stationPassContainer, Pos.BOTTOM_CENTER);
			GridPane.setHalignment(station, HPos.CENTER);
			
			listLblStation.add(station);
			listStationPassContainer.add(stationPassContainer);
			listStckPStation.add(stack);
			
			stack.getChildren().add(station);
			stack.getChildren().add(stationPassContainer);
			
			GridPane.setValignment(stack, VPos.BOTTOM);
		}
		
		// Initialize stack panes
		for(int i = 0; i < TRACK_CNT; i++) {
			StackPane stack = new StackPane();
			Label track;
			if(i == 7) {
				track = new Label("", new ImageView(ImageSources.getImgTrackR()));
				stack.getChildren().add(track);
				
				GridPane.setValignment(stack, VPos.TOP);
				stack.setMaxWidth(ImageSources.getImgTrackR().getWidth());
				stack.setMaxHeight(ImageSources.getImgTrackR().getHeight());
			} else if(i == 15) {
				track = new Label("", new ImageView(ImageSources.getImgTrackL()));
				stack.getChildren().add(track);
				
				GridPane.setValignment(stack, VPos.TOP);
				stack.setMaxWidth(ImageSources.getImgTrackL().getWidth());
				stack.setMaxHeight(ImageSources.getImgTrackL().getHeight());
				
			} else {
				track = new Label("", new ImageView(ImageSources.getImgTrack()));				
				stack.getChildren().add(track);
				
				GridPane.setValignment(track, VPos.BOTTOM);
				stack.setMaxWidth(ImageSources.getImgTrack().getWidth());
				stack.setMaxHeight(ImageSources.getImgTrack().getHeight());
				
			}
			listLblTrack.add(track);
			listStckPTrack.add(stack);
		}
		
		// Add track stackpane on layout
		for(int i = 0; i < listStckPTrack.size(); i++) {
			if(i == 7) {
				((GridPane)layout).add(listStckPTrack.get(i), 8, 4);
			} else if(i == 15) {
				((GridPane)layout).add(listStckPTrack.get(i), 0, 4);
			} else {
				if(i < 7) {
					((GridPane)layout).add(listStckPTrack.get(i), i + 1, 3);
				} else {
					((GridPane)layout).add(listStckPTrack.get(i), i - 2 * (i - 7) + 1, 4);
				}
			}
		}
		
		// Add station stackpane on layout
		for(int i = 0; i < listLblStation.size(); i++) {
			if(i <4)
				((GridPane)layout).add(listStckPStation.get(i), i * 2 + 1, 2);
			else
				((GridPane)layout).add(listStckPStation.get(i), (i - 2 * (i - 4)) * 2 - 1, 6);
		}
		
		// Initialize button
		btnTogglePause = new Button("Next");
		btnTogglePause.setOnAction(e -> {
			moveTrainGUI(0);
			//moveTrainGUI(14);
			//moveTrainGUI(13);
		});
		
		passengerWalk = new Button("Board");
		passengerWalk.setOnAction(e -> {
			System.out.println("Passengers start walking");
			boardPTrain(0);
			//boardPTrain(1);
		});
		
		passengerAlight = new Button("Alight");
		passengerAlight.setOnAction(e -> {
			System.out.println("Passengers start alighting");
			alightPTrain(0);
			//alightPTrain(1);
		});
		
		((GridPane)layout).add(btnTogglePause, 1, 0);
		((GridPane)layout).add(passengerWalk, 2, 0);
		((GridPane)layout).add(passengerAlight, 3, 0);
		
		// Initialize column / row constraints
		for(int i = 0; i < COL_CNT; i++) {
			ColumnConstraints colCon = new ColumnConstraints(ImageSources.getImgTrack().getWidth());
			if(i == 0 || i == 8)
				colCon = new ColumnConstraints(ImageSources.getImgTrack().getHeight());
			((GridPane)layout).getColumnConstraints().add(colCon);
		}
		for(int i = 0; i < ROW_CNT; i++) {
			RowConstraints rowCon = new RowConstraints(ImageSources.getImgTrack().getHeight());
			if(i == 4) {
				rowCon = new RowConstraints(ImageSources.getImgTrack().getWidth() + 25);
			}
			rowCon.setValignment(VPos.BOTTOM);
			((GridPane)layout).getRowConstraints().add(rowCon);
		}
    }
    
    /**
     * 
     * @param trainLabel
     * @return
     */
    public int getTrainTrackPosition(Label trainLabel) {
    		
    	for(int i = 0; i < listStckPTrack.size(); i++) {
    		for(int j = 0; j < listStckPTrack.get(i).getChildren().size(); j++) {
    			if(listStckPTrack.get(i).getChildren().get(j) == trainLabel) 
    				return i;
    		}
    	}
    	
    	return -1;
    }
    
//    public int getPassengerPosition(Label passPosition) {
//		
//    	for(int i = 0; i < listLblPassenger.size(); i++) {
//    		//for(int j = 0; j < listStckPStation.get(i).getChildren().size(); j++) {
//    			if(listLblPassenger.get(i) == passPosition) 
//    				return i;
//    		//}
//    	}
//    	
//    	return -1;
//    }
    
	public int getPassengerPosition(StackPane passPosition) {

		for (int i = 0; i < listStckPassenger.size(); i++) {
			// for(int j = 0; j < listStckPStation.get(i).getChildren().size();
			// j++) {
			if (listStckPassenger.get(i) == passPosition)
				return i;
			// }
		}

		return -1;
	}
    
    public void moveTrainGUI(int trainIndex) {
    	System.out.println("--------------------MOVE TRAIN ANIMATION");
    	Label movLabel = listLblTrain.get(trainIndex);
		int origIndex = getTrainTrackPosition(movLabel); 
		
		System.out.println("TRAIN INDEX: " + origIndex);
		System.out.println("START Train track curr: " + getTrainTrackPosition(movLabel));
		if(origIndex < 6) {
			// Move to the right
			
			((StackPane)movLabel.getParent()).getChildren().remove(movLabel);
			listStckPTrack.get((origIndex + 1) % 16).getChildren().add(movLabel);
			
			TranslateTransition trtr = new TranslateTransition(Duration.millis(trainDuration), movLabel);
			trtr.setFromX(ImageSources.getImgTrack().getWidth() * -1);
			trtr.setByX(ImageSources.getImgTrack().getWidth());
			trtr.play();
		} else if(origIndex == 6) {
			// Move to the right, rotate
			RotateTransition rotr = new RotateTransition(Duration.millis(trainDuration), movLabel);
		    rotr.setByAngle(90f);
		    
		    TranslateTransition trtr = new TranslateTransition(Duration.millis(trainDuration), movLabel);
			trtr.setByX(ImageSources.getImgTrack().getHeight() * 2);
			trtr.setByY(ImageSources.getImgTrack().getWidth() - 50);
			
			ParallelTransition patr = new ParallelTransition();
			patr.getChildren().add(trtr);
			patr.getChildren().add(rotr);
			patr.play();
			
			patr.setOnFinished(ee -> {
				System.out.println("patr finish");
				movLabel.setGraphic(new ImageView(ImageSources.getImgTrainR()));
				((StackPane)movLabel.getParent()).getChildren().remove(movLabel);
				listStckPTrack.get((origIndex + 1) % 16).getChildren().add(movLabel);
				
				RotateTransition ro = new RotateTransition(Duration.millis(1), movLabel);
			    ro.setByAngle(270);
			    
			    TranslateTransition tr = new TranslateTransition(Duration.millis(1), movLabel);
				tr.setByX(ImageSources.getImgTrack().getHeight() * -2);
				tr.setByY((ImageSources.getImgTrack().getWidth() - 50) * -1);
				
				ParallelTransition pa = new ParallelTransition();
				pa.getChildren().add(tr);
				pa.getChildren().add(ro);
				pa.play();
				System.out.println("END Train track curr: " + getTrainTrackPosition(movLabel));
			});
		} else if(origIndex == 7) {
			// Move to the left, rotate
			RotateTransition rotr = new RotateTransition(Duration.millis(trainDuration), movLabel);
		    rotr.setByAngle(90f);
		    
		    TranslateTransition trtr = new TranslateTransition(Duration.millis(trainDuration), movLabel);
			trtr.setByX(ImageSources.getImgTrack().getHeight() * -2);
			trtr.setByY(ImageSources.getImgTrack().getWidth() - 50);
			
			ParallelTransition patr = new ParallelTransition();
			patr.getChildren().add(trtr);
			patr.getChildren().add(rotr);
			patr.play();
			patr.setOnFinished(ee -> {
				System.out.println("patr finish");
				movLabel.setGraphic(new ImageView(ImageSources.getImgTrainD()));
				((StackPane)movLabel.getParent()).getChildren().remove(movLabel);
				listStckPTrack.get((origIndex + 1) % 16).getChildren().add(movLabel);
				
				RotateTransition ro = new RotateTransition(Duration.millis(1), movLabel);
			    ro.setByAngle(270);
			    
			    TranslateTransition tr = new TranslateTransition(Duration.millis(1), movLabel);
				tr.setByX(ImageSources.getImgTrack().getHeight() * 2);
				tr.setByY((ImageSources.getImgTrack().getWidth() - 50) * -1);
				
				ParallelTransition pa = new ParallelTransition();
				pa.getChildren().add(tr);
				pa.getChildren().add(ro);
				pa.play();
				System.out.println("END Train track curr: " + getTrainTrackPosition(movLabel));
			});
			
		} else if(origIndex == 14) {
			// Move to the left, rotate
			RotateTransition rotr = new RotateTransition(Duration.millis(trainDuration), movLabel);
		    rotr.setByAngle(-90);

		    TranslateTransition trtr = new TranslateTransition(Duration.millis(trainDuration), movLabel);
			trtr.setByX(ImageSources.getImgTrack().getHeight() * -2);
			trtr.setByY((ImageSources.getImgTrack().getHeight() + 25) * -1);
			
			ParallelTransition patr = new ParallelTransition();
			patr.getChildren().add(trtr);
			patr.getChildren().add(rotr);
			patr.play();
			
			patr.setOnFinished(ee -> {
				RotateTransition ro = new RotateTransition(Duration.millis(1), movLabel);
			    ro.setByAngle(90);
			    
			    TranslateTransition tr = new TranslateTransition(Duration.millis(1), movLabel);
				tr.setByX(ImageSources.getImgTrack().getHeight() * 2);
				tr.setByY((ImageSources.getImgTrack().getHeight() + 25));
				
				ParallelTransition pa = new ParallelTransition();
				pa.getChildren().add(tr);
				pa.getChildren().add(ro);
				pa.play();
				System.out.println("END Train track curr: " + getTrainTrackPosition(movLabel));
			
				movLabel.setGraphic(new ImageView(ImageSources.getImgTrainL()));
			    ((StackPane)movLabel.getParent()).getChildren().remove(movLabel);
				listStckPTrack.get((origIndex + 1) % 16).getChildren().add(movLabel);
			});
			
		} else if(origIndex == 15) {
			// Move to the right, rotate
			RotateTransition rotr = new RotateTransition(Duration.millis(trainDuration), movLabel);
		    rotr.setByAngle(90f);
		    
		    TranslateTransition trtr = new TranslateTransition(Duration.millis(trainDuration), movLabel);
			trtr.setByX(ImageSources.getImgTrack().getHeight() * 2);
			trtr.setByY((ImageSources.getImgTrack().getWidth() - 50) * -1);
			
			ParallelTransition patr = new ParallelTransition();
			patr.getChildren().add(trtr);
			patr.getChildren().add(rotr);
			patr.play();
			patr.setOnFinished(ee -> {
				System.out.println("patr finish");
				movLabel.setGraphic(new ImageView(ImageSources.getImgTrain()));
				((StackPane)movLabel.getParent()).getChildren().remove(movLabel);
				listStckPTrack.get((origIndex + 1) % 16).getChildren().add(movLabel);
				
				RotateTransition ro = new RotateTransition(Duration.millis(1), movLabel);
			    ro.setByAngle(-90);
			    
			    TranslateTransition tr = new TranslateTransition(Duration.millis(1), movLabel);
				tr.setByX(ImageSources.getImgTrack().getHeight() * -2);
				tr.setByY((ImageSources.getImgTrack().getWidth() - 50));
				
				ParallelTransition pa = new ParallelTransition();
				pa.getChildren().add(tr);
				pa.getChildren().add(ro);
				pa.play();
				System.out.println("END Train track curr: " + getTrainTrackPosition(movLabel));
			});
		} else if(origIndex < 15) {
			// Move to the left
			System.out.println("Train track curr: " + origIndex);
			
			((StackPane) movLabel.getParent()).getChildren().remove(movLabel);
			listStckPTrack.get((origIndex + 1) % 16).getChildren().add(movLabel);
			
			TranslateTransition trtr = new TranslateTransition(Duration.millis(trainDuration), movLabel);
			trtr.setFromX(ImageSources.getImgTrack().getWidth());
			trtr.setByX(ImageSources.getImgTrack().getWidth() * -1);
			trtr.play();
		} 
		
		System.out.println("END Train track curr: " + getTrainTrackPosition(movLabel));
		
	}
    
	public void display(){
		
		//trying Task huhu
	     Task<Void> taskTrain = new Task<Void>() {
	         @Override protected Void call() throws Exception {
	               
	                 Platform.runLater(new Runnable() {                          
	                        @Override
	                        public void run() {
	                        
	                                //FX Stuff done here
	                           
	                        }
	                });
	             
	             return null;
	         }
	     };
		
		// Initialize train labels
		for (int i = 0; i < simulation.getTrainCnt(); i++) {
			if (i < 7) {
				listLblTrain.add(new Label("", new ImageView(ImageSources.getImgTrain())));
			} else if (i == 7) {
				listLblTrain.add(new Label("", new ImageView(ImageSources.getImgTrainR())));
			} else if (i == 15) {
				listLblTrain.add(new Label("", new ImageView(ImageSources.getImgTrainL())));
			} else {
				listLblTrain.add(new Label("", new ImageView(ImageSources.getImgTrainD())));
			}
		}
		
		// Add train labels to stack pane
		if(simulation.getTrainCnt() <= 8) {
			for(int i = 0; i < simulation.getTrainCnt(); i++ ) {
				listStckPTrack.get(i * 2).getChildren().add(listLblTrain.get(i));
				if(i * 2 > 7)
					listLblTrain.get(i).setGraphic(new ImageView(ImageSources.getImgTrainD()));
			}
		} else{
			for(int i = 0; i < simulation.getTrainCnt(); i++ ) {
				listStckPTrack.get(i).getChildren().add(listLblTrain.get(i));
			}
		}
		
		
		// Place passenger labels to stations
		for (int i = 0; i < simulation.getPassengerCnt(); i++) {
			
			listLblPassenger.add(new Label("", new ImageView(ImageSources.getImgPassenger((int )(Math.random() * 4)))));
			listStckPassenger.add(new StackPane());
			
			listStckPassenger.get(i).getChildren().add(listLblPassenger.get(i));
			
			Text lblPass = new Text("#" + (i + 1));
			lblPass.setFont(Font.font("Sans Serif", FontWeight.BOLD, 25));
			//lblPass.setFill(Color.WHITE);
			lblPass.setId("lblPass");
			StackPane.setMargin(lblPass, new Insets(0, 0, 50, 0));
			StackPane.setAlignment(listLblPassenger.get(i), Pos.BOTTOM_CENTER);
			
			listStckPassenger.get(i).getChildren().add(lblPass);
			listStckPassenger.get(i).setMaxHeight(ImageSources.getImgPassenger(1).getHeight());
			
			StackPane.setAlignment(listStckPassenger.get(i), Pos.BOTTOM_CENTER);
			
			int station = simulation.getPassenger(i).getOrigin() / 2;
			(listStationPassContainer.get(station)).getChildren().add(listStckPassenger.get(i));
			
			if(listStationPassContainer.get(station).getChildren().size() > 5)
				listStationPassContainer.get(station).setSpacing(-10);
			if(listStationPassContainer.get(station).getChildren().size() > 10)
				listStationPassContainer.get(station).setSpacing(-20);
		}	
		
		Scene scene = new Scene(layout);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		
		System.out.println("b");
		windowSim.initModality(Modality.APPLICATION_MODAL);
		System.out.println("o");
		windowSim.setTitle("Simulation");
		System.out.println("o");
		windowSim.setScene(scene);
		System.out.println("m");
		
		System.out.println(".");
		
		simulation.startSimulation();
		windowSim.showAndWait();
	}
	
	// added method
//		public void boardPTrain(int passIndex) {
//			Label movLabel = listLblPassenger.get(passIndex);
//			int origIndex = getPassengerPosition(movLabel); 
//			
//			System.out.println(origIndex);
//			
//			System.out.println("PausePlay");
//			System.out.println("PASSENGER #" + getPassengerPosition(movLabel) + " is boarding");
//			
//			int startIndex = simulation.getPassenger(passIndex).getOrigin();
//			double yToValue;
//			
//			if(startIndex < 8) {
//				yToValue = ImageSources.getImgPassenger(origIndex).getHeight() + 1;
//			} else {
//				yToValue = ImageSources.getImgStation().getHeight() * -1;
//			}
//			
//			TranslateTransition trtr = new TranslateTransition(Duration.millis(500), movLabel);
//			trtr.setFromY(ImageSources.getImgPassenger(origIndex).getHeight());
//			trtr.setToY(yToValue);
//			trtr.play(); 
//			
//			trtr.setOnFinished(new EventHandler<ActionEvent>() {
//				public void handle(ActionEvent event) {
//					FadeTransition ft = new FadeTransition(Duration.millis(500), movLabel);
//					ft.setFromValue(1.0);
//					ft.setToValue(0.0);
//					ft.play();
//				}
//			});
//		}
	
	public void boardPTrain(int passIndex) {
		StackPane movLabel = listStckPassenger.get(passIndex);
		int origIndex = getPassengerPosition(movLabel); 
		
		System.out.println(origIndex);
		
		System.out.println("PausePlay");
		System.out.println("PASSENGER #" + getPassengerPosition(movLabel) + " is boarding");
		
		int startIndex = simulation.getPassenger(passIndex).getOrigin();
		double yToValue;
		
		if(startIndex < 8) {
			yToValue = ImageSources.getImgPassenger(origIndex).getHeight() + 1;
		} else {
			yToValue = ImageSources.getImgStation().getHeight() * -1;
		}
		
		TranslateTransition trtr = new TranslateTransition(Duration.millis(500), movLabel);
		trtr.setFromY(ImageSources.getImgPassenger(origIndex).getHeight());
		trtr.setToY(yToValue);
		trtr.play(); 
		
		trtr.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				FadeTransition ft = new FadeTransition(Duration.millis(500), movLabel);
				ft.setFromValue(1.0);
				ft.setToValue(0.0);
				ft.play();
				
				int station = simulation.getPassenger(origIndex).getOrigin() / 2;
				System.out.println("Orig index: " + origIndex + " origin: " + station);
			    (listStationPassContainer.get(station)).getChildren().remove(movLabel);
			}
		});
		//(listStationPassContainer.get(simulation.getPassenger(origIndex).getDestination() / 2)).getChildren().add(movLabel);
	}
		
		public void alightPTrain(int passIndex) {
			StackPane movLabel = listStckPassenger.get(passIndex);
			int origIndex = getPassengerPosition(movLabel); 
			
//			(listStationPassContainer.get(simulation.getPassenger(passIndex).getOrigin() / 2)).getChildren().remove(movLabel);
//			(listStationPassContainer.get(simulation.getPassenger(passIndex).getDestination() / 2)).getChildren().add(movLabel);
//			
			System.out.println("PausePlay");
			System.out.println("PASSENGER #" + getPassengerPosition(movLabel) + " is alighting");
			
			int endIndex = simulation.getPassenger(passIndex).getDestination();
			double yFromValue; 
			double yToValue;
			
			
			if(endIndex < 8) {
				yFromValue = ImageSources.getImgStation().getHeight();
				yToValue = ImageSources.getImgPassenger(origIndex).getHeight() - 65;
			} else {
				yFromValue = ImageSources.getImgPassenger(origIndex).getHeight() * -0.5;
				yToValue = ImageSources.getImgStation().getHeight() - 1; 
			}
			
//			int station = simulation.getPassenger(origIndex).getDestination() / 2;
//		    (listStationPassContainer.get(station)).getChildren().add(movLabel);
			
			FadeTransition ft = new FadeTransition(Duration.millis(passDuration), movLabel);
			ft.setFromValue(0.0);
			ft.setToValue(1.0);
			ft.play();
			
			ft.setOnFinished(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					TranslateTransition trtr = new TranslateTransition(Duration.millis(passDuration), movLabel);
					trtr.setFromY(yFromValue);
					trtr.setToY(yToValue);
					trtr.play(); 
					
					int station = simulation.getPassenger(origIndex).getDestination() / 2;
				    (listStationPassContainer.get(station)).getChildren().add(movLabel);
					
					trtr.setOnFinished(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {
							FadeTransition ft = new FadeTransition(Duration.millis(passDuration), movLabel);
							ft.setFromValue(1.0);
							ft.setToValue(0.0);
							ft.play();
						}
					});
				}
			});
		}
}
    
