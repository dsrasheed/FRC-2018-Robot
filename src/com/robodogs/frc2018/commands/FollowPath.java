package com.robodogs.frc2018.commands;

import com.robodogs.lib.motion.TankTrajectory;
import edu.wpi.first.wpilibj.command.Command;

import com.robodogs.frc2018.Robot;

public class FollowPath extends Command {

    private TankTrajectory traj;

    public FollowPath(String trajName) {
        this(new TankTrajectory(trajName));
    }

    public FollowPath(TankTrajectory traj) {
        requires(Robot.drive);
        this.traj = traj;
    }

    public FollowPath() {}

    public void setTrajectory(String trajName) {
        this.traj = new TankTrajectory(trajName);
    }

    public void setTrajectory(TankTrajectory traj) {
        this.traj = traj;
    }

    protected void initialize() {
        //Robot.drive.startFollowing(traj);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        //return !Robot.drive.isFollowing();
        return true;
    }

    protected void end() {
        // Robot.drive.stopFollowing();
    }

    protected void interrupted() {
        end();
    }
}
