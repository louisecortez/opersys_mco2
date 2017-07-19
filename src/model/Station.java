package model;

import java.util.ArrayList;
import java.util.List;

public class Station {
	
	private int index;								// could be changed to station name
	private List<Passenger> listPassengers;
	private Train currTrain;
	
	public Station(int index) {
		this.index = index;
		listPassengers = new ArrayList<Passenger>();
	}
	
	public void setCurrTrain(Train t) {
		currTrain = t;
	}
	
	public synchronized void addPassenger(Passenger p) {
		listPassengers.add(p);
	}
	
	public synchronized void removePassenger(Passenger p) {
		listPassengers.remove(p);
	}
	
	/**
	 * This method notifies the passengers waiting on the station if there are free seats on the train that just arrived.
	 */
	public void notifyPassengers() {
		List<Thread> listThread = new ArrayList<Thread>();
		
		for(Passenger p : listPassengers) {
			listThread.add(new Thread(new Runnable() {

				@Override
				public void run() {
					p.boardTrain(currTrain);
				}
				
			}));
		}
		
		for(Thread t : listThread) {
			t.start();
		}
	}
}
