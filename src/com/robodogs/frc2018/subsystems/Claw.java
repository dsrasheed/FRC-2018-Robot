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
    private DigitalInput limit;
    
    private Solenoid forward1;
    private Solenoid forward2;
    private Solenoid reverse1;
    private Solenoid reverse2;
    
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
        
        limit = new DigitalInput(Constants.Claw.kLimitPort);
        
        forward1 = new Solenoid(Constants.Claw.kForwardPort1);
        forward2 = new Solenoid(Constants.Claw.kForwardPort2);
        reverse1 = new Solenoid(Constants.Claw.kReversePort1);
        reverse2 = new Solenoid(Constants.Claw.kReversePort2);
    }
    
    public void suck() {
        if (!hasCube())
            master.set(ControlMode.PercentOutput, 1.0);
    }
    
    public void spit() {
        master.set(ControlMode.PercentOutput,  -1.0);
    }
    
    public void stop() {
        master.set(ControlMode.PercentOutput, 0.0);
    }
    
    public void lift() {
        forward1.set(true);
        forward2.set(true);
        reverse1.set(false);
        reverse1.set(false);
        
        isLifted = true;
    }
    
    public void lower() {
        forward1.set(false);
        forward2.set(false);
        reverse1.set(true);
        reverse2.set(true);
        
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
