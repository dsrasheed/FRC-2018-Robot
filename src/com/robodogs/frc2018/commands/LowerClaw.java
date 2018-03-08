package com.robodogs.frc2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.robodogs.frc2018.Robot;

/**
 *
 */
public class LowerClaw extends Command {

    public LowerClaw() {
        requires(Robot.claw);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.claw.lower();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
