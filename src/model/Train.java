package model;

import java.util.ArrayList;
import java.util.List;

public class Train implements Runnable, Pausable {

	private int testId;
	
	private Track stationCurr; // current place of train
	private Simulation simulation; // added attribute
	private List<Passenger> listPassenger;
	private final int capacity;
	
	private TrackManager ttracker;
	
	// Pausable vars
	private boolean paused;

	/**
	 * 
	 * @param capacity	Maximum passenger capacity of the train.
	 */
	public Train(int testId, int capacity, TrackManager tracker, Simulation simulation) {
		this.testId = testId;
		listPassenger = new ArrayList<Passenger>();
		this.capacity = capacity;
		this.simulation = simulation;
		ttracker = tracker;
	}
	
	public int getId() {
		return testId;
	}
	
	public void setStationCurr(Track station) {
		stationCurr = station;
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	/**
	 * Called by the Passenger class. Passenger attempts to board the train.
	 * 
	 * @param p	
	 * @return whether the passenger successfully boards the train
	 */
	public boolean boardTrain(Passenger p) {
		synchronized(listPassenger) {
			if(listPassenger.size() < capacity) {
				System.out.println("Train " + testId + " accepts Passenger " + p.getId());
				listPassenger.add(p);
				return true;
			}
			else return false;
		}
	}
	
	/**
	 * Removes a Passenger object from the Train. 
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
			final Passenger pass = p;
			if(p.isDestination(stationCurr)) {
				listAlightThread.add(new Thread(new Runnable() {

					@Override
					public void run() {
						// passenger exit
						alightTrain(pass);
					}
					
				}));
			}
		}
		
		for(Thread t : listAlightThread) {
			t.start();
		}
		
		for(Thread t : listAlightThread) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * HOW TRAINS WORK
	 * 
	 * - Train threads continue until there are no more Passenger objects in the simulation.
	 * - If the Train object's current track is an instance of a Station:
	 * 		- Train object removes all of its Passenger objects whose destination is the current Station.
	 * 		- Tells the current station to transfer all of its Passenger objects to this Train while the capacity allows it.
	 * 	- Train attempts to move to the next Track (through a TrackManager).
	 *  - If the next Track is occupied by a train, Train object waits on the next Track, notified only when the next Track is free.
	 */
	@Override
	public void run() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Train " + testId + " start running.");
		while(!simulation.allPassengersExited()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(paused) {
				pauseSim();
			}

			System.out.println("Train " + testId + " at station " + stationCurr.toString() + " checks if station instance.");
			if(stationCurr instanceof Station) {
				System.out.println("Train " + testId + " is station instance.");
				
				if(paused) {
					pauseSim();
				}
				// unload passengers on the train
				System.out.println("Train " + testId + " unloads passengers.");
				passengersUnload();
				
				if(paused) {
					pauseSim();
				}
				// let passengers in on train
				System.out.println("Train " + testId + " loads passengers.");
				((Station) stationCurr).notifyPassengers();						
			}
			
			System.out.println("Train " + testId + " checks if it can move forward.");
			if(ttracker.moveTrain(this, stationCurr)) {
				
				if(paused) {
					pauseSim();
				}
				System.out.println("Train " + testId + " moves forward.");
				
			} else {
				
				if(paused) {
					pauseSim();
				}
				System.out.println("Train " + testId + " waits before moving forward.");
				ttracker.waitOnNextTrack(this, stationCurr);
				
				if(paused) {
					pauseSim();
				}
				System.out.println("Train " + testId + " moves forward.");
				ttracker.moveTrain(this, stationCurr);
				
			}
		}
		System.out.println("Train " + testId + " exits simulation");
	}

	public Track getCurrTrack() {
		return stationCurr;
	}
	
	@Override
	public String toString() {
		return "Train " + testId;
	}

	@Override
	public void pauseSim() {
		try {
			simulation.pauseThread();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
