package com.robodogs.frc2018.commands;

import java.io.File;
import java.nio.file.Paths;

import edu.wpi.first.wpilibj.command.Command;

import com.robodogs.frc2018.Robot;
import com.robodogs.lib.motion.TrajectoryDeserializer;
import com.robodogs.frc2018.Constants;
import com.ctre.phoenix.motion.TrajectoryPoint;

public class FollowPath extends Command {
    
    TrajectoryPoint[] left;
    TrajectoryPoint[] right;
    
    public FollowPath(String trajName) {
        setTrajectory(trajName);
    }

    public FollowPath(TrajectoryPoint[] left, TrajectoryPoint[] right) {
        setTrajectory(left,right);
        requires(Robot.drive);
    }
    
    public FollowPath() {}
    
    public void setTrajectory(String trajName) {
        File leftFile = Paths.get(Constants.Drive.kTrajectoriesDirName, trajName, "left.txt").toFile();
        File rightFile = Paths.get(Constants.Drive.kTrajectoriesDirName, trajName, "right.txt").toFile();
        this.left = new TrajectoryDeserializer(leftFile).deserialize(2005.10165,200.51016);
        this.right= new TrajectoryDeserializer(rightFile).deserialize(2005.10165,200.51016);
    }
    
    public void setTrajectory(TrajectoryPoint[] left, TrajectoryPoint[] right) {
        this.left = left;
        this.right = right;
    }
    
    protected void initialize() {
        Robot.drive.startFollowing(left,right);
    }
    
    protected void execute() {
        Robot.drive.motionControl();
    }
    
    protected boolean isFinished() {
        return !Robot.drive.isFollowing();
    }
    
    protected void end() {
        Robot.drive.stopFollowing();
    }
    
    protected void interrupted() {
        end();
    }
}
