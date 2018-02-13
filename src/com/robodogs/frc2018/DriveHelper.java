package com.robodogs.frc2018;

import com.robodogs.frc2018.Constants;

/*
 * Methods to help implement mecanum drive functionality
 */
public class DriveHelper {

    public static double[] rotateVector(double x, double y, double angle) {
        double cosA = Math.cos(angle * (Math.PI / 180.0));
        double sinA = Math.sin(angle * (Math.PI / 180.0));
        return new double[] {
                x * cosA - y * sinA,
                x * sinA + y * cosA
        };
    }

    public static void normalize(double[] wheelSpeeds) {
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

    public static double applyDeadband(double value) {
        if (Math.abs(value) <= Constants.Drive.kDeadband)
            return 0.0;
        return value;
    }

}
