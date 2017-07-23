package caltrain3;

import java.util.ArrayList;
import java.util.List;

public class Train implements Runnable {

	private int testId;
	
	private Track stationCurr; // current place of train
	private Simulation simulation; // added attribute
	private List<Passenger> listPassenger;
	private final int capacity;
	
	private TrackManager ttracker;
	// TEST
	
	/**
	 * 
	 * @param capacity	Maximum passenger capacity of the train.
	 */
//	public Train(int capacity, Simulation simulation) { // added simulation
//		listPassenger = new ArrayList<Passenger>();
//		this.capacity = capacity;
//		this.simulation = simulation;
//	}
	
	public Train(int testId, int capacity, TrackManager tracker, Simulation simulation) {
		this.testId = testId;
		listPassenger = new ArrayList<Passenger>();
		this.capacity = capacity;
		this.simulation = simulation;
		ttracker = tracker;
	}
	
	public int getId() {
		return testId;
	}
	
	public void setStationCurr(Track station) {
		stationCurr = station;
	}

	/**
	 * Called by the Passenger class. Passenger attempts to board the train.
	 * 
	 * @param p	
	 * @return whether the passenger successfully boards the train
	 */
	public boolean boardTrain(Passenger p) {
		synchronized(listPassenger) {
			if(listPassenger.size() < capacity) {
				System.out.println("Train " + testId + " accepts Passenger " + p.getId());
				listPassenger.add(p);
				return true;
			}
			else return false;
		}
	}
	
	
	/**
	 * 
	 * @param p
	 */
	public synchronized void alightTrain(Passenger p) {
		listPassenger.remove(p);
		p.leaveTrain();
	}
	
	/**
	 * Unloads passengers whose destination is the current station of the train.
	 */
	public void passengersUnload() {
		List<Thread> listAlightThread = new ArrayList<Thread>();
		
		for(Passenger p : listPassenger) {
			final Passenger pass = p;
			if(p.isDestination(stationCurr)) {
				listAlightThread.add(new Thread(new Runnable() {

					@Override
					public void run() {
						// passenger exit
						alightTrain(pass);
					}
					
				}));
			}
		}
		
		for(Thread t : listAlightThread) {
			t.start();
		}
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Train " + testId + " start running.");
		while(!simulation.allPassengersExited()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Train " + testId + " at station " + stationCurr.toString() + " checks if station instance.");
			//System.out.println("Current train at current station: " + stationCurr.getCurrTrain().toString());
			if(stationCurr instanceof Station) {
				System.out.println("Train " + testId + " is station instance.");
				// unload passengers on the train, if pwede (mutex'd)
				System.out.println("Train " + testId + " unloads passengers.");
				passengersUnload();
				System.out.println("Train " + testId + " loads passengers.");
				// let passengers in on train, if may space pa. (mutex'd)
				((Station) stationCurr).notifyPassengers();						
			}
			System.out.println("Train " + testId + " checks if it can move forward.");
			if(ttracker.moveTrain(this, stationCurr)) {
				System.out.println("Train " + testId + " moves forward.");
				//ttracker.moveTrain(this, stationCurr);
			} else {
				System.out.println("Train " + testId + " waits before moving forward.");
				ttracker.waitOnNextTrack(this, stationCurr);
				System.out.println("Train " + testId + " moves forward.");
				ttracker.moveTrain(this, stationCurr);
			}
		}
		System.out.println("Train " + testId + " exits simulation");
	}

	public Track getCurrTrack() {
		return stationCurr;
	}
	
	@Override
	public String toString() {
		return "Train " + testId;
	}
}
