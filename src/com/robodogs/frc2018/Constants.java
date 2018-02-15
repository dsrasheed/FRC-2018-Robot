/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.robodogs.frc2018;

public class Constants {

    public static double inchesToMeters(double inches) {
        return inches * 2.54 * 0.01;
    }

    public static class Drive {

        public static final int kFrontLeftCANID  = 0;
        public static final int kFrontRightCANID = 0;
        public static final int kRearLeftCANID   = 0;
        public static final int kRearRightCANID  = 0;

        public static final double kDeadband = 0.08;

        // Motion Profiling
        public static final int kEncCodesPerRev = 1440;
        public static final double kWheelDiameter = inchesToMeters(4.0); // m
        public static final double kMaxVelocity = 1.0; // m/s
        public static final String kTrajectoriesDirName = "trajectories";
        public static final double kP = 0.0;
        public static final double kI = 0.0;
        public static final double kD = 0.0;
        public static final double kV = 1.0 / kMaxVelocity;
        public static final double kA = 0.0;
    }
}
