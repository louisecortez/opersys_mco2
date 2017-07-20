package model;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
	
	private Train[] tracks;
	private List<Station> listStation;
	private List<Train> listTrain;
	private List<Passenger> listPassenger;
	
	private List<Thread> listPThread;
	
	private int totalPassengers;
	private int totalExited;
	
	public Simulation() {
		tracks = new Train[16];
		
		listStation = new ArrayList<Station>();
		listTrain = new ArrayList<Train>();
		listPassenger = new ArrayList<Passenger>();
		
		listPThread = new ArrayList<Thread>();
		
		totalPassengers = 9;
	}
	
	public void passengerExitSim(Passenger p) {
		totalExited++;
		listPassenger.remove(p);
	}
	
	public boolean allTrainsReady() {
		// 
		
		return false;
	}
	
	// TEST FUNCTION
	public void initComponents() {
		int trainCnt = 2;
		int stationCnt = 8;
		
		// Initialize stations
		for(int i = 0; i < stationCnt; i++) {
			listStation.add(new Station(i));
		}
		
		// Initialize trains
		for(int i = 0; i < trainCnt; i++) {
			listTrain.add(new Train(2, this));
		}
		
		// Place train on tracks
		tracks[2] = listTrain.get(0);
		tracks[7] = listTrain.get(1);
		
		// Assign station to train
		listTrain.get(0).setStationCurr(listStation.get(2 / 2));
		
		// Initialize (Runnable) passengers
		for(int i = 0; i < 3; i++) {
			listPassenger.add(new Passenger(i, listStation.get(2), this));
			listPassenger.add(new Passenger(i + 3, listStation.get(5), this));
			listPassenger.add(new Passenger(i + 6, listStation.get(6), this));
		}
		
		// Place passengers on their initial stations
		for(Passenger p : listPassenger) {
			listStation.get(0).addPassenger(p);
			p.setInitStation(listStation.get(0));
		}
		
		// Create threads for all passengers
		for(int i = 0; i < listPassenger.size(); i++) {
			listPThread.add(new Thread(listPassenger.get(i)));
		}
		
		// Start passenger threads
		
		
		// Start train threads
		
		while(!allTrainsReady()) {
			
		}
		
	}
}
