package values;

import java.io.IOException;

import javafx.scene.image.Image;

public class ImageSources {
	
	public static Image getImgTrack() {		
		return new Image("file:res/sprites/track.png", 180, 61, true, false);
	}
	
	public static Image getImgTrackL() {		
		return new Image("file:res/sprites/trackL.png", 61, 180, true, false);
	}
	
	public static Image getImgTrackR() {		
		return new Image("file:res/sprites/trackR.png", 61, 180, true, false);
	}
	
	public static Image getImgTrain() {		
		return new Image("file:res/sprites/train.png", 180, 61, true, false);
	}
	
	public static Image getImgTrainL() {		
		return new Image("file:res/sprites/trainL.png", 61, 180, true, false);
	}
	
	public static Image getImgTrainR() {		
		return new Image("file:res/sprites/trainR.png", 61, 180, true, false);
	}
	
	public static Image getImgTrainD() {		
		return new Image("file:res/sprites/trainD.png", 180, 61, true, false);
	}
	
	public static Image getImgStation() {		
		return new Image("file:res/sprites/station.png", 170, 0, true, false);
	}
	
	public static Image getImgPassenger(int i) {
		System.out.println("Pass: " + i);
		if(i == 0) {
			return new Image("file:res/passengers/robo-1.png", 0, 60, true, false);
		} else if(i == 1) {
			return new Image("file:res/passengers/robo-2.png", 0, 60, true, false);
		} else if(i == 2) {
			return new Image("file:res/passengers/robo-3.png", 0, 60, true, false);
		} else {
			return new Image("file:res/passengers/robo-4.png", 0, 60, true, false);
		}
	}
	
//	public static Image getImgBGMain() {
//		return new Image("file:res/bg/bg-test-2.jpg", 900, 750, false, false);
//		// return new Image("file:res/bg/black-marble.jpg");
//	}
}
