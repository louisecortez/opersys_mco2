package caltrain3;

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
		//ListIterator<Passenger> itPassenger = listPassengers.listIterator();
		
		System.out.println("NotifyPassengers() List size: " + listPassengers.size());
		for(Passenger p : listPassengers) {
			System.out.println("Notify Passengers");
			System.out.println(currTrain.toString());
			if(p.boardTrain(currTrain)) {
				this.removePassenger(p);
			}	
			System.out.println("/notify passengers");
		}
		
//		for (Iterator<Passenger> it = listPassengers.iterator(); it.hasNext(); ) {
//		    Passenger pIt = it.next();
//		    System.out.println("Notify Passengers");
//			System.out.println(currTrain.toString()); 
//			if(pIt.boardTrain(currTrain)) {
//				this.removePassenger(pIt);
//			}
//			System.out.println("/notify passengers");
//		}
		
//		//synchronized(itPassenger) {
//			while(itPassenger.hasNext()) {
//				System.out.println("Notify Passengers");
//				System.out.println(currTrain.toString());
//				(itPassenger.next()).boardTrain(currTrain);		
//				System.out.println("/notify passengers");
//			}
//		//}
		
		
	}
}
