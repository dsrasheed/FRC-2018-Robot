package com.robodogs.frc2018.commands.auto;

import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;

import edu.wpi.first.wpilibj.command.Command;

import com.robodogs.frc2018.Robot;
import com.robodogs.lib.motion.MyTrajectoryDeserializer;
import com.robodogs.frc2018.Constants;
import com.ctre.phoenix.motion.TrajectoryPoint;

public class FollowPath extends Command {
    
    TrajectoryPoint[] left;
    TrajectoryPoint[] right;
    
    public FollowPath(String trajName) {
        this();
        setTrajectory(trajName);
    }

    public FollowPath(TrajectoryPoint[] left, TrajectoryPoint[] right) {
        this();
        setTrajectory(left,right);
    }
    
    public FollowPath() {
        requires(Robot.drive);
    }
    
    public void setTrajectory(String trajName) {
        String leftPath = Paths.get(Constants.Drive.kTrajectoriesDirName, trajName, "left.txt").toString();
        String rightPath = Paths.get(Constants.Drive.kTrajectoriesDirName, trajName, "right.txt").toString();
        this.left = new MyTrajectoryDeserializer(leftPath).deserialize(2005.10165,200.51016);
        this.right = new MyTrajectoryDeserializer(rightPath).deserialize(2005.10165,200.51016);
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
