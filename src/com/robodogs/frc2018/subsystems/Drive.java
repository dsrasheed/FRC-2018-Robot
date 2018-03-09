package com.robodogs.frc2018.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Notifier;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motion.MotionProfileStatus;

import com.robodogs.lib.util.PIDTuner;
import com.robodogs.lib.util.PIDTunable;
import com.robodogs.frc2018.Constants;
import com.robodogs.frc2018.Robot;
import com.robodogs.frc2018.DriveHelper;

// TODO: Implement wrapper around all the motors,
// or store them in an array, or implement functions
// to apply operations to all the motors, just reduce all 
// this stupid repeat code
// DO it after competiton so you don't break anything
// TODO: Wherever ControlMode.PercentOutput is present, 
// it can be replaced with ControlMode.Velocity
public class Drive extends Subsystem {

    private TalonSRX frontLeft;
    private TalonSRX frontRight;
    private TalonSRX rearLeft;
    private TalonSRX rearRight;
    
    private ControlMode controlMode;
    private PIDController headingCtrl;
    double rotation = 0.0;
    
    private PIDTuner speedTuner = new PIDTuner(new PIDTunable() {
        double setpoint;
        
        @Override
        public void onPIDChange(double p, double i, double d) {
            frontLeft.config_kP(0, p, 0);
            frontRight.config_kP(0, p, 0);
            rearLeft.config_kP(0, p, 0);
            rearRight.config_kP(0, p, 0);
            
            frontLeft.config_kI(0, i, 0);
            frontRight.config_kI(0, i, 0);
            rearLeft.config_kI(0, i, 0);
            rearRight.config_kI(0, i, 0);
            
            frontLeft.config_kD(0, d, 0);
            frontRight.config_kD(0, d, 0);
            rearLeft.config_kD(0, d, 0);
            rearRight.config_kD(0, d, 0);
        }
        @Override
        public double getError() {
            System.out.println("Getting Error");
            return frontLeft.getClosedLoopError(0);
        }
        @Override
        public void setSetpoint(double setpoint) {
            this.setpoint = setpoint;
            System.out.println("SETPOINT: " + setpoint);
        }
        @Override
        public void start() {
            System.out.println("STARTING TO TUNE");
            controlMode = ControlMode.Velocity;
            //setSpeed(new DriveSignal(setpoint));
        }
        @Override
        public void stop() {
            System.out.println("STOPPING TUNING");
            controlMode = ControlMode.PercentOutput;
            // setSpeed(DriveSignal.STOP);
        }
    }, "drivebase_speed", Constants.Drive.kFrontLeftP, Constants.Drive.kFrontLeftI, Constants.Drive.kFrontLeftD);

