package model;

public class Passenger implements Runnable, Pausable {

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
	
	public int getOrigin() {
		return initStation.getId();
	}
	
	public int getDestination() {
		return destination.getId();
	}
	
	public int getId() {
		return testId;
	}
	
	public boolean isDestination(Track destination) {
		return this.destination == destination;
	}
	
	public synchronized boolean boardTrain(Train t) {
		System.out.println("\tBoard train() (Passenger) " + testId);
		if(t.boardTrain(this)) {
			this.train = t;
			System.out.println("\tPassenger " + testId + " boarded a train.");
			notify();
			return true;
		}
		return false;
	}
	
	public void leaveTrain() {
		synchronized(this) {
			System.out.println("\tPassenger " + testId + " leaveTrain() execute.");
			train = null;
			notify();
		}
	}
	
	/**
	 * HOW PASSENGER WORKS
	 * 
	 * - When its thread starts running, Passenger object waits until a Train visits its initialStation(origin)
	 * - Thread continues only if there is a Station that successfully boards this Passenger to a Train.
	 * - Waits again until it's Train object reaches the Passenger object's destination.
	 * - Passenger exits Train, finishes simulation.
	 */
	@Override
	public void run() {
		
		synchronized(this) {
			// start by waiting at the station
			System.out.println("\tPassenger " + testId + " waits for a train.");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// (assuming it wakes up when boardTrain)
			
			// wait for destination
			System.out.println("\tPassenger " + testId + " waits inside a train.");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
			// wake up only if train is in the destination station : WOKEN UP BY TRAIN
			// leaveTrain
			simulation.passengerExitSim(this);
			System.out.println("\tPassenger " + testId + " successfully unboarded.");
		}
		
	}

	@Override
	public String toString() {
		return "Passenger [testId=" + testId + ", initStation=" + initStation + ", destination=" + destination + "]";
	}

	@Override
	public synchronized void pauseSim() {
		try {
			simulation.pauseThread();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
