package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class Station extends Track {
	
	private List<Passenger> listPassengers; // passengers waiting 
	
	public Station(int index) {
		super(index);
		//listPassengers = new ArrayList<Passenger>();
		listPassengers = new CopyOnWriteArrayList<Passenger>();
	}
	
	/**
	 * Add passenger to the station.
	 * @param p Passenger added to the station.
	 */
	public synchronized void addPassenger(Passenger p) {
		listPassengers.add(p);
	}
	
	/**
	 * Remove passenger from the station. Used in simulation before Passenger boards a train.
	 * @param p Passenger removed from the station.
	 */
	public synchronized void removePassenger(Passenger p) {
		listPassengers.remove(p);
	}
	
	/**
	 * This method notifies the passengers waiting on the station if there are free seats on the train that just arrived.
	 */
	public void notifyPassengers() {
	
		System.out.println("---NotifyPassengers() List size: " + listPassengers.size());
		for(Passenger p : listPassengers) {
			System.out.println("---Notify Passengers");
			System.out.println("---" + currTrain.toString());
			if(p.boardTrain(currTrain)) {
				this.removePassenger(p);
			}
			System.out.println("---/notify passengers");
		}	
		
		
	}
}
