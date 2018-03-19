package com.robodogs.frc2018.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

import com.robodogs.frc2018.Robot;

import com.robodogs.frc2018.subsystems.Drive.DriveSignal;

/**;
 *
 */
public class DrivePastLine extends Command {
    
    private double startTime;

    public DrivePastLine() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.drive);
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        startTime = Timer.getFPGATimestamp();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double currTime = Timer.getFPGATimestamp();
        if (currTime - startTime >= 10) {
            Robot.drive.set(new DriveSignal(0.75));
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Timer.getFPGATimestamp() - startTime >= 12.5;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.drive.set(DriveSignal.STOP);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
