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
