package com.robodogs.frc2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.robodogs.frc2018.Robot;

public class Wind extends Command {

    public Wind() {
        requires(Robot.climber);
    }

    protected void initialize() {
        Robot.climber.wind();
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
