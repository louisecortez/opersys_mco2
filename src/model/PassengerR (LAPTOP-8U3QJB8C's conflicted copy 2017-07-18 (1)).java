package model;

public class PassengerR implements Runnable {

	private int testId;
	
	private Train train;
	private Station destination;
	
	public PassengerR(int id, Station destination) {
		testId = id;
	}
	
	public boolean isDestination(Station destination) {
		return this.destination == destination;
	}
	
	
	/**
	 * 
	 * @throws InterruptedException
	 */
	/*
	public void waitForTrain() throws InterruptedException {
		synchronized(this) {
			System.out.println("Passenger " + testId + " waits for a train.");
			wait();
		}
	}
	*/
	
	public void boardTrain(Train t) {
		synchronized(this) {
			System.out.println("Passenger can board a train.");
			if(t.boardTrain(this)) {
				notify();
				train = t;
			}
		}
	}
	
	public void leaveTrain() {
		synchronized(this) {
			System.out.println("Passenger " + testId + " leaves boarded train.");
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
			
			// wake up only if there is a train in the station with FREE SEATS : WOKEN UP BY STATION
			// add train sa passenger
			// (assuming it wakes up from boardTrain, called by station)
			System.out.println("Passenger " + testId + " is currently riding the train.");
			
			// wait for destination
			
			// wake up only if train is in the destination station : WOKEN UP BY TRAIN
		}
		
		
		
		// /thread
		
	}

}
