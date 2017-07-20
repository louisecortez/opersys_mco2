package model;

public class Passenger implements Runnable {

	private int testId;
	
	private Train train;
	private Station initStation;
	private Station destination; // compare if curr place of train is passenger's destination
	private Simulation simulation;
	
	public Passenger(int id, Simulation simulation) {
		testId = id;
		this.simulation = simulation;
	}
	
	public Passenger(int id, Station destination, Simulation simulation) {
		testId = id;
		this.destination = destination;
		this.simulation = simulation;
	}
	
	public Passenger(int id, Station initStation, Station destination, Simulation simulation) {
		testId = id;
		this.initStation = initStation;
		this.destination = destination;
		this.simulation = simulation;
	}
	
	public void setInitStation(Station s) {
		initStation = s;
	}
	
	public void setDestination(Station s) {
		destination = s;
	}
	
	public boolean isDestination(Track destination) {
		return this.destination == destination;
	}
	
	public synchronized void boardTrain(Train t) {
		//synchronized(this) {
		System.out.println("Board train (Passenger)");
		System.out.println(t.toString());
			if(t.boardTrain(this)) {
				initStation.removePassenger(this);
				this.train = t;
				System.out.println("Passenger " + testId + " boarded a train.");
				notify();
			}
		//}
	}
	
	public void leaveTrain() {
		synchronized(this) {
			System.out.println("Passenger " + testId + " leaveTrain() execute.");
			train = null;
			notify();
		}
	}
	
	@Override
	public void run() {
		
		synchronized(this) {
			// start by waiting at the station
			System.out.println("Passenger " + testId + " waits for a train.");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// (assuming it wakes up when boardTrain)0
			// add train sa passenger
			
			// wait for destination
			System.out.println("Passenger " + testId + " waits inside a train.");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
			// wake up only if train is in the destination station : WOKEN UP BY TRAIN
			// leaveTrain
			simulation.passengerExitSim(this);
			System.out.println("Passenger " + testId + " successfully unboarded.");
		}
		
	}

	@Override
	public String toString() {
		return "Passenger [testId=" + testId + ", initStation=" + initStation + ", destination=" + destination + "]";
	}

}
