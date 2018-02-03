package com.robodogs.lib.motion;

import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.Trajectory;

/*
 * A wrapper around two EncoderFollowers to reduce
 * repeat code when needing to compute both left and right 
 */
public class TankEncoderFollower {

	private EncoderFollower leftFollower;
	private EncoderFollower rightFollower;

	public TankEncoderFollower() {
		leftFollower = new EncoderFollower();
		rightFollower = new EncoderFollower();
	}

	public TankEncoderFollower(TankTrajectory traj) {
		leftFollower = new EncoderFollower(traj.getLeftTrajectory());
		rightFollower = new EncoderFollower(traj.getRightTrajectory());
	}

	public void setTrajectory(TankTrajectory traj) {
		leftFollower.setTrajectory(traj.getLeftTrajectory());
		rightFollower.setTrajectory(traj.getRightTrajectory());
	}

	public void configurePIDVA(double kp, double ki, double kd, double kv, double ka) {
		leftFollower.configurePIDVA(kp, ki, kd, kv, ka);
		rightFollower.configurePIDVA(kp, ki, kd, kv, ka);
	}

	public void configureEncoder(int initial_position, int ticks_per_revolution, double wheel_diameter) {
		leftFollower.configureEncoder(initial_position, ticks_per_revolution, wheel_diameter);
		rightFollower.configureEncoder(initial_position, ticks_per_revolution, wheel_diameter);
	}

	public double calculateLeft(int encoder_tick) {
		return leftFollower.calculate(encoder_tick);
	}

	public double calculateRight(int encoder_tick) {
		return rightFollower.calculate(encoder_tick);
	}

	public void reset() {
		leftFollower.reset();
		rightFollower.reset();
	}

	public Trajectory.Segment getLeftSegment() {
		return leftFollower.getSegment();
	}

	public Trajectory.Segment getRightSegment() {
		return rightFollower.getSegment();
	}

	public double getHeading() {
		// The heading is the same for the left and right trajectories
		return leftFollower.getHeading();
	}

	public boolean isFinished() {
		// The left and right trajectories will finish at the same time
		// since they both have the same number of segments.
		return leftFollower.isFinished();
	}
}
