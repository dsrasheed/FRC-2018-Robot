package com.robodogs.frc2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.robodogs.frc2018.Robot;

/**
 *
 */
public class LiftClaw extends Command {

    public LiftClaw() {
        requires(Robot.claw);
    }

    protected void initialize() {
        Robot.claw.lift();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