    public Drive() {
        frontLeft = new TalonSRX(Constants.Drive.kFrontLeftCANID);
        frontRight = new TalonSRX(Constants.Drive.kFrontRightCANID);
        rearLeft = new TalonSRX(Constants.Drive.kRearLeftCANID);
        rearRight = new TalonSRX(Constants.Drive.kRearRightCANID);
        
        frontLeft.selectProfileSlot(0, 0);
        frontRight.selectProfileSlot(0, 0);
        rearLeft.selectProfileSlot(0, 0);
        rearRight.selectProfileSlot(0, 0);

        frontLeft.setNeutralMode(NeutralMode.Brake);
        frontRight.setNeutralMode(NeutralMode.Brake);
        rearLeft.setNeutralMode(NeutralMode.Brake);
        rearRight.setNeutralMode(NeutralMode.Brake);
        
        frontRight.setInverted(true);
        rearRight.setInverted(true);

        frontLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        frontRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        rearLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        rearRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        
        frontLeft.config_kP(0, Constants.Drive.kFrontLeftP, Constants.Drive.kTimeout);
        frontLeft.config_kI(0, Constants.Drive.kFrontLeftI, Constants.Drive.kTimeout);
        frontLeft.config_kD(0, Constants.Drive.kFrontLeftD, Constants.Drive.kTimeout);
        frontLeft.config_kF(0, Constants.Drive.kFrontLeftF, Constants.Drive.kTimeout);
        
        frontRight.config_kP(0, Constants.Drive.kFrontRightP, Constants.Drive.kTimeout);
        frontRight.config_kI(0, Constants.Drive.kFrontRightI, Constants.Drive.kTimeout);
        frontRight.config_kD(0, Constants.Drive.kFrontRightD, Constants.Drive.kTimeout);
        frontRight.config_kF(0, Constants.Drive.kFrontRightF, Constants.Drive.kTimeout);
        
        rearLeft.config_kP(0, Constants.Drive.kRearLeftP, Constants.Drive.kTimeout);
        rearLeft.config_kI(0, Constants.Drive.kRearLeftI, Constants.Drive.kTimeout);
        rearLeft.config_kD(0, Constants.Drive.kRearLeftD, Constants.Drive.kTimeout);
        rearLeft.config_kF(0, Constants.Drive.kRearLeftF, Constants.Drive.kTimeout);
        
        rearRight.config_kP(0, Constants.Drive.kRearRightP, Constants.Drive.kTimeout);
        rearRight.config_kI(0, Constants.Drive.kRearRightI, Constants.Drive.kTimeout);
        rearRight.config_kD(0, Constants.Drive.kRearRightD, Constants.Drive.kTimeout);
        rearRight.config_kF(0, Constants.Drive.kRearRightF, Constants.Drive.kTimeout);
        
        frontLeft.changeMotionControlFramePeriod((int) (Constants.Drive.kLoopPeriod * 1000));
        frontRight.changeMotionControlFramePeriod((int) (Constants.Drive.kLoopPeriod * 1000));
        rearLeft.changeMotionControlFramePeriod((int) (Constants.Drive.kLoopPeriod * 1000));
        rearRight.changeMotionControlFramePeriod((int) (Constants.Drive.kLoopPeriod * 1000));
        
        controlMode = ControlMode.PercentOutput;
        
        headingCtrl = new PIDController(0.0, 0.0, 0.0, Robot.gyro, (double out) -> {
            rotation = out;
        });
        
        set(DriveSignal.STOP);
    }
    
    public static enum MotorType {
        kFrontLeft(0),
        kFrontRight(1),
        kRearLeft(2),
        kRearRight(3);

        public int value;

        private MotorType(int value) {
            this.value = value;
        }
    }
    
    public static class DriveSignal {
        private double fl;
        private double fr;
        private double rl;
        private double rr;
        
        public DriveSignal(double value) {
            this(value, value, value, value);
        }

        public DriveSignal(double fl, double fr, double rl, double rr) {
            this.fl = fl;
            this.fr = fr;
            this.rl = rl;
            this.rr = rr; 
        }

        public DriveSignal(double[] wheelSpeeds) {
            this(wheelSpeeds[MotorType.kFrontLeft.value], wheelSpeeds[MotorType.kFrontRight.value],
                    wheelSpeeds[MotorType.kRearLeft.value], wheelSpeeds[MotorType.kRearRight.value]);
        }

        public double getFrontLeft()  { return fl; }
        public double getFrontRight()   { return fr; }
        public double getRearLeft() { return rl; }
        public double getRearRight()  { return rr; }

        public static final DriveSignal STOP = new DriveSignal(0,0,0,0);
    }
    
    /* OPERATOR CONTROL */
    
