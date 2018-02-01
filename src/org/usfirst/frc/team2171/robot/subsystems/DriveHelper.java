package org.usfirst.frc.team2171.robot.subsystems;

import org.usfirst.frc.team2171.robot.Robot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

// Turn this into an instantiable class
public class DriveHelper {
	
	private static double rotationOut = 0.0;
	public static PIDController headingController;
	
	static {
		headingController = new PIDController(0.000, 0.000, 0.0, Robot.gyro, (double out) -> {
			rotationOut = out;
		});
		headingController.setContinuous(true);
		headingController.setAbsoluteTolerance(0.1);
		headingController.setInputRange(-180.0, 180.0);
		headingController.setOutputRange(-1.0, 1.0);
		//headingController.enable();
		
		LiveWindow.add(headingController);
	}

	
	public static enum MotorType {
		kFrontLeft(0),
		kFrontRight(1),
		kRearLeft(2),
		kRearRight(3);
		
		private int value;
		
		private MotorType(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
	
	private static double[] rotateVector(double x, double y, double angle) {
		double cosA = Math.cos(angle * (Math.PI / 180.0));
		double sinA = Math.sin(angle * (Math.PI / 180.0));
		return new double[] {
			x * cosA - y * sinA,
			x * sinA + y * cosA
		};
	}
	
	private static void normalize(double[] wheelSpeeds) {
		double maxMagnitude = Math.abs(wheelSpeeds[0]);
		for (int i = 1; i < wheelSpeeds.length; i++) {
			double temp = Math.abs(wheelSpeeds[i]);
			if (maxMagnitude < temp) {
				maxMagnitude = temp;
			}
		}
		if (maxMagnitude > 1.0) {
			for (int i = 0; i < wheelSpeeds.length; i++) {
				wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
			}
		}
	}
	
	public static Drive.DriveSpeed mecanumDrive(double xIn, double yIn, double rotation, double gyroAngle) {
		/*if (Math.abs(rotation) >= 0.15)
			headingController.setSetpoint(gyroAngle);
		else
			rotation = rotationOut;*/
		
		double[] rotated = rotateVector(xIn, yIn, gyroAngle);
		xIn = rotated[0];
		yIn = rotated[1];
		
		double[] wheelSpeeds = new double[4];
		wheelSpeeds[MotorType.kFrontLeft.value] = yIn - xIn + rotation;
		wheelSpeeds[MotorType.kFrontRight.value] = yIn + xIn - rotation;
		wheelSpeeds[MotorType.kRearLeft.value] = yIn + xIn + rotation;
		wheelSpeeds[MotorType.kRearRight.value] = yIn - xIn - rotation;
		normalize(wheelSpeeds);
		
		return new Drive.DriveSpeed(wheelSpeeds);
	}

}
