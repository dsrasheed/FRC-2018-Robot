package com.robodogs.frc2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.robodogs.frc2018.Robot;

public class Spit extends Command {

    public Spit() {
        requires(Robot.claw);
    }

    protected void initialize() {
        Robot.claw.spit();
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
