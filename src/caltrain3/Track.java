package caltrain3;

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
		this.currTrain = t;
	}
	
	public Train getCurrTrain() {
		return currTrain;
	}
	
	public int getId() {
		return index;
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
	 * Checks whether there is a train on the tracks.
	 * @return whether this track is occupied or not
	 */
	public synchronized boolean isVacant() {
		return currTrain == null;
	}
	
	/**
	 * Train parameter would wait until this track becomes vacant
	 * @param t train waiting on this track space
	 */
	public synchronized void waitOn(Train t) {
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is called by Train objects when it needs to vacate the current track.
	 */
	public synchronized void vacate() {
		currTrain = null;
		this.notifyAll();
	}

	@Override
	public String toString() {
		return "Track [index=" + index + "]";
	}
}