    public void mecanumDrive(double x, double y, double rot) {
        x = DriveHelper.applyDeadband(x);
        y = DriveHelper.applyDeadband(y);
        rot = DriveHelper.applyDeadband(rot);
        // double gyroAngle = Robot.gyro.getYaw();

        double[] wheelSpeeds = new double[4];
        wheelSpeeds[Drive.MotorType.kFrontLeft.value] = y - x + rot;
        wheelSpeeds[Drive.MotorType.kFrontRight.value] = y + x - rot;
        wheelSpeeds[Drive.MotorType.kRearLeft.value] = y + x + rot;
        wheelSpeeds[Drive.MotorType.kRearRight.value] = y - x - rot;
        DriveHelper.normalize(wheelSpeeds);
        
        if (frontLeft.getControlMode() == ControlMode.Velocity) {
            for (int i = 0; i < 4; i++)
                wheelSpeeds[i] *= Constants.Drive.kMaxVelocity;
        }

        set(new DriveSignal(wheelSpeeds));
    }

    public void set(DriveSignal signal) {
        frontLeft.set(controlMode, signal.getFrontLeft());
        frontRight.set(controlMode, signal.getFrontRight()); 
        rearLeft.set(controlMode, signal.getRearLeft()); 
        rearRight.set(controlMode, signal.getRearRight());
    }
    
    /* MOTION CONTROL */
    
    private MotionProfileStatus flStatus = new MotionProfileStatus();
    private MotionProfileStatus frStatus = new MotionProfileStatus();
    private MotionProfileStatus rlStatus = new MotionProfileStatus();
    private MotionProfileStatus rrStatus = new MotionProfileStatus();
    
    private int loopTimeout = -1;
    private boolean filled;
    private boolean following;
    private Notifier profileLoop = new Notifier(() -> {
        frontLeft.processMotionProfileBuffer();
        frontRight.processMotionProfileBuffer();
        rearLeft.processMotionProfileBuffer();
        rearRight.processMotionProfileBuffer();
    });
    
    public void startFollowing(TrajectoryPoint[] left, TrajectoryPoint[] right) {
        // stop drive base
        controlMode = ControlMode.MotionProfile;
        set(new DriveSignal(SetValueMotionProfile.Disable.value));
        
        // fill up talons with points
        fill(left, right);
        
        // reset control state
        following = true;
        filled = false;
        loopTimeout = Constants.Drive.kNumLoopsTimeout;
        
        // start processing profiles
        profileLoop.startPeriodic(Constants.Drive.kLoopPeriod);
        
        System.out.println("START motion profile!");
    }
    
    public void motionControl() {
        if (loopTimeout == 0)
            System.out.println("Talon SRX TIMEDOUT during motion control loop!");
        else if (loopTimeout != -1)
            --loopTimeout;
        
        frontLeft.getMotionProfileStatus(flStatus);
        frontRight.getMotionProfileStatus(frStatus);
        rearLeft.getMotionProfileStatus(rlStatus);
        rearRight.getMotionProfileStatus(rrStatus);
        
        if (!filled) {
            if (flStatus.btmBufferCnt >= Constants.Drive.kMinPointsInTalon &&
                frStatus.btmBufferCnt >= Constants.Drive.kMinPointsInTalon &&
                rlStatus.btmBufferCnt >= Constants.Drive.kMinPointsInTalon &&
                rrStatus.btmBufferCnt >= Constants.Drive.kMinPointsInTalon) {
                
                set(new DriveSignal(SetValueMotionProfile.Enable.value));
                loopTimeout = Constants.Drive.kNumLoopsTimeout;
                filled = true;
                
                System.out.println("BOTTOM BUFFER FILLED WITH MIN!");
            }
        } 
        else {
            if (flStatus.isUnderrun == false &&
                frStatus.isUnderrun == false &&
                rlStatus.isUnderrun == false &&
                rrStatus.isUnderrun == false)
                    loopTimeout = Constants.Drive.kNumLoopsTimeout;
            else
                System.out.println("A talon has UNDERRUNNED!");
            
            if (flStatus.activePointValid && flStatus.isLast &&
                frStatus.activePointValid && frStatus.isLast &&
                rlStatus.activePointValid && rlStatus.isLast &&
                rrStatus.activePointValid && rrStatus.isLast) {
                System.out.println("COMPLETED motion profile!");
                set(new DriveSignal(SetValueMotionProfile.Hold.value));
                following = false;
            }
        }
    }
    
