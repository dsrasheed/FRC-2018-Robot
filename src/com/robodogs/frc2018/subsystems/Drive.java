package com.robodogs.frc2018.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Talon;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import com.robodogs.lib.loops.Loop;
import com.robodogs.lib.loops.Looper;
import com.robodogs.lib.motion.TankTrajectory;
import com.robodogs.lib.motion.TankEncoderFollower;
import com.robodogs.frc2018.Constants;
import com.robodogs.frc2018.Robot;

public class Drive extends Subsystem {

    private TalonSRX frontLeft;
    private TalonSRX frontRight;
    private TalonSRX rearLeft;
    private TalonSRX rearRight;

    private ControlMode controlMode;

    /*
	private Looper followerLoop;
	private TankTrajectory traj;

	// This variables is written to by the Pathfollower 
	// class and read-only by other threads.
	private volatile boolean isFollowing;*/

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

    /* Implements path following functionality
	private class PathFollower implements Loop {

		private TankEncoderFollower follower;

		public PathFollower() {
			follower = new TankEncoderFollower();
			follower.configurePIDVA(Constants.Drive.kP, Constants.Drive.kI, 
				Constants.Drive.kD, Constants.Drive.kV, Constants.Drive.kA);
		}

		@Override
		public void onStart() {
			isFollowing = true;

			follower.configureEncoder(leftMaster.getSelectedSensorPosition(0),
					Constants.Drive.kEncCodesPerRev, Constants.Drive.kWheelDiameter);
			follower.setTrajectory(traj);

			controlMode = ControlMode.PercentOutput;
			setSpeed(DrivePower.ZERO);
		}

		@Override
		public void onLoop() {
			double l = follower.calculateLeft(leftMaster.getSelectedSensorPosition(0));
			double r = follower.calculateRight(rightMaster.getSelectedSensorPosition(0));

			setSpeed(l, l, r, r);

			if (follower.isFinished())
				stopFollowing();
		}

		@Override
		public void onStop() {
			isFollowing = false;

			// !!!!!UNCOMMENT WHEN FIND MAX VELOCITY!!!
			// controlMode = ControlMode.Velocity;
			setSpeed(DriveSpeed.ZERO);
		}
	}*/

    /*
	public void startFollowing(TankTrajectory traj) {
		if (isFollowing)
			return;

		this.traj = traj;
		followerLoop.start();
	}

	public void stopFollowing() {
		followerLoop.stop();
	}

	public boolean isFollowing() {
		return isFollowing;
	}*/

    public Drive() {
        frontLeft = new TalonSRX(Constants.Drive.kFrontLeftCANID);
        frontRight = new TalonSRX(Constants.Drive.kFrontRightCANID);
        rearLeft = new TalonSRX(Constants.Drive.kRearLeftCANID);
        rearRight = new TalonSRX(Constants.Drive.kRearRightCANID);

        // Set back to ControlMode.Velocity once you find out what the max velocity is
        controlMode = ControlMode.PercentOutput;
        setSpeed(DriveSignal.STOP);

        frontLeft.setNeutralMode(NeutralMode.Brake);
        frontRight.setNeutralMode(NeutralMode.Brake);
        rearLeft.setNeutralMode(NeutralMode.Brake);
        rearRight.setNeutralMode(NeutralMode.Brake);

        frontLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        frontRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        rearLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        rearRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

        // You'll have to see which to invert by running on horse
        // leftMaster.setInverted(true);
        // leftMaster.setSensorPhase(true);

        // Set voltage ramp, set current limit, set PID
    }

    public void mecanumDrive(double x, double y, double rotation) {
        setSpeed(DriveHelper.mecanumDrive(x, y, rotation, Robot.gyro.getYaw()));
    }

    // Method is vulnerable to being called outside of commands
    // Should synchronize talons?
    public void setSpeed(DriveSignal speed) {
        frontLeft.set(controlMode, speed.getFrontLeft());
        frontRight.set(controlMode, speed.getFrontRight()); 
        rearLeft.set(controlMode, speed.getRearLeft()); 
        rearRight.set(controlMode, speed.getRearRight());
    }


    // Debugging state
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
