package com.robodogs.lib.motion;

import java.io.File;
import java.nio.file.Paths;

import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Pathfinder;

public class TankTrajectory {
	
	private Trajectory leftTraj;
	private Trajectory rightTraj;
		
	public TankTrajectory(String trajectoryName) {
		File left = Paths.get(trajectoryName, "left").toFile();
		File right = Paths.get(trajectoryName, "right").toFile();
		
		leftTraj = Pathfinder.readFromFile(left);
		rightTraj = Pathfinder.readFromFile(right);
	}
	
	public TankTrajectory(Trajectory leftTraj, Trajectory rightTraj) {
		this.leftTraj = leftTraj;
		this.rightTraj = rightTraj;
	}
	
	public Trajectory getLeftTrajectory() {
		return leftTraj;
	}
	
	public Trajectory getRightTrajectory() {
		return rightTraj;
	}
}
