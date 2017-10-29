package robot_2;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class Controller {
	
	private static DataExchange DE;
	//private static DetectHumanoid DHobj;
	private static SearchPath SPobj;
	
	public static void main(String[] args) {
		DE = new DataExchange();
		//DHobj = new DetectHumanoid(DE);
		SPobj = new SearchPath(DE);
		
		//DHobj.start();
		SPobj.start();
		
		while(!Button.ESCAPE.isDown()){
			
		}
		LCD.drawString("Finished", 0, 7);
		LCD.refresh();
		System.exit(0);
	}
}
