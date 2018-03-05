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

        public static final int kFrontLeftCANID  = 2;
        public static final int kFrontRightCANID = 7;
        public static final int kRearLeftCANID   = 18;
        public static final int kRearRightCANID  = 13;

        public static final double kDeadband = 0.05;

        // Motion Profiling
        public static final int kEncCodesPerRev = 1440;
        public static final double kWheelDiameter = inchesToMeters(4.0); // meters
        public static final double kMaxVelocity = 540.0; // ticks/100ms
        public static final String kTrajectoriesDirName = "/trajectories";
        public static final double kP = 0.0;
        public static final double kI = 0.0;
        public static final double kD = 0.0;
        public static final double kV = 1023 / kMaxVelocity;
        public static final double kA = 0.0;
    }
    
    public static class Arm {
        
        public static final int kMasterCANID = 5;
        public static final int kSlaveCANID  = 0;
        public static final int kLimitPort = 4;
        
        
    }
    
    public static class Claw {
        
        public static final int kMasterCANID = 11;
        public static final int kSlaveCANID = 8;
        public static final int kLimitPort = 5;
        
        public static final int kLeftPistonForwardPort  = 0;
        public static final int kRightPistonForwardPort = 0;
        public static final int kLeftPistonReversePort  = 0;
        public static final int kRightPistonReversePort = 0;
        
    }
}
