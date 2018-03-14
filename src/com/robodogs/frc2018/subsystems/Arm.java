package com.robodogs.frc2018.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import com.robodogs.frc2018.Constants;
import com.robodogs.frc2018.Robot;

public class Arm extends Subsystem {
    
    private TalonSRX master;
    private TalonSRX slave;
    private DigitalInput descendStop1;
    private DigitalInput descendStop2;
    private DigitalInput ascendStop1;
    private DigitalInput ascendStop2;
    
    public Arm() {
        master = new TalonSRX(Constants.Arm.kMasterCANID);
        slave = new TalonSRX(Constants.Arm.kSlaveCANID);
        
        master.configContinuousCurrentLimit(20, 0);
        slave.configContinuousCurrentLimit(20, 0);
        master.configPeakCurrentLimit(0, 0);
        slave.configPeakCurrentLimit(0, 0);
        master.configPeakCurrentDuration(0, 0);
        slave.configPeakCurrentDuration(0, 0);
        master.enableCurrentLimit(true);
        slave.enableCurrentLimit(true);
        
        master.configOpenloopRamp(1.0, 0);
        
        master.setNeutralMode(NeutralMode.Brake);
        slave.setNeutralMode(NeutralMode.Brake);
        
        slave.setInverted(true);
        slave.set(ControlMode.Follower, Constants.Arm.kMasterCANID);
        
        descendStop1 = new DigitalInput(Constants.Arm.kDescendStop1Port);
        ascendStop1 = new DigitalInput(Constants.Arm.kAscendStop1Port);
        ascendStop2 = new DigitalInput(Constants.Arm.kAscendingStop2Port);
    }
    
    public void ascend() {
        if (canAscend()) {
            master.set(ControlMode.PercentOutput, 0.75);
        }
    }
    
    public void stop() {
        master.set(ControlMode.PercentOutput, 0.0);
    }
    
    public void descend() {
        if (canDescend()) {
            master.set(ControlMode.PercentOutput, -0.75);
        }
    }
    
    public boolean canDescend() {
        return descendStop1.get() == true;
        // || descendStop2.get() != true
    }
    
    public boolean canAscend() {
        return ascendStop1.get() == true || ascendStop2.get() == true;
    }
    
    public void outputToSmartDashboard() {
        SmartDashboard.putBoolean("Can The Arm Descend", canDescend());
        SmartDashboard.putBoolean("Can the Arm Ascend", canAscend());
    }
    
    public void initDefaultCommand() {
        
    }
}

