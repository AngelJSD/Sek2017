package Ultrasonic;

import lejos.ev3.*;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

public class UltrasonicSensor {

   private EV3UltrasonicSensor us;
   private SampleProvider ultrasonicSamples;
   float[] sample= { 0.0f };

   public static void main(String[] args) {
	   UltrasonicSensor test = new UltrasonicSensor();
   }
   
   public UltrasonicSensor(){
	   
	   us = new EV3UltrasonicSensor(SensorPort.S3);
	   ultrasonicSamples = us.getDistanceMode();
	   ultrasonicSamples.fetchSample(sample, 0);
	   System.out.println(sample[0]);
	   while(true){
		   ultrasonicSamples.fetchSample(sample, 0);
		   System.out.println(sample[0]);
	   }
   }
}