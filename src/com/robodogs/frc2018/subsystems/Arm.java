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
    private DigitalInput limit;
    
    private Encoder enc;

    public Arm() {
        master = new TalonSRX(Constants.Arm.kMasterCANID);
        slave = new TalonSRX(Constants.Arm.kSlaveCANID);
        
        master.configContinuousCurrentLimit(20, 0);
        slave.configContinuousCurrentLimit(20, 0);
        master.configPeakCurrentLimit(0, 0);
        slave.configPeakCurrentLimit(0, 0);
        master.configPeakCurrentDuration(0, 0);
        slave.configPeakCurrentDuration(0, 0);
        // master.enableCurrentLimit(true);
        // slave.enableCurrentLimit(true);

        master.configOpenloopRamp(1.0, 0);
        
        master.setNeutralMode(NeutralMode.Brake);
        slave.setNeutralMode(NeutralMode.Brake);
        
        slave.setInverted(true);
        slave.set(ControlMode.Follower, Constants.Arm.kMasterCANID);
        
        // enc = new Encoder(0, 1, false, Encoder.EncodingType.k1X);
        // enc.setDistancePerPulse(1.0);
        
        limit = new DigitalInput(Constants.Arm.kLimitPort);
    }
    
    public void ascend() {
        if (canContinueAscend())
            master.set(ControlMode.PercentOutput, 0.75);
    }
    
    public void stop() {
        master.set(ControlMode.PercentOutput, 0.0);
    }
    
    public void descend() {
        if (canContinueDescend())
            master.set(ControlMode.PercentOutput, -0.75);
        // else
            // enc.reset();
    }
    
    // combine into single function, can continue.
    public boolean canContinueDescend() {
        // return limit.get();
        return true;
    }
    
    // change to isPastLimit
    public boolean canContinueAscend() {
        // return enc.get() < 100;
        return true;
    }
    
    public void outputToSmartDashboard() {
        // SmartDashboard.putNumber("Positio of Arm", enc.get());
        // SmartDashboard.putNumber("Rate of Arm", enc.getRate());
    }
     
    public void initDefaultCommand() {
        
    }
}
