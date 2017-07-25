package caltrain3;

import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainView extends Application {

	Stage window;
	Scene input, sim;
	TextField inTest;
	ComboBox origin;
	ComboBox destination;
	TextField maxTrain;
	ComboBox numTrain;
	int ctrT;
	int ctrP;
	int maxval;
	int numt;
	int origp;
	int destp;

	private Controller controller;

	public MainView() {
		ctrT = 0;
		ctrP = 0;
	}

	public void addCon(Controller c) {
		controller = c;
	}

	public int getMax() {
		return maxval;
	}

	public int getNumT() {
		return numt;
	}

	public int getOrigP() {
		return origp;
	}

	public int getDestP() {
		return destp;
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("CalTrainII Simulation!");

		controller = new Controller();

		// GridPane with 10px padding around edge
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);

		maxTrain = new TextField();
		Label orig = new Label("Origin Station: ");
		Label dest = new Label("Destination: ");
		Label max = new Label("Maximum train load: ");
		Label num = new Label("Number of trains: ");

		origin = new ComboBox();
		destination = new ComboBox();
		numTrain = new ComboBox();
		Label train = new Label("TRAIN");
		Label pass = new Label("PASSENGER");

		train.getStyleClass().add("labels");
		pass.getStyleClass().add("labels");

		Button addP = new Button("Add Passenger");
		Button addT = new Button("Add Trains");
		Button simulate = new Button("Simulate");
		Button reset = new Button("Reset");
		TextArea feed = new TextArea();

		for (int i = 0; i < 8; i++) {
			origin.getItems().add("Station " + (i + 1));
			destination.getItems().add("Station " + (i + 1));
		}

		inTest = new TextField();
		maxTrain = numOnly(inTest);
		GridPane.setConstraints(maxTrain, 1, 1);

		for (int i = 0; i < 16; i++) {
			numTrain.getItems().add((i + 1));
		}

		addT.setOnAction(e -> {
			maxval = Integer.valueOf(maxTrain.getText());

			String n = "" + numTrain.getValue();
			numt = Integer.parseInt(n);
			// return number of trains
			controller.addTrain(maxval, numt, ctrT);
			ctrT++;

		});

		addP.setOnAction(e -> {
			String o = (String) origin.getValue();
			origp = Integer.parseInt(o.substring(8));
			// add origin station index with the integer ^^^^

			String d = (String) destination.getValue();
			destp = Integer.parseInt(d.substring(8));
			// add destination station index with the integer ^^^^
			controller.addPass(origp, destp, ctrP);
			ctrP++;

		});

		simulate.setOnAction(e -> {
			pass.setVisible(false);
			orig.setVisible(false);
			origin.setVisible(false);
			dest.setVisible(false);
			destination.setVisible(false);
			addP.setVisible(false);
			train.setVisible(false);
			max.setVisible(false);
			maxTrain.setVisible(false);
			num.setVisible(false);
			numTrain.setVisible(false);
			addT.setVisible(false);
			simulate.setVisible(false);
			reset.setVisible(true);

			simThis();
		});

		reset.setOnAction(e -> {
			pass.setVisible(true);
			orig.setVisible(true);
			origin.setVisible(true);
			dest.setVisible(true);
			destination.setVisible(true);
			addP.setVisible(true);
			train.setVisible(true);
			max.setVisible(true);
			maxTrain.setVisible(true);
			num.setVisible(true);
			numTrain.setVisible(true);
			simulate.setVisible(true);
			addT.setVisible(true);
		});

		GridPane.setConstraints(train, 0, 0);
		GridPane.setConstraints(max, 0, 1);
		GridPane.setConstraints(maxTrain, 1, 1);
		GridPane.setConstraints(num, 0, 2);
		GridPane.setConstraints(numTrain, 1, 2);
		GridPane.setConstraints(addT, 1, 3);
		GridPane.setConstraints(pass, 0, 5);
		GridPane.setConstraints(orig, 0, 6);
		GridPane.setConstraints(origin, 1, 6);
		GridPane.setConstraints(dest, 0, 7);
		GridPane.setConstraints(destination, 1, 7);
		GridPane.setConstraints(addP, 1, 8);
		GridPane.setConstraints(simulate, 1, 12);
		GridPane.setConstraints(reset, 1, 13);
		GridPane.setConstraints(feed, 1, 14);

		grid.getChildren().addAll(pass, orig, origin, dest, destination, addP, train, max, maxTrain, num,
				numTrain, addT, simulate, reset);
		Scene scene = new Scene(grid, 600, 800);
		scene.getStylesheets().add(this.getClass().getResource("init.css").toExternalForm());
		window.setScene(scene);
		window.show();
	}

	public TextField numOnly(TextField inTest) {
		inTest.textProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					inTest.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

		return inTest;
	}

	public void simThis() {
		//Popup popup = new Popup();

		//popup.display(controller);
		APopup ap = new APopup(controller);
		controller.setPops(ap);
		ap.display();
	}

}
