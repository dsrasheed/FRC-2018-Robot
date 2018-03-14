package com.robodogs.frc2018.commands;

import edu.wpi.first.wpilibj.Timer;

public class AutoSpit extends Spit {
    
    private double duration;
    private double startTime;
    
    public AutoSpit(double duration) {
        super();
        this.duration = duration;
    }

    protected void initialize() {
        super.initialize();
        startTime = Timer.getFPGATimestamp();
    }

    protected boolean isFinished() {
        return Timer.getFPGATimestamp() - startTime >= duration;
    }
}
