package com.robodogs.frc2018.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

import com.robodogs.lib.util.PIDTuner2;
import com.robodogs.frc2018.Robot;
import com.robodogs.frc2018.subsystems.Drive;

public class Turn extends Command {
    
    private PIDTuner2 tuner;
    private PIDController ctrller;
    
    public Turn(double degrees, boolean relative) {
        requires(Robot.drive);
        requires(Robot.gyro);
        
        ctrller = new PIDController(0.0056,0.0,0.0,Robot.gyro, (double out) -> {
            Robot.drive.mecanumDrive(0, 0, out);
        });
        ctrller.setContinuous(true);
        ctrller.setInputRange(0,360);
        ctrller.setOutputRange(-1.0, 1.0);
        ctrller.setAbsoluteTolerance(3.0);
        ctrller.setSetpoint(relative ? Robot.gyro.getAngle() + degrees : degrees);
        
        tuner = new PIDTuner2("turn");
    }

    protected void initialize() {
        // remove later
        ctrller.setPID(tuner.getP(), tuner.getI(), tuner.getD());
        ctrller.setSetpoint(tuner.getSetpoint());
        
        tuner.enable();
        ctrller.enable();
    }

    protected void execute() {
        tuner.sendError(ctrller.getError());
    }

    protected boolean isFinished() {
        return ctrller.onTarget();
    }

    protected void end() {
        ctrller.disable();
        tuner.disable();
        Robot.drive.set(Drive.DriveSignal.STOP);
    }

    protected void interrupted() {
        end();
    }
}
