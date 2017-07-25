package caltrain3;

import javafx.concurrent.Task;
import model.Passenger;
import model.Train;
import model.Simulation;

public class Controller {
	public static final int STATION_COUNT = 8;
	private MainView view;
	private Simulation s;
	private APopup pops;
	
	public Controller(){
		s = new Simulation(this);
	}
	
	public void addController(){
		view.addCon(this);
	}
	
	/*
	public void withgui(){
		view = new MainView();
		addController();
		s = new Simulation();
	}
	*/
	
	public Simulation getSimulation(){
		return s;
	}
	
	public APopup getPops() {
		return pops;
	}
	
	public void setPops(APopup pops) {
		this.pops = pops;
	}
	
	public void addPass(int o, int d, int id){
		Passenger pTemp  =new Passenger(id,s);
		s.passengerEnterSim(pTemp, o * 2 - 2, d * 2 - 2);
		//System.out.println("Size of listPass: " + s.getSizePass());
	}
	
	public void addTrain(int max, int num, int id){
		//s.setTrains(s.setTrainCnt(num), max);
		s.setTrains(num, max);
		//System.out.println("Size of listTrain: " + s.getSizeTrain());
	}
	
	
}
