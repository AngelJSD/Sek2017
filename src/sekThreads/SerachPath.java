package sekThreads;

import java.util.ArrayList;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class SerachPath extends Thread{
	
	private DataExchange DEobj;
	private static EV3GyroSensor gs;
	private static EV3ColorSensor cs;
	private static EV3ColorSensor cs1;

	private static SampleProvider colorSamples;
	private static SampleProvider colorSamples1;
	private static EV3LargeRegulatedMotor mB,mB1;
	private static SampleProvider gyroSamples;
	private static SampleProvider ultrasonicSamples;
	
	static float[] sample= { 0.0f };
	static float[] sampleC= { 0.0f };
	static float[] sampleU= { 0.0f };
	static Wheel wheel1,wheel2;
	static WheeledChassis chassis;
	static MovePilot pilot;
	String colorName = "";
	
	static ArrayList<int[]> map = new ArrayList<int[]>();
	
	public SerachPath(DataExchange DE){
		
		DEobj = DE;
		mB = new EV3LargeRegulatedMotor(MotorPort.B);
		mB1 = new EV3LargeRegulatedMotor(MotorPort.A);
		gs = new EV3GyroSensor(SensorPort.S1);
		cs = new EV3ColorSensor(SensorPort.S2);
		cs1 = new EV3ColorSensor(SensorPort.S4);
		gyroSamples = gs.getAngleMode();
		colorSamples = cs.getColorIDMode();
		colorSamples1 = cs1.getColorIDMode();
		
		wheel1 = WheeledChassis.modelWheel(mB1, 56.0).offset(-94.5);
	   //offset es la distancia del centro a la rueda
	   wheel2 = WheeledChassis.modelWheel(mB, 56.0).offset(94.5);
	   chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
	   pilot = new MovePilot(chassis);
	   pilot.setLinearAcceleration(150);
	   pilot.setAngularAcceleration(70);
	   pilot.setLinearSpeed(250);
	   //pilot.setAngularSpeed(50);
	   //pilot.travel(-1500);
	   gs.reset();
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
	
	public void DetectIntersection(){
		
		
		colorSamples.fetchSample(sample, 0);
		System.out.println(sample[0]);
		colorSamples1.fetchSample(sampleC, 0);
		System.out.println(sampleC[0]);
		while((int)sample[0]== Color.WHITE && (int)sampleC[0]== Color.WHITE){
			while (DEobj.getHumanoid());
			
			colorSamples.fetchSample(sample, 0);
			System.out.println(sample[0]);
			colorSamples1.fetchSample(sampleC, 0);
			System.out.println(sampleC[0]);
		}
		//OJO con que justo detecte humanito aqui
		pilot.stop();
		pilot.setLinearSpeed(50);
	}
	
	public static void correctGiro1(){
		
		pilot.setLinearSpeed(15);
		pilot.forward();
		colorSamples.fetchSample(sample, 0);
		System.out.println(sample[0]);
		colorSamples1.fetchSample(sampleC, 0);
		System.out.println(sampleC[0]);
		while((int)sample[0]!= Color.WHITE || (int)sampleC[0]!= Color.WHITE){
			colorSamples.fetchSample(sample, 0);
			System.out.println(sample[0]);
			colorSamples1.fetchSample(sampleC, 0);
			System.out.println(sampleC[0]);
			if((int)sample[0]== Color.WHITE){
				mB1.stop(true);
			}
			if((int)sampleC[0]== Color.WHITE){
				mB.stop(true);
			}
		}
		pilot.travel(30);
		pilot.backward();
		
		colorSamples.fetchSample(sample, 0);
		System.out.println(sample[0]);
		colorSamples1.fetchSample(sampleC, 0);
		System.out.println(sampleC[0]);
		while((int)sample[0]== Color.WHITE || (int)sampleC[0]== Color.WHITE){
			colorSamples.fetchSample(sample, 0);
			System.out.println(sample[0]);
			colorSamples1.fetchSample(sampleC, 0);
			System.out.println(sampleC[0]);
			if((int)sample[0]!= Color.WHITE ){
				mB1.stop();
			}
			if((int)sampleC[0]!= Color.WHITE ){
				mB.stop();
			}
		}
	    
		pilot.travel(-30);
		pilot.setLinearSpeed(15);
		pilot.forward();
		colorSamples.fetchSample(sample, 0);
		System.out.println(sample[0]);
		colorSamples1.fetchSample(sampleC, 0);
		System.out.println(sampleC[0]);
		while((int)sample[0]!= Color.WHITE || (int)sampleC[0]!= Color.WHITE){
			colorSamples.fetchSample(sample, 0);
			System.out.println(sample[0]);
			colorSamples1.fetchSample(sampleC, 0);
			System.out.println(sampleC[0]);
			if((int)sample[0]== Color.WHITE){
				mB1.stop(true);
			}
			if((int)sampleC[0]== Color.WHITE){
				mB.stop(true);
			}
		}
		gs.reset();
	}
	
	public void run(){
		
		for(int i=0;i<8; ++i){
		   pilot.forward();
		   DetectIntersection();
		   pilot.setLinearSpeed(250);
		   
		   //pilot.travel(200);
		   giro(180);
		   //pilot.travel(30);
		   //pilot.setLinearSpeed(15);
		   
		   //pilot.backward();
		   correctGiro1();
		   pilot.setLinearSpeed(250);
		   pilot.travel(30);
		   
	   }
	}
}
