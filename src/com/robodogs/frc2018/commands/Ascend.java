package com.robodogs.frc2018.commands;

import edu.wpi.first.wpilibj.command.Command;

import com.robodogs.frc2018.Robot;

/**
 *
 */
public class Ascend extends Command {

    public Ascend() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.arm.ascend();
        Robot.claw.suck();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Robot.arm.canAscend();
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.arm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
