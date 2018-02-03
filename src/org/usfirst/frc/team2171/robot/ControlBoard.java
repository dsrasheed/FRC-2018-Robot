/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2171.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team2171.robot.commands.OperatorDrive;
import com.robodogs.lib.util.ControllerMap;

public class ControlBoard {

	public Joystick driveStick;

	public ControlBoard() {
		driveStick = new Joystick(0);

		JoystickButton driveSafety = new JoystickButton(driveStick, ControllerMap.RB);

		driveSafety.whileHeld(new OperatorDrive());
	}

	public double getDriveX() {
		return driveStick.getRawAxis(ControllerMap.leftJoyXAxis);
	}

	public double getDriveY() {
		return -(driveStick.getRawAxis(ControllerMap.leftJoyYAxis));
	}

	public double getDriveTwist() {
		return driveStick.getRawAxis(ControllerMap.rightJoyXAxis);
	}

	public double getAngularPower() {
		return driveStick.getRawAxis(ControllerMap.rightJoyXAxis);
	}

	public boolean gyroResetPressed() {
		return driveStick.getRawButtonReleased(ControllerMap.Back);
	}
}
