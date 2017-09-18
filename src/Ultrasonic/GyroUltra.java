package Ultrasonic;

import java.util.ArrayList;

import lejos.hardware.lcd.LCD;
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
import lejos.utility.Delay;

public class GyroUltra {
	
	private static EV3GyroSensor gs;
	private static EV3ColorSensor cs;
	private static EV3ColorSensor cs1;
	private static EV3UltrasonicSensor us;

	private static SampleProvider colorSamples;
	private static SampleProvider colorSamples1;
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
	String colorName = "";
	
	static ArrayList<int[]> map = new ArrayList<int[]>();
	//private static int map[][];
	
	
	public static void main(String[] args) {
		GyroUltra test = new GyroUltra();
	}
	
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
	
	public static void correctGiro(){
		
		
		colorSamples.fetchSample(sample, 0);
		System.out.println(sample[0]);
		colorSamples1.fetchSample(sampleC, 0);
		System.out.println(sampleC[0]);
		while((int)sample[0]== Color.WHITE && (int)sampleC[0]== Color.WHITE){
			colorSamples.fetchSample(sample, 0);
			System.out.println(sample[0]);
			colorSamples1.fetchSample(sampleC, 0);
			System.out.println(sampleC[0]);
		}
		
		pilot.stop();
		pilot.setLinearSpeed(50);
		/*pilot.backward();
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
		colorSamples.fetchSample(sample, 0);
		System.out.println(sample[0]);
		mB1.setSpeed(50);
		mB1.forward();
		while((int)sample[0]== Color.WHITE){
			colorSamples.fetchSample(sample, 0);
			System.out.println(sample[0]);
			mB1.forward();
		}
		mB1.stop();
		colorSamples1.fetchSample(sampleC, 0);
		System.out.println(sampleC[0]);
		mB.setSpeed(50);
		mB.forward();
		while((int)sampleC[0]== Color.WHITE){
			colorSamples1.fetchSample(sampleC, 0);
			System.out.println(sampleC[0]);
			mB.forward();
		}
		mB.stop();
		
		colorSamples.fetchSample(sample, 0);
		System.out.println(sample[0]);
		mB1.backward();
		while((int)sample[0]!= Color.WHITE){
			colorSamples.fetchSample(sample, 0);
			System.out.println(sample[0]);
			mB1.backward();
		}
		mB1.stop();
		colorSamples1.fetchSample(sampleC, 0);
		System.out.println(sampleC[0]);
		mB.backward();
		while((int)sampleC[0]!= Color.WHITE){
			colorSamples1.fetchSample(sampleC, 0);
			System.out.println(sampleC[0]);
			mB.backward();
		}
		mB.stop();
		gs.reset();*/
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

	public static void correctGiro2(){
		
		System.out.println("Correct");
		boolean reset1 = false;
		boolean reset2 = false;
		colorSamples.fetchSample(sample, 0);
		System.out.println(sample[0]);
		colorSamples1.fetchSample(sampleC, 0);
		System.out.println(sampleC[0]);
		while((int)sample[0]== Color.WHITE || (int)sampleC[0]== Color.WHITE){
			colorSamples.fetchSample(sample, 0);
			System.out.println(sample[0]);
			colorSamples1.fetchSample(sampleC, 0);
			System.out.println(sampleC[0]);
			if((int)sample[0]!= Color.WHITE){
				if(!reset1) {
					mB1.resetTachoCount();
					reset1=true;
				}
			}
			if((int)sampleC[0]!= Color.WHITE){
				if(!reset2) {
					mB.resetTachoCount();
					reset2=true;
				}
			}
		}
		pilot.stop();
		mB1.setSpeed(50);
		mB.setSpeed(50);
		int a = mB.getTachoCount();
		int b = mB1.getTachoCount();
		double aux = Math.atan(Math.PI*56*a/(360*182))*180*2*182/360;
		int theta = (int) aux;
		System.out.println("Angulo: "+theta);
		mB1.rotate(-theta);
		/*System.out.println("tacho: "+mB1.getTachoCount());
		mB1.rotate(-mB1.getTachoCount(),true);
		System.out.println("tacho: "+mB.getTachoCount());
		mB.rotate(-mB.getTachoCount());*/
		
		
	}
	
	public static void correctGiro3(){
		
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
		
		pilot.setLinearSpeed(150);
		float aux1,aux2;
		ultrasonicSamples.fetchSample(sampleU, 0);
		Delay.msDelay(250);
		System.out.println(sampleU[0]);
		aux1=sampleU[0];
		pilot.forward();
		while(aux1>0.5 || aux1<0.01){
			//pilot.forward();
			ultrasonicSamples.fetchSample(sampleU, 0);
			if(sampleU[0]<0.5){
				mB.resetTachoCount();
				mB1.resetTachoCount();
			}
			Delay.msDelay(250);
			System.out.println(sampleU[0]);
			aux1=sampleU[0];
		}
		
		pilot.stop();
		pilot.setLinearSpeed(30);
		mB.setSpeed(30);
		mB1.setSpeed(30);
		/*ultrasonicSamples.fetchSample(sampleU, 0);
		Delay.msDelay(250);
		System.out.println(sampleU[0]);*/
		//pilot.backward();
		System.out.println("TACHO:"+mB.getTachoCount());
		
		mB.rotate(-mB.getTachoCount()+50,true);
		mB1.rotate(-mB1.getTachoCount()+50);
		
		giro(-90);
		pilot.travel(sampleU[0]*100+100);
	}
	
	public GyroUltra(){
		
		mB = new EV3LargeRegulatedMotor(MotorPort.B);
		mB1 = new EV3LargeRegulatedMotor(MotorPort.A);
		claw = new EV3MediumRegulatedMotor(MotorPort.C);
		gs = new EV3GyroSensor(SensorPort.S1);
		cs = new EV3ColorSensor(SensorPort.S2);
		us = new EV3UltrasonicSensor(SensorPort.S3);
		cs1 = new EV3ColorSensor(SensorPort.S4);
		gyroSamples = gs.getAngleMode();
		colorSamples = cs.getColorIDMode();
		colorSamples1 = cs1.getColorIDMode();
		
		ultrasonicSamples = us.getDistanceMode();
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
	   //control();
	   //humanoid();
	   
	   
	   for(int i=0;i<8; ++i){
		   pilot.forward();
		   correctGiro();
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
		
		//pilot.travel(300);
	   
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
