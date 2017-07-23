package caltrain3;

public class Controller {
	public static final int STATION_COUNT = 8;
	private MainView view;
	private Simulation s;
	
	public Controller(){
		s = new Simulation();
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
	
	public void addPass(int o, int d, int id){
		Passenger pTemp  =new Passenger(id,s);
		s.passengerEnterSim(pTemp, o * 2 - 2, d * 2 - 2);
		System.out.println("Size of listPass: " + s.getSizePass());
	}
	
	public void addTrain(int max, int num, int id){
		//s.setTrains(s.setTrainCnt(num), max);
		s.setTrains(num, max);
		System.out.println("Size of listTrain: " + s.getSizeTrain());
	}
	
	
	
}
