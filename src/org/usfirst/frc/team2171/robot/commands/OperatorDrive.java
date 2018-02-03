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
		double x = Robot.cb.getDriveX();
		double y = Robot.cb.getDriveY();
		double rotation = Robot.cb.getDriveTwist();
		Robot.drive.mecanumDrive(x, y, rotation);
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.drive.setSpeed(Drive.DriveSignal.STOP);
	}

	protected void interrupted() {
		end();
	}
}
