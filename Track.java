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
}