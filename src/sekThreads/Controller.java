package sekThreads;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;

public class Controller {
	
	private static DataExchange DE;
	private static DetectHumanoid DHobj;
	private static SerachPath SPobj;
	
	public static void main(String[] args) {
		DE = new DataExchange();
		DHobj = new DetectHumanoid(DE);
		SPobj = new SerachPath(DE);
		
		//DHobj.start();
		SPobj.start();
		
		while(!Button.ESCAPE.isDown()){
			
		}
		LCD.drawString("Finished", 0, 7);
		LCD.refresh();
		System.exit(0);
	}
}
