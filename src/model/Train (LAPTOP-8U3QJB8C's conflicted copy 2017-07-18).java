package model;

import java.util.ArrayList;
import java.util.List;

public class Train implements Runnable {

	private Station stationCurr;
	private List<PassengerR> listPassenger;
	private final int capacity;
	
	public Train(int capacity) {
		listPassenger = new ArrayList<PassengerR>();
		stationCurr = new Station(1);
		this.capacity = capacity;
	}
	
	public synchronized boolean boardTrain(PassengerR p) {
		if(listPassenger.size() < capacity) {
			listPassenger.add(p);
			p.boardTrain(this);
			return true;
		}
		return false;
	}
	
	public synchronized void alightTrain(PassengerR p) {
		listPassenger.remove(p);
		p.leaveTrain();
	}
	
	public void passengersUnload() {
		List<Thread> listAlightThread = new ArrayList<Thread>();
		
		for(PassengerR p : listPassenger) {
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
			// unload passengers on the train, if pwede (mutex'd)
			passengersUnload();
			
			// let passengers in on train, if may space pa. (mutex'd)
			stationCurr.notifyPassengers();
		
			// if full na or kung walang magboboard ng passenger, signal simulation na move sa track
		
	}

}
