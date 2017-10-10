package sekThreads;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class DetectHumanoid extends Thread{
	
	private DataExchange DEobj;
	private static EV3GyroSensor gs;
    //private static EV3ColorSensor cs;
    //private static EV3ColorSensor cs1;
    private static EV3UltrasonicSensor us;

    private static EV3LargeRegulatedMotor mB,mB1;
    private static SampleProvider gyroSamples;
    private static SampleProvider ultrasonicSamples;
    
    static EV3MediumRegulatedMotor claw;
    
    static float[] sample= { 0.0f };
    static float[] sampleC= { 0.0f };
    static float[] sampleU= { 0.0f };
    static Wheel wheel1,wheel2;
    static WheeledChassis chassis;
    static MovePilot pilot;
    
    public DetectHumanoid(DataExchange DE){
    	DEobj = DE;
    	claw = new EV3MediumRegulatedMotor(MotorPort.C);
    	mB = DE.mB;
    	mB1 = DE.mB1;
    	gs = DE.gs;
    	us = new EV3UltrasonicSensor(SensorPort.S3);
    	gyroSamples = gs.getAngleMode();
    	wheel1 = WheeledChassis.modelWheel(mB1, 56.0).offset(-94.5);
		//offset es la distancia del centro a la rueda
		wheel2 = WheeledChassis.modelWheel(mB, 56.0).offset(94.5);
		chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
    }
    
    public static void giro(int angule) {
        
        int sign=1;
        if(angule<0){
            sign=-1;
        }
        gyroSamples.fetchSample(sample, 0);
        System.out.println(sample[0]);
        
        System.out.println("TestChassis");
        while(Math.abs(sample[0])>1.0){
            pilot.rotate(-sample[0]);
            gyroSamples.fetchSample(sample, 0);    
            System.out.println("c:" + sample[0]);
        }
        
        
        pilot.rotate(angule); // degree clockwise
        System.out.println("giro");
        gyroSamples.fetchSample(sample, 0);
        System.out.println(sample[0]);
        
        while(Math.abs(angule-sign*Math.abs(sample[0]))>1.0){
            pilot.rotate(angule-sign*Math.abs(sample[0]));
            gyroSamples.fetchSample(sample, 0);    
            System.out.println(sample[0]);
        }
    }
	//GARRA
	 
	 
	public static void take()
	{
	    LCD.drawString("Atrapando...", 0, 0);
	    claw.rotate(320);
	    LCD.clear();
	}
	 
	public static void drop()
	{
	    LCD.drawString("Dejando...", 0, 0);
	    claw.rotate(-320);
	    LCD.clear();
	}
	public void run(){
		
		pilot.setLinearSpeed(150);
		pilot.setAngularAcceleration(70);
		ultrasonicSamples = us.getDistanceMode();
		
		//pilot.forward();
		while(true){
			ultrasonicSamples.fetchSample(sampleU, 0);
			System.out.println(sampleU[0]);
			while(sampleU[0]>0.30){
				//pilot.forward();
				ultrasonicSamples.fetchSample(sampleU, 0);
				System.out.println(sampleU[0]);
			}
			
			DEobj.setHumanoid(true);
			System.out.println();
			pilot.stop();
			pilot.travel(50);
			gs.reset();
			giro(-90);
			pilot.setLinearAcceleration(250);
			pilot.travel(sampleU[0]*100+100);
			
			take();
			pilot.travel(-(sampleU[0]*100+100));
			gs.reset();
			giro(180);
			drop();
			gs.reset();
			giro(-90);
			pilot.setLinearSpeed(250);
			pilot.setLinearAcceleration(150);
			
			DEobj.setHumanoid(false);
		}
	}
}
