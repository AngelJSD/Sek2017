package Ultrasonic;

import java.util.ArrayList;
import java.util.Stack;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
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

public class Gyro {
	
	private static EV3GyroSensor gs;
	private EV3ColorSensor cs;
	private static EV3UltrasonicSensor us;

	private static SampleProvider colorSamples;
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
	static Stack<Integer> pila = new Stack<Integer>();
	//private static int map[][];
	
	
	public static void main(String[] args) {
		Gyro test = new Gyro();
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
	
	
	public static void control(){
		
		int it = 1;
		boolean visit = false;
		
		//bucle
		while(true){
			pilot.travel(300);
			colorSamples.fetchSample(sample, 0);
			if((int) sample[0]==Color.GREEN){
				return;
			}
			if((int) sample[0]!=Color.WHITE && (int) sample[0]!=Color.BLACK && visit==false){
				
				
				pila.push(1);
				
				if(map.size()>0){
					map.get(map.size()-1)[it]=map.size();
				}
				int[] aux = new int[4];
				aux[0]=map.size()-1;
				map.add(aux);
				it=1;
			}
			else{
				if((int) sample[0]!=Color.WHITE && (int) sample[0]!=Color.BLACK){
					visit=false;
					if(it==2){
						giro(90);
						gs.reset();
					}
				}
				if((int) sample[0]==Color.BLACK){
					if(map.size()>0){
						map.get(map.size()-1)[it]=-1;
					}
					++it;
					visit=true;
					//media vuelta
					giro(180);
					gs.reset();
				}
			}
		}
	}
	
	public static void humanoid(){
		ultrasonicSamples = us.getDistanceMode();
		ultrasonicSamples.fetchSample(sampleU, 0);
		System.out.println(sampleU[0]);
		pilot.forward();
		while(sampleU[0]>0.1){
			//pilot.forward();
			ultrasonicSamples.fetchSample(sampleU, 0);
			System.out.println(sampleU[0]);
		}
		pilot.stop();
		pilot.travel(-300);
		giro(-90);
	}
	
	public Gyro(){
		
		mB = new EV3LargeRegulatedMotor(MotorPort.B);
		mB1 = new EV3LargeRegulatedMotor(MotorPort.A);
		gs = new EV3GyroSensor(SensorPort.S1);
		cs = new EV3ColorSensor(SensorPort.S2);
		us = new EV3UltrasonicSensor(SensorPort.S3);
		gyroSamples = gs.getAngleMode();
		colorSamples = cs.getColorIDMode();
		wheel1 = WheeledChassis.modelWheel(mB1, 56.0).offset(-94.5);
	   //offset es la distancia del centro a la rueda
	   wheel2 = WheeledChassis.modelWheel(mB, 56.0).offset(94.5);
	   chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
	   pilot = new MovePilot(chassis);
	   pilot.setLinearAcceleration(150);
	   pilot.setAngularAcceleration(70);
	   //pilot.setLinearSpeed(250);
	   //pilot.setAngularSpeed(50);
	   //pilot.travel(-1500);
	   gs.reset();
	   control();
	   //humanoid();
	   /*
	   for(int i=0; i<8; ++i){
		   gs.reset();
		   pilot.travel(200);
		   giro(-90);
	   }*/
	   /*
	   colorSamples.fetchSample(sampleC, 0);
	   
	   while(true){
		   colorSamples.fetchSample(sample, 0);
		   switch((int) sample[0]){
				case Color.NONE: colorName = "NONE"; break;
				case Color.BLACK: colorName = "BLACK"; break;
				case Color.BLUE: colorName = "BLUE"; break;
				case Color.GREEN: colorName = "GREEN"; break;
				case Color.YELLOW: colorName = "YELLOW"; break;
				case Color.RED: colorName = "RED"; break;
				case Color.WHITE: colorName = "WHITE"; break;
				case Color.BROWN: colorName = "BROWN"; break;
		   }
		   System.out.println(colorName);
	   }*/
	   
	}
}
