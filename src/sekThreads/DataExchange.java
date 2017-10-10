package sekThreads;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;

public class DataExchange {
	
	private boolean humanoid = false;
	
	public SampleProvider colorSamples;
	public SampleProvider colorSamples1;
	public EV3LargeRegulatedMotor mB,mB1;
	public SampleProvider gyroSamples;
	
	public EV3GyroSensor gs;
	public EV3ColorSensor cs;
	public EV3ColorSensor cs1;
	
	public DataExchange(){
		mB = new EV3LargeRegulatedMotor(MotorPort.B);
		mB1 = new EV3LargeRegulatedMotor(MotorPort.A);
		gs = new EV3GyroSensor(SensorPort.S1);
		cs = new EV3ColorSensor(SensorPort.S2);
		cs1 = new EV3ColorSensor(SensorPort.S4);
		gyroSamples = gs.getAngleMode();
		colorSamples = cs.getColorIDMode();
		colorSamples1 = cs1.getColorIDMode();
	}
	
	public void setHumanoid(boolean status){
		humanoid=status;
	}
	public boolean getHumanoid(){
		return humanoid;
	}
}
