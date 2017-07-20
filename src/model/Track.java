package model;

import java.util.*;

public class Track {
	protected int index;								// could be changed to station name
	protected Train currTrain;
	
	/**
	 * 
	 * @param index id of the track.
	 */
	public Track(int index) {
		this.index = index;
	}
	
	public void setCurrTrain(Train t) {
		currTrain = t;
	}
	
	public boolean notOccupied() {
		if(currTrain == null) {
			System.out.println("This track is free");
			return true;
		} else {
			System.out.println("This track is occupied");
			return false;
		}
	}
	
	/**
	 * This method notifies the passengers to wait until the train moves to a station.
	 */
	// track will notify all trains 
	public void notifyPassengers() {
		
	}
}