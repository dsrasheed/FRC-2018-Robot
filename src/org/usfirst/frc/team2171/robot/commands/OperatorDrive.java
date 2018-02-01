package org.usfirst.frc.team2171.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2171.robot.Robot;
import org.usfirst.frc.team2171.robot.subsystems.Drive;

/**
 *
 */
public class OperatorDrive extends Command {

    public OperatorDrive() {
    	requires(Robot.drive);
    }

    protected void initialize() {
    }

    protected void execute() {
    	double x = Robot.cb.getLeftStickX() * 0.4;
    	double y = Robot.cb.getLeftStickY() * 0.4;
    	double rotation = Robot.cb.getTwist() * 0.4;
    	Robot.drive.setSpeed(Robot.drive.mecanumDrive(x, y, rotation));
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.drive.setSpeed(Drive.DriveSpeed.ZERO);
    }

    protected void interrupted() {
    	end();
    }
}
