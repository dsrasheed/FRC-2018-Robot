package com.robodogs.frc2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.robodogs.frc2018.Robot;

public class Unwind extends Command {

    public Unwind() {
        requires(Robot.climber);
    }

    protected void initialize() {
        Robot.climber.unwind();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.climber.stop();
    }

    protected void interrupted() {
        end();
    }
}
