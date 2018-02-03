package org.usfirst.frc.team2171.robot.subsystems;

import org.usfirst.frc.team2171.robot.Constants;

/*
 * Methods to help implement mecanum drive functionality
 */
public class DriveHelper {

	public static Drive.DriveSignal mecanumDrive(double x, double y, double rot, double gyroAngle) {
		x = applyDeadband(x);
		y = applyDeadband(y);
		rot = applyDeadband(rot);

		double[] rotated = rotateVector(x, y, gyroAngle);
		x = rotated[0];
		y = rotated[1];

		double[] wheelSpeeds = new double[4];
		wheelSpeeds[Drive.MotorType.kFrontLeft.value] = y - x + rot;
		wheelSpeeds[Drive.MotorType.kFrontRight.value] = y + x - rot;
		wheelSpeeds[Drive.MotorType.kRearLeft.value] = y + x + rot;
		wheelSpeeds[Drive.MotorType.kRearRight.value] = y - x - rot;
		normalize(wheelSpeeds);

		return new Drive.DriveSignal(wheelSpeeds);
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

	private static double applyDeadband(double value) {
		if (Math.abs(value) <= Constants.Drive.kDeadband)
			return 0.0;
		return value;
	}

}
