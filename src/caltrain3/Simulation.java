package caltrain3;

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
		
		totalPassengers = 0;
		totalExited = 0;
	}
	
	public void passengerEnterSim(Passenger p, int origin, int destination) {
		totalPassengers++;
		
		listPassenger.add(new Passenger(p.getId(), (Station) ttracker.getTrack(origin), (Station) ttracker.getTrack(destination), this));
	}
	
	public void passengerExitSim(Passenger p) {
		System.out.println("Passenger exits");
		totalExited++;
		listPassenger.remove(p);
	}
	
	public void setTrains(int trainCnt, int capacity) {
		Train traintemp;
		
		for(int i = 0; i < trainCnt; i++) {
			traintemp = new Train(i, capacity, ttracker, this);
			listTrain.add(traintemp);
			System.out.println("listTrain is setTrains():  " + listTrain.size());
		}
	}
	
	public int setTrainCnt(int capacity) {
		int tcntTemp;
		
		tcntTemp = listPassenger.size() / capacity;
		
		if(tcntTemp == 0)
			return 1;
		else if(tcntTemp < 15) 
			return tcntTemp;
		else return 15;
	}
	
	public void placePassenger(int indexTrack, int indexPassenger) {
		System.out.println("sim getTrack()" + ttracker.getTrack(indexTrack));
		System.out.println("sim get(indexPassenger)" + listPassenger.get(indexPassenger));
		((Station) ttracker.getTrack(indexTrack)).addPassenger(listPassenger.get(indexPassenger));
	}
	
	public void assignStationToTrain(int indexTrack, int indexTrain) {
		ttracker.getTrack(indexTrack).setCurrTrain(listTrain.get(indexTrain));
	}
	
	public void assignTrainToStation(int indexTrack, int indexTrain) {
		listTrain.get(indexTrain).setStationCurr(ttracker.getTrack(indexTrack));
	}
	
	//method that will get the current track of a certain train
	public int getCurrTrack(int indexTrain){
		return listTrain.get(indexTrain).getCurrTrack().getId();
	}
	
	//method that will get the list of trains
	public List<Train> getTrains(){
		return listTrain;
	}
	
	//method that will get the list of passengers
	public List<Passenger> getPassengers(){
		return listPassenger;
	}
	
	//method that will get the size of listTrain
	public int getSizeTrain(){
		return listTrain.size();
	}
	
	//method that will get the size of the listPassenger;
	public int getSizePass(){
		return listPassenger.size();
	}
	
	
	public void initializePThread() {
		for(int i = 0; i < listPassenger.size(); i++) {
			listPThread.add(new Thread(listPassenger.get(i)));
		}
	}
	
	public void initializeTThread() {
		for(Train t : listTrain) {
			listTThread.add(new Thread(t));
		}
	}

	public boolean allPassengersExited() {
		return totalPassengers == totalExited;
	}
	//ADDED THIS
	public int getListPassSize(){
		return listPThread.size();
	}
	
	public List<Thread> getListP(){
		return listPThread;
	}
	
	public List<Thread> getListT(){
		return listTThread;
	}
	
	// TEST FUNCTION
	public void startSimulation() {
		
		// Initialize stations
		//for(int i = 0; i < stationCnt; i++) {
			//listStation.add(new Station(i));
		//}
		
		// Initialize trains
		//setTrains(2, 2);
		
		// Assign station to train
		/*assignStationToTrain(4, 0);
		assignStationToTrain(5, 1);
		
		
		// Assign train to station
		assignTrainToStation(4, 0);
		assignTrainToStation(5, 1);*/
		
		// Initialize (Runnable) passengers
		/*for(int i = 0; i < 3; i++) {
			listPassenger.add(new Passenger(i, (Station) ttracker.getTrack(6), (Station) ttracker.getTrack(4), this));
			//listPassenger.add(new Passenger(i + 3, (Station) ttracker.getTrack(2), (Station) ttracker.getTrack(8), this));
			//listPassenger.add(new Passenger(i + 6, (Station) ttracker.getTrack(8), (Station) ttracker.getTrack(6), this));
		}*/
		
		// initial train-station assignments
		int j = 0; // index of tracks
		for(int i = 0; i < listTrain.size(); i++) {
			assignStationToTrain(j, i);
			assignTrainToStation(j, i);
			
			//CHANGED INDEX FROM i TO j
			System.out.println("This is the track: " + ttracker.getTrack(j));
			System.out.println("This train: " + ttracker.getTrack(j).getCurrTrain());
			System.out.println(ttracker.getTrack(j).getCurrTrain().toString());
			
			
			j += 2;
		}
		
		// Place passengers on their initial stations		
		for(int i = 0; i < listPassenger.size(); i++) {
			placePassenger(listPassenger.get(i).getOrigin(), listPassenger.get(i).getId());
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

//package model;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Simulation {
//	
//	//private List<Station> listStation;
//	private List<Train> listTrain;
//	private List<Passenger> listPassenger;
//	//private TrainController tc;
//	private List<Thread> listPThread;
//	private List<Thread> listTThread;
//	
//	private int totalPassengers;
//	private int totalExited;
//	
//	private TrackManager ttracker;
//	
//	public Simulation() {
//		
//		//listStation = new ArrayList<Station>();
//		listTrain = new ArrayList<Train>();
//		listPassenger = new ArrayList<Passenger>();
//		
//		listPThread = new ArrayList<Thread>();
//		listTThread = new ArrayList<Thread>();
//		
//		ttracker = new TrackManager();
//		
//		totalPassengers = 25;
//		totalExited = 0;
//	}
//	
//	public synchronized void passengerExitSim(Passenger p) {
//		System.out.println("Passenger exits");
//		totalExited++;
//		listPassenger.remove(p);
//	}
//
//	public boolean allPassengersExited() {
//		return listPassenger.size() == 0;
//	}
//	
//	// TEST FUNCTION
//	public void initComponents() {
//		
//		// Initialize trains
//		for(int i = 0; i <2; i++) {
//			listTrain.add(new Train(i, 2, ttracker, this));
//		}
//		listTrain.add(new Train(2, 1, ttracker, this));
//		
//		// Assign station to train
//		ttracker.getTrack(4).setCurrTrain(listTrain.get(0));
//		ttracker.getTrack(5).setCurrTrain(listTrain.get(1));
//		ttracker.getTrack(6).setCurrTrain(listTrain.get(2));
//		
//		// Assign train to station
//		listTrain.get(0).setStationCurr(ttracker.getTrack(4));
//		listTrain.get(1).setStationCurr(ttracker.getTrack(5));
//		listTrain.get(2).setStationCurr(ttracker.getTrack(6));
//		
//		// Initialize (Runnable) passengers
//		for(int i = 0; i < totalPassengers; i++) {
//			listPassenger.add(new Passenger(i, (Station) ttracker.getTrack(6), (Station) ttracker.getTrack(4), this));
//			//listPassenger.add(new Passenger(i + 3, (Station) ttracker.getTrack(2), (Station) ttracker.getTrack(8), this));
//			//listPassenger.add(new Passenger(i + 6, (Station) ttracker.getTrack(8), (Station) ttracker.getTrack(6), this));
//		}
//		
//		// Place passengers on their initial stations
//		for(int i = 0; i < totalPassengers; i++) {
//			((Station) ttracker.getTrack(6)).addPassenger(listPassenger.get(i));
//			//((Station) ttracker.getTrack(2)).addPassenger(listPassenger.get(i + 3));
//			//((Station) ttracker.getTrack(8)).addPassenger(listPassenger.get(i + 6));
//		}
//		
//		// Create threads for all passengers
//		for(int i = 0; i < listPassenger.size(); i++) {
//			listPThread.add(new Thread(listPassenger.get(i)));
//		}
//		
//		for(Train t : listTrain) {
//			listTThread.add(new Thread(t));
//		}
//		
//		// Start train threads
//		for(Thread t : listTThread) {
//			t.start();
//		}
//		
//		// Start passenger threads
//		for(Thread t : listPThread) {
//			t.start();
//		}
//	}
//}