package com.robodogs.frc2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.robodogs.frc2018.Robot;

public class LowerClaw extends Command {
    
    public LowerClaw() {
        requires(Robot.claw);
    }
    
    protected void initialize() {
        Robot.claw.lower();
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
