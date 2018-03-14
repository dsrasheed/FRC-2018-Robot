package com.robodogs.frc2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.robodogs.frc2018.Robot;

/**
 *
 */
public class ToggleHook extends Command {

    public ToggleHook() {
        requires(Robot.climber);
    }

    protected void initialize() {
        if (Robot.climber.isExtended())
            Robot.climber.retract();
        else
            Robot.climber.extend();
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }
}
