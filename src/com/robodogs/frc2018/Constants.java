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
        public static final int kTimeout = 0;

        /* MOTION PROFILING */
        public static final int kEncCodesPerRev = 960;
        public static final double kLoopPeriod = 0.005;
        public static final int kMinPointsInTalon = 100;
        public static final int kNumLoopsTimeout = 10;

        public static final String kTrajectoriesDirName = "/trajectories/";
        
        public static final double kMaxVelocity = 540.0;
        
        public static final double kFrontLeftP = 0.0;
        public static final double kFrontLeftI = 0.0;
        public static final double kFrontLeftD = 0.0;
        public static final double kFrontLeftF = 1023 / 540.0;
        
        public static final double kFrontRightP = 0.0;
        public static final double kFrontRightI = 0.0;
        public static final double kFrontRightD = 0.0;
        public static final double kFrontRightF = 0.0;
        
        public static final double kRearLeftP = 0.0;
        public static final double kRearLeftI = 0.0;
        public static final double kRearLeftD = 0.0;
        public static final double kRearLeftF = 0.0;
        
        public static final double kRearRightP = 0.0;
        public static final double kRearRightI = 0.0;
        public static final double kRearRightD = 0.0;
        public static final double kRearRightF = 0.0;
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
        
        // public static final int kClqwForwardPort =
        public static final int kForwardPort1 = 0;
        public static final int kForwardPort2 = 2;
        public static final int kReversePort1 = 1;
        public static final int kReversePort2 = 3;
        
    }
}
