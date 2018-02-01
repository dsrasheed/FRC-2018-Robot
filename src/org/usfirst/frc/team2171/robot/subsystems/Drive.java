package org.usfirst.frc.team2171.robot.subsystems;

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
import org.usfirst.frc.team2171.robot.Constants;
import org.usfirst.frc.team2171.robot.Robot;
import org.usfirst.frc.team2171.robot.subsystems.DriveHelper.MotorType;

public class Drive extends Subsystem {
	
	/* TalonSRX Required
	private TalonSRX leftMaster;
	private TalonSRX leftSlave1;
	private TalonSRX leftSlave2;
	
	private TalonSRX rightMaster;
	private TalonSRX rightSlave1;
	private TalonSRX rightSlave2;*/
	
	private Talon frontLeft;
	private Talon frontRight;
	private Talon rearLeft;
	private Talon rearRight;
	
	/* TalonSRX Required
	private ControlMode controlMode;
	
	private Looper followerLoop;
	private TankTrajectory traj;
	
	// This variables is written to by the Pathfollower 
	// class and read-only by other threads.
	private volatile boolean isFollowing;*/
	
	public static class DriveSpeed {
		private double fl;
		private double fr;
		private double rl;
		private double rr;
		
		public DriveSpeed(double fl, double fr, double rl, double rr) {
			this.fl = fl;
			this.fr = fr;
			this.rl = rl;
			this.rr = rr; 
		}
		public DriveSpeed(double[] wheelSpeeds) {
			this(
				wheelSpeeds[DriveHelper.MotorType.kFrontLeft.getValue()],
				wheelSpeeds[DriveHelper.MotorType.kFrontRight.getValue()],
				wheelSpeeds[DriveHelper.MotorType.kRearLeft.getValue()],
				wheelSpeeds[DriveHelper.MotorType.kRearRight.getValue()]
			);
		}
		public double getFrontLeft()  { return fl; }
		public double getFrontRight()   { return fr; }
		public double getRearLeft() { return rl; }
		public double getRearRight()  { return rr; }
		
		public static final DriveSpeed ZERO = new DriveSpeed(0,0,0,0);
	}
	
	/* TalonSRX Required
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
	
	public Drive() {
		/* TalonSRX Required
		leftMaster = new TalonSRX(Constants.Drive.kLeftMasterCANID);
		leftSlave1 = new TalonSRX(Constants.Drive.kLeftSlave1CANID);
		leftSlave2 = new TalonSRX(Constants.Drive.kLeftSlave2CANID);
		rightMaster = new TalonSRX(Constants.Drive.kRightMasterCANID);
		rightSlave1 = new TalonSRX(Constants.Drive.kRightSlave1CANID);
		rightSlave2 = new TalonSRX(Constants.Drive.kRightSlave2CANID);
		
		leftSlave1.set(ControlMode.Follower, Constants.Drive.kLeftMasterCANID);
		leftSlave2.set(ControlMode.Follower, Constants.Drive.kLeftMasterCANID);
		
		rightSlave1.set(ControlMode.Follower, Constants.Drive.kRightMasterCANID);
		rightSlave2.set(ControlMode.Follower, Constants.Drive.kRightMasterCANID);
		
		// !!!!!!Set back to Velocity once you find out what the max velocity is!!!!!!
		controlMode = ControlMode.PercentOutput;
		setSpeed(DriveSpeed.ZERO);
		
		leftMaster.setNeutralMode(NeutralMode.Brake);
		rightMaster.setNeutralMode(NeutralMode.Brake);
		
		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		
		leftMaster.setInverted(true);
		leftMaster.setSensorPhase(true);*/
		
		frontLeft = new Talon(Constants.Drive.kFrontLeftPort);
		frontRight = new Talon(Constants.Drive.kFrontRightPort);
		rearLeft = new Talon(Constants.Drive.kRearLeftPort);
		rearRight = new Talon(Constants.Drive.kRearRightPort);
	}

	/* TalonSRX Required
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
	
	private double applyDeadband(double value) {
		if (Math.abs(value) <= Constants.Drive.kDeadband)
			return 0.0;
		return value;
	}
	
	public DriveSpeed mecanumDrive(double x, double y, double rotation) {
		x = applyDeadband(x);
		y = applyDeadband(y);
		rotation = applyDeadband(rotation);
		
		return DriveHelper.mecanumDrive(x, y, rotation, Robot.gyro.getYaw());
	}
	
	// Method is vulnerable to being called outside of commands
	// Should synchronize talons?
	public void setSpeed(DriveSpeed speed) {
		setSpeed(speed.getFrontLeft(), speed.getFrontRight(), speed.getRearLeft(), speed.getRearRight());
	}
	
	// Method is vulnerable to being called outside of commands
	// Should synchronize talons?
	public void setSpeed(double fl, double fr, double rl, double rr) {
		// When we have TalonSRXs, make sure to pass the mode into the set method
		frontLeft.set(fl);
		frontRight.set(-fr);
		rearLeft.set(rl);
		rearRight.set(rr);
	}
	
	public void outputToSmartDashboard() {
		/* Talon SRX Required
		SmartDashboard.putNumber("Left Encoder Position (ticks)", leftMaster.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Right Encoder Position (ticks)", rightMaster.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Left Encoder Velocity (ticks/100ms)", leftMaster.getSelectedSensorVelocity(0));
		SmartDashboard.putNumber("Right Encoder Velocity (ticks/100ms)", rightMaster.getSelectedSensorVelocity(0));
		SmartDashboard.putBoolean("Is Following a Trajectory?", isFollowing);*/
	}

    public void initDefaultCommand() {}
}
