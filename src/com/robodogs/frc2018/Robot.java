/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.robodogs.frc2018;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.CameraServer;

import com.robodogs.frc2018.subsystems.*;
import com.robodogs.frc2018.commands.*;

import com.kauailabs.navx.frc.AHRS;


public class Robot extends TimedRobot {

    public static Drive drive;
    public static Arm arm;
    public static Claw claw;
    public static AHRS gyro;
    public static Climber climber;
    public static ControlBoard cb;

    // For Competition
    private SendableChooser<Command> autoChooser;
    private SendableChooser<String> botPosChooser;
    
    // For Testing
    private SendableChooser<String> trajectoryChooser;
    private Notifier debugLoop;

    @Override
    public void robotInit() {
        try {
            gyro = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error setting up navx: " + ex.getMessage(), true);
        }
        initSubsystems();
        setupDriverStation();
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        Command auto = autoChooser.getSelected();

        if (auto != null) {
            if (auto instanceof FollowPath) {
                String name = trajectoryChooser.getSelected();
                System.out.println("Hreloo");
                ((FollowPath) auto).setTrajectory(name);
            }
            else if (auto instanceof PlaceCubeOnSwitch) {
                PlaceCubeOnSwitch placeAuto = (PlaceCubeOnSwitch) auto;
                String botPos = botPosChooser.getSelected();
                char switchPos = DriverStation.getInstance().getGameSpecificMessage().charAt(0);
                String name = placeAuto.getMotionProfileName(botPos, switchPos);
                placeAuto.setProfile(name);
            }
            auto.start();
        }
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        /* No need to cancel, wait for autonomous to complete
         * if the drivers want to do something else, the moment
         * they start using that subsystem, the auto will cancel.
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }*/
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        if (cb.gyroResetPressed())
            gyro.reset();
    }


    @Override
    public void testPeriodic() {
        Scheduler.getInstance().run();
    }
    
    public void initSubsystems() {
        drive = new Drive();
        arm = new Arm();
        claw = new Claw();
        climber = new Climber();
    }

    public void setupDriverStation() {
        // Controller input
        cb = new ControlBoard();

        // Choosers
        autoChooser = new SendableChooser<>();
        autoChooser.addDefault("Do Nothing", null);
        autoChooser.addObject("Drive Past Line", new DrivePastLine());
        autoChooser.addObject("Follow a Trajectory - Do not pick", new FollowPath());
        autoChooser.addObject("Place Cube on Switch", new PlaceCubeOnSwitch());
        
        botPosChooser = new SendableChooser<>();
        botPosChooser.addDefault("Middle", PlaceCubeOnSwitch.MIDDLE);
        botPosChooser.addObject("Left", PlaceCubeOnSwitch.LEFT);
        botPosChooser.addObject("Right", PlaceCubeOnSwitch.RIGHT);

        trajectoryChooser = new SendableChooser<>();
        trajectoryChooser.addObject("Pass Line", "PassLine"); 
        
        SmartDashboard.putData("Auto mode", autoChooser);
        SmartDashboard.putData("Auto Trajectory", trajectoryChooser);
        SmartDashboard.putData("Robot Position", botPosChooser);

        // Subsystem debugging
        // Extend Loop with interface DebugLoop, create default (with default keyboard) method onLoop that calls outputToSmartDashboard,
        // another method on DebugLoop interface. RoboSubsystem class will implement DebugLoop, abstract class will pass on implementation
        // subclasses.
        debugLoop = new Notifier(() -> {
            drive.outputToSmartDashboard();
            arm.outputToSmartDashboard();

            // Debug Gyro
            SmartDashboard.putBoolean("Gyro Is Calibrating", gyro.isCalibrating());
            SmartDashboard.putBoolean("Gyro Is Connected", gyro.isConnected());
            SmartDashboard.putNumber("Gyro Byte Count", gyro.getByteCount());
            SmartDashboard.putNumber("Gyro Update Count", gyro.getUpdateCount());

            SmartDashboard.putNumber("Gyro Angle: ", gyro.getAngle());
            SmartDashboard.putNumber("Gyro Yaw", gyro.getYaw());
        });
        debugLoop.startPeriodic(0.25);
        
        // Cameras
        CameraServer.getInstance().startAutomaticCapture();
    }
}
