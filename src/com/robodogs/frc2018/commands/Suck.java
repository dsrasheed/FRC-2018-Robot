package com.robodogs.frc2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.robodogs.frc2018.Robot;

public class Suck extends Command {

    public Suck() {
        requires(Robot.claw);
    }

    protected void initialize() {
        Robot.claw.suck();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.claw.stop();
    }

    protected void interrupted() {
        end();
    }
}
