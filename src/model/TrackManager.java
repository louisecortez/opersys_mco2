package model;

import java.util.*;

public class TrackManager {
	
	private List<Track> tracks; // can change 
	
	/**
	 * Constructor: alternate gap and station instantiation
	 * 
	 */
	public TrackManager() {
		tracks = new ArrayList<Track>();
		
		for(int i = 0; i < 16; i++){ 
			Track track;
			
			if(i % 2 == 0) {
				track = new Station(i);
			} else {
				track = new Track(i);
			}
			
//			if(track instanceof Station) {
//				System.out.println("Station " + i + " created");
//			} else {
//				System.out.println("Track " + i + " created");
//			}
			
			tracks.add(track);
		}
	}
	
	/**
	 * This function returns the next track from Track t in the list.
	 * @param t the train's current track.
	 * @return track succeeding the current track.
	 */
	public Track getNextTrack(Track t) {
		int nextIndex = tracks.indexOf(t);

		return tracks.get((nextIndex + 1) % tracks.size());
	}
	
	public Track getTrack(int index) {
		return tracks.get(index);
	}
	
	/**
	 * This function determines whether the next track from Track t in 
	 * the list is vacant.
	 * @param t the train's current track.
	 * @return whether the track following Track t is vacant
	 */
	public synchronized boolean isNextTrackVacant(Track t) {
		return getNextTrack(t).isVacant();
	}
	
	public synchronized boolean moveTrain(Train train, Track track) {
		System.out.println("Move train");
		if(isNextTrackVacant(track)) {
			//System.out.println("Track current: " + track.toString());
			train.setStationCurr(getNextTrack(track));
			//System.out.println("Track next: " + train.getCurrTrack().toString());
			train.getCurrTrack().setCurrTrain(train);
			//System.out.println("Train next: " + train.getCurrTrack().getCurrTrain().toString());
			//System.out.println("Train verify: " + train.getCurrTrack().getCurrTrain().getCurrTrack().toString());
			try {
				Thread.sleep(500);				// in case a train is waiting on the current station
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			track.vacate();
			return true;
		} 
		System.out.println("Train " + train.getId() + " waits.");
		return false;
	
	}
	
	/**
	 * This function is called by trains when the succeeding station is
	 * occupied by another train. Train thread would wait on the succeeding
	 * station.
	 * 
	 * @param waitTrain the train waiting on the next station
	 * @param currTrack the current station the train is on
	 */
	public void waitOnNextTrack(Train waitTrain, Track currTrack) {
		getNextTrack(currTrack).waitOn(waitTrain);
	}
}