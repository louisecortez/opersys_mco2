package model;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
	
	//private List<Station> listStation;
	private List<Train> listTrain;
	private List<Passenger> listPassenger;
	//private TrainController tc;
	private List<Thread> listPThread;
	private List<Thread> listTThread;
	
	private int totalPassengers;
	private int totalExited;
	
	private TrackManager ttracker;
	
	public Simulation() {
		
		//listStation = new ArrayList<Station>();
		listTrain = new ArrayList<Train>();
		listPassenger = new ArrayList<Passenger>();
		
		listPThread = new ArrayList<Thread>();
		listTThread = new ArrayList<Thread>();
		
		ttracker = new TrackManager();
		
		totalPassengers = 3;
		totalExited = 0;
	}
	
	public void passengerExitSim(Passenger p) {
		System.out.println("Passenger exits");
		totalExited++;
		listPassenger.remove(p);
	}

	public boolean allPassengersExited() {
		return totalPassengers == totalExited;
	}
	
	// TEST FUNCTION
	public void initComponents() {
		
		// Initialize stations
		//for(int i = 0; i < stationCnt; i++) {
			//listStation.add(new Station(i));
		//}
		
		// Initialize trains
		for(int i = 0; i < 1; i++) {
			listTrain.add(new Train(i, 2, ttracker, this));
		}
		
		// Assign station to train
		ttracker.getTrack(0).setCurrTrain(listTrain.get(0));
		System.out.println(ttracker.getTrack(0).getCurrTrain().toString());
		ttracker.getTrack(4).setCurrTrain(listTrain.get(1));
		
		
		// Assign train to station
		listTrain.get(0).setStationCurr(ttracker.getTrack(0));
		listTrain.get(1).setStationCurr(ttracker.getTrack(4));
		System.out.println(ttracker.getTrack(0).getCurrTrain().toString());
		
		// Initialize (Runnable) passengers
		for(int i = 0; i < 3; i++) {
			listPassenger.add(new Passenger(i, (Station) ttracker.getTrack(6), (Station) ttracker.getTrack(4), this));
			//listPassenger.add(new Passenger(i + 3, (Station) ttracker.getTrack(2), (Station) ttracker.getTrack(8), this));
			//listPassenger.add(new Passenger(i + 6, (Station) ttracker.getTrack(8), (Station) ttracker.getTrack(6), this));
		}
		
		// Place passengers on their initial stations
		for(int i = 0; i < 3; i++) {
			((Station) ttracker.getTrack(6)).addPassenger(listPassenger.get(i));
			//((Station) ttracker.getTrack(2)).addPassenger(listPassenger.get(i + 3));
			//((Station) ttracker.getTrack(8)).addPassenger(listPassenger.get(i + 6));
		}
		
		// Create threads for all passengers
		for(int i = 0; i < listPassenger.size(); i++) {
			listPThread.add(new Thread(listPassenger.get(i)));
		}
		
		for(Train t : listTrain) {
			listTThread.add(new Thread(t));
		}
		
		// Start train threads
		for(Thread t : listTThread) {
			t.start();
		}
		
		// Start passenger threads
		for(Thread t : listPThread) {
			t.start();
		}
	}
}
