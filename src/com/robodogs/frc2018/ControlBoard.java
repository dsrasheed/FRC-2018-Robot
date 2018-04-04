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
            toggleClaw = new JoystickButton(mechStick, ControllerMap.B),
            toggleHook = new JoystickButton(mechStick, ControllerMap.leftJoyButton);
        
        AxisButton ascend = new AxisButton(mechStick, ControllerMap.rightJoyYAxis, -0.8),
            descend = new AxisButton(mechStick, ControllerMap.rightJoyYAxis, 0.8),
            wind = new AxisButton(mechStick, ControllerMap.leftJoyYAxis, 0.8),
            unwind = new AxisButton(mechStick, ControllerMap.leftJoyYAxis, -0.8);
        
        driveSafety.whileHeld(new OperatorDrive());
        spit.whileHeld(new Spit());
        suck.whileHeld(new Suck());
        toggleClaw.whenPressed(new ToggleClaw());
        toggleHook.whenPressed(new ToggleHook());
        
        ascend.whileActive(new Ascend());
        descend.whileActive(new Descend());
        //wind.whileActive(new Wind());
        unwind.whileActive(new Unwind());
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
}


