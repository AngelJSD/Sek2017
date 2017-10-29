package robot_2;

import java.util.ArrayList;
import java.util.Stack;

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
import lejos.hardware.Sound;

public class SearchPath extends Thread{
	
	private DataExchange DEobj;
	private static EV3GyroSensor gs;
	private static EV3ColorSensor cs;
	private static EV3ColorSensor cs1;
	private static Sound sound;

	private static SampleProvider colorSamples;
	private static SampleProvider colorSamples1;
	private static EV3LargeRegulatedMotor mB,mB1;
	private static SampleProvider gyroSamples;
	private static SampleProvider ultrasonicSamples;
	
	static float[] sample= { 0.0f };
	static float[] sampleU= { 0.0f };
	static Wheel wheel1,wheel2;
	static WheeledChassis chassis;
	static MovePilot pilot;
	String colorName = "";
	
	static ArrayList<int[]> map = new ArrayList<int[]>();
	static Stack<Integer> pila = new Stack<Integer>();
	
	public SearchPath(DataExchange DE){
		
		DEobj = DE;
		mB = DE.mB;
		mB1 = DE.mB1;
		gs = DE.gs;
		cs = DE.cs;
		cs1 = DE.cs1;
		gyroSamples = DE.gyroSamples;
		colorSamples = DE.colorSamples;
		colorSamples1 = DE.colorSamples1;
		
		
		wheel1 = WheeledChassis.modelWheel(mB1, 56.0).offset(-62);
	   //offset es la distancia del centro a la rueda
	   wheel2 = WheeledChassis.modelWheel(mB, 56.0).offset(62);
	   chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);
	   pilot = new MovePilot(chassis);
	   //pilot.setLinearAcceleration(150);
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
		
		boolean visit = false;
		
		colorSamples.fetchSample(sample, 0);
		System.out.println(sample[0]);
		while((int)sample[0]!= Color.GREEN){
			
			if((int)sample[0]== Color.RED){
				pilot.stop();
				pilot.travel(-15);
				colorSamples.fetchSample(sample, 0);
				System.out.println(sample[0]);
				if((int)sample[0]== Color.BLUE){
					Sound.twoBeeps();
					pilot.travel(-60);
					gs.reset();
					giro(180);
					
				}
			}
			
			if((int)sample[0]== Color.WHITE){
				
				if(DEobj.getHumanoid()){
					while (DEobj.getHumanoid());
					pilot.backward();
				}
				
			}
			else if((int)sample[0]!= Color.WHITE && (int) sample[0]!=Color.BLACK){
				
				//OJO con que justo detecte humanito aqui
				pilot.stop();
				//pilot.setLinearSpeed(50);
				if(visit==false){
					pila.push(1);
					pilot.travel(-350);
					pilot.backward();
					//visit=true;
				}
				else{
					int a = pila.pop();
					pilot.travel(-120);
					if(a==1){
						giro(90);
						
					}
					else if(a==3){
						giro(-90);
					}
					gs.reset();
					pilot.travel(-120);
					pilot.backward();
					visit=false;
					a+=1;
					if(a!=4){
						pila.push(a);
					}
				}
			}
			else if((int) sample[0]==Color.BLACK){
				visit=true;
				//media vuelta
				pilot.stop();
				giro(180);
				pilot.travel(-50);
				gs.reset();
				pilot.backward();
			}
			
			colorSamples.fetchSample(sample, 0);
			System.out.println(sample[0]);
		}
	}

	public void run(){
		
		/*for(int i=0;i<8; ++i){
		   pilot.forward();
		   DetectIntersection1();
		   pilot.setLinearSpeed(250);

		   System.out.println("Giro 180");
		   gs.reset();
		   giro(180);
		   
		   correctGiro1();
		   //pilot.setLinearSpeed(250);
		   //pilot.travel(30);
	   }*/
		pilot.backward();
		DetectIntersection();
	}
}
