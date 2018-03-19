package com.robodogs.frc2018.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import com.robodogs.frc2018.Constants;

public class Claw extends Subsystem {
    
    private TalonSRX master;
    private TalonSRX slave;
    
    private Solenoid forwardLeft;
    private Solenoid reverseLeft;
    private Solenoid forwardRight;
    private Solenoid reverseRight;
    
    private boolean isLifted;

    public Claw() {
        master = new TalonSRX(Constants.Claw.kMasterCANID);
        slave = new TalonSRX(Constants.Claw.kSlaveCANID);
        
        master.configContinuousCurrentLimit(18, 0);
        slave.configContinuousCurrentLimit(18, 0);
        master.configPeakCurrentLimit(0, 0);
        slave.configPeakCurrentLimit(0, 0);
        master.configPeakCurrentDuration(0, 0);
        slave.configPeakCurrentDuration(0, 0);
        master.enableCurrentLimit(true);
        slave.enableCurrentLimit(true);

        master.setNeutralMode(NeutralMode.Brake);
        slave.setNeutralMode(NeutralMode.Brake);
        
        slave.set(ControlMode.Follower, Constants.Claw.kMasterCANID);
                
        forwardLeft = new Solenoid(Constants.Claw.kLeftForwardPort);
        reverseLeft = new Solenoid(Constants.Claw.kLeftReversePort);
        forwardRight = new Solenoid(Constants.Claw.kRightForwardPort);
        reverseRight = new Solenoid(Constants.Claw.kRightReversePort);
    }
    
    public void suck() {
        if (!hasCube()) {
            master.set(ControlMode.PercentOutput, -1.0);
        }
    }
    
    public void spit() {
        master.set(ControlMode.PercentOutput, 1.0);
    }
    
    public void stop() {
        master.set(ControlMode.PercentOutput, 0.0);
    }
    
    public void lift() {
        reverseLeft.set(true);
        reverseRight.set(true);
        forwardLeft.set(false);
        forwardRight.set(false);
        
        isLifted = true;
    }
    
    public void lower() {
        reverseLeft.set(false);
        reverseRight.set(false);
        forwardLeft.set(true);
        forwardRight.set(true);
        
        isLifted = false;
    }
    
    public boolean hasCube() {
        //return limit.get();
        return false;
    }
    
    public boolean isLifted() {
        return isLifted;
    }
     
    public void initDefaultCommand() {
        lift();
    }
}
