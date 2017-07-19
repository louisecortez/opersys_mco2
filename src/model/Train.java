package model;

import java.util.ArrayList;
import java.util.List;

public class Train implements Runnable {

	private Station stationCurr;
	private Simulation simulation; // added attribute
	private List<Passenger> listPassenger;
	private final int capacity;
	
	/**
	 * 
	 * @param capacity	Maximum passenger capacity of the train.
	 */
	public Train(int capacity, Simulation simulation) { // added simulation
		listPassenger = new ArrayList<Passenger>();
		this.capacity = capacity;
		this.simulation = simulation;
	}
	
	public void setStationCurr(Station station) {
		stationCurr = station;
	}
	

	/**
	 * Called by the Passenger class. Passenger attempts to board the train.
	 * 
	 * @param p	
	 * @return whether the passenger successfully boards the train
	 */
	public synchronized boolean boardTrain(Passenger p) {
		if(listPassenger.size() < capacity) {
			listPassenger.add(p);
			return true;
		}
		else return false;
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
			if(p.isDestination(stationCurr)) {
				listAlightThread.add(new Thread(new Runnable() {

					@Override
					public void run() {
						// passenger exit
						alightTrain(p);
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
		// loop
			if(stationCurr != null) {
				// unload passengers on the train, if pwede (mutex'd)
				passengersUnload();
				
				// let passengers in on train, if may space pa. (mutex'd)
				stationCurr.notifyPassengers();
			
				// signal simulation to move to the next track.
				// wait until all trains are ready.
				
				
			}
	}

}
