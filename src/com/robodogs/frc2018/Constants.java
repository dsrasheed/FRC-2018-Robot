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

        public static final String kTrajectoriesDirName = "/trajectories";
        
        public static final double kMaxVelocity = 540.0;
        
        public static final double kFrontLeftP = 0.5328; // 511.5 out / 960 error (50% out per 1 rotation of error)
        public static final double kFrontLeftI = 0.0;
        public static final double kFrontLeftD = 0.0;
        public static final double kFrontLeftF = 1.81222;
        
        public static final double kFrontRightP = 0.5328; // 511.5 out / 960 error (50% out per 1 rotation of error)
        public static final double kFrontRightI = 0.0;
        public static final double kFrontRightD = 0.0;
        public static final double kFrontRightF = 1.86169;
        
        public static final double kRearLeftP = 0.5328; // 511.5 out / 960 error (50% out per 1 rotation of error)
        public static final double kRearLeftI = 0.0;
        public static final double kRearLeftD = 0.0;
        public static final double kRearLeftF = 1.79316;
        
        public static final double kRearRightP = 0.5328; // 511.5 out / 960 error (50% out per 1 rotation of error)
        public static final double kRearRightI = 0.0;
        public static final double kRearRightD = 0.0;
        public static final double kRearRightF = 1.8;
    }
    
    public static class Arm {
        
        public static final int kMasterCANID = 5;
        public static final int kSlaveCANID  = 20;
        public static final int kDescendStop1Port = 4;
        public static final int kAscendStop1Port = 5;
        public static final int kAscendingStop2Port = 7;

    }
    
    public static class Claw {
        
        public static final int kMasterCANID = 11;
        public static final int kSlaveCANID = 8;
        public static final int kLimitPort = 5;
        
        public static final int kLeftForwardPort = 2;
        public static final int kLeftReversePort = 0;
        public static final int kRightForwardPort = 3;
        public static final int kRightReversePort = 1;
        
    }
    
    public static class Winder {
        public static final int kTalonPort = 8;
        public static final int kForwardPort = 5; // B
        public static final int kReversePort = 4; // A
    }
}
