package com.robodogs.frc2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.robodogs.frc2018.Robot;

/**
 *
 */
public class ToggleClaw extends Command {

    public ToggleClaw() {
        requires(Robot.claw);
    }

    protected void initialize() {
        if (Robot.claw.isLifted())
            Robot.claw.lower();
        else
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
