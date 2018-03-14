/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.robodogs.frc2018;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import com.robodogs.frc2018.commands.*;
import com.robodogs.frc2018.triggers.*;

import com.robodogs.lib.util.ControllerMap;

public class ControlBoard {
    
    public Joystick driveStick;
    public Joystick mechStick;
    
    public ControlBoard() {
        driveStick = new Joystick(0);
        mechStick = new Joystick(1);
        
        JoystickButton driveSafety = new JoystickButton(driveStick, ControllerMap.RB),
            spit = new JoystickButton(mechStick, ControllerMap.LB),
            suck = new JoystickButton(mechStick, ControllerMap.RB),
            ascend = new JoystickButton(mechStick, ControllerMap.Y),
            descend = new JoystickButton(mechStick, ControllerMap.A),
            toggleClaw = new JoystickButton(mechStick, ControllerMap.B),
            toggleHook = new JoystickButton(mechStick, ControllerMap.leftJoyButton);
        
        AxisButton wind = new AxisButton(mechStick, ControllerMap.leftJoyYAxis, 0.8);
        AxisButton unwind = new AxisButton(mechStick, ControllerMap.leftJoyYAxis, -0.8);
        
        JoystickButton unwindDrive = new JoystickButton(driveStick, ControllerMap.Start);
        
        
        driveSafety.whileHeld(new OperatorDrive());
        spit.whileHeld(new Spit());
        suck.whileHeld(new Suck());
        ascend.whileHeld(new Ascend());
        descend.whileHeld(new Descend());
        toggleClaw.whenPressed(new ToggleClaw());
        wind.whileActive(new Wind());
        unwind.whileActive(new Unwind());
        unwindDrive.whileHeld(new Unwind());
        toggleHook.whenPressed(new ToggleHook());
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
    
    public boolean gyroResetPressed() {
        return driveStick.getRawButtonReleased(ControllerMap.Back);
    }
}