    private void fill(TrajectoryPoint[] left, TrajectoryPoint[] right) {
        // Empty bottom buffer
        frontLeft.clearMotionProfileTrajectories();
        frontRight.clearMotionProfileTrajectories();
        rearLeft.clearMotionProfileTrajectories();
        rearRight.clearMotionProfileTrajectories();
        
        // Clear underrun flag so new ones can be detected
        frontLeft.clearMotionProfileHasUnderrun(0);
        frontRight.clearMotionProfileHasUnderrun(0);
        rearLeft.clearMotionProfileHasUnderrun(0);
        rearRight.clearMotionProfileHasUnderrun(0);
        
        frontLeft.configMotionProfileTrajectoryPeriod(0,0);
        frontRight.configMotionProfileTrajectoryPeriod(0,0);
        rearLeft.configMotionProfileTrajectoryPeriod(0,0);
        rearRight.configMotionProfileTrajectoryPeriod(0,0);
        
        int totalCnt = left.length;
        for (int i = 0; i < totalCnt; i++) {
            frontLeft.pushMotionProfileTrajectory(left[i]);
            frontRight.pushMotionProfileTrajectory(right[i]);
            rearLeft.pushMotionProfileTrajectory(left[i]);
            rearRight.pushMotionProfileTrajectory(right[i]);
        }
    }
    
    public boolean isFollowing() {
        return following;
    }
    
    public void stopFollowing() {
        // reset to give control back to operator
        set(new DriveSignal(SetValueMotionProfile.Disable.value));
        controlMode = ControlMode.PercentOutput;

        profileLoop.stop();
        
        // If the motion profile is abruptly ended, this state won't
        // be set to false by the motionControl method
        following = false;
        
        System.out.println("STOP motion profile");
    }
    
    
    private double maxFLVel = 0.0;
    private double maxFRVel = 0.0;
    private double maxRLVel = 0.0;
    private double maxRRVel = 0.0;

    public void outputToSmartDashboard() {
        // Position
        SmartDashboard.putNumber("Front Left Position (ticks)", frontLeft.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Front Right Position (ticks)", frontRight.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Rear Left Position (ticks)", rearLeft.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Rear Right Position (ticks)", rearRight.getSelectedSensorPosition(0));

        // Velocity
        double flVel = frontLeft.getSelectedSensorVelocity(0);
        double frVel = frontRight.getSelectedSensorVelocity(0);
        double rlVel = rearLeft.getSelectedSensorVelocity(0);
        double rrVel = rearRight.getSelectedSensorVelocity(0);
        SmartDashboard.putNumber("Front Left Velocity (ticks/100ms)", flVel);
        SmartDashboard.putNumber("Front Right Velocity (ticks/100ms)", frVel);
        SmartDashboard.putNumber("Rear Left Velocity (ticks/100ms)", rlVel);
        SmartDashboard.putNumber("Rear Right Velocity (ticks/100ms)", rrVel);

        // Max velocity
        SmartDashboard.putNumber("Front Left Highest Velocity (ticks/100ms)", 
                (maxFLVel = (flVel > maxFLVel ? flVel : maxFLVel)));
        SmartDashboard.putNumber("Front Right Highest Velocity (ticks/100ms)", 
                (maxFRVel = (frVel > maxFRVel ? frVel : maxFRVel)));
        SmartDashboard.putNumber("Rear Left Highest Velocity (ticks/100ms)", 
                (maxRLVel = (rlVel > maxRLVel ? rlVel : maxRLVel)));
        SmartDashboard.putNumber("Rear Right Highest Velocity (ticks/100ms)", 
                (maxRRVel = (rrVel > maxRRVel ? rrVel : maxRRVel)));
    }

    public void initDefaultCommand() {}
}
