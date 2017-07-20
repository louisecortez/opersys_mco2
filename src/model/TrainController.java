package model;

import java.util.*;

public class TrainController {
	
	private List<Track> tracks; // can change 
	
	/**
	 * Constructor: alternate gap and station instantiation
	 * 
	 */
	public TrainController() {
		tracks = new LinkedList<Track>();
		
		for(int i = 0; i < 16; i++){ 
			Track track;
			
			if(i % 2 == 0) {
				track = new Station(i);
			} else {
				track = new Track(i);
			}
			
			if(track instanceof Station) {
				System.out.println("Station " + i + " created");
			} else {
				System.out.println("Track " + i + " created");
			}
			
			tracks.add(track);
		}
	}
}