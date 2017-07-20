package model;

import java.util.ArrayList;
import java.util.List;

public class Station extends Track {
	
	private List<Passenger> listPassengers; // passengers waiting 
	
	public Station(int index) {
		super(index);
		listPassengers = new ArrayList<Passenger>();
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
	public synchronized void notifyPassengers() {
	
		for(Passenger p : listPassengers) {
			System.out.println("Notify Passengers");
			System.out.println(currTrain.toString());
			p.boardTrain(currTrain);		
			System.out.println("/notify passengers");
		}
		
	}
}
