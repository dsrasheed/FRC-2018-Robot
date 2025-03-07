package com.robodogs.frc2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.robodogs.frc2018.Robot;

/**
 *
 */
public class Descend extends Command {
    
    public Descend() {
        requires(Robot.arm);
    }
    
    protected void initialize() {
        Robot.arm.descend();
    }
    
    protected void execute() {
    }
    
    protected boolean isFinished() {
        return !Robot.arm.canDescend();
    }
    
    protected void end() {
        Robot.arm.stop();
    }
    
    protected void interrupted() {
        end();
    }
}
