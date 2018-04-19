package com.robodogs.frc2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import com.robodogs.frc2018.Robot;

public class Spit extends Command {
    
    private boolean firstExecute;
    private double speed;
   
    public Spit() {
        this(1.0);
    }
    
    public Spit(double speed) {
        this.speed = speed;
        requires(Robot.claw);
    }

    protected void initialize() {
        Robot.claw.spit(this.speed);
        firstExecute = true;
    }

    protected void execute() {
        if (firstExecute) {
            Robot.claw.push(true);
            firstExecute = false;
        }       
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.claw.stop();
        Robot.claw.push(false);
    }

    protected void interrupted() {
        end();
    }
}
