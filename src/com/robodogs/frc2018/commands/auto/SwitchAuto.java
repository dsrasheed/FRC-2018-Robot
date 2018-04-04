package com.robodogs.frc2018.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.robodogs.frc2018.commands.LiftClaw;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwitchAuto extends CommandGroup {

    public SwitchAuto(boolean crossOver,char robotPos, char switchPos) {
        
        if (robotPos == AutoChooser.MIDDLE) {
            addSequential(new LiftClaw());
            
            /*if (switchPos == AutoChooser.LEFT) {
                //addSequential(new FollowPath("MiddleToLeftSwitch")); // Left switch from middle
                SmartDashboard.putBoolean("Left Switch Auto", true);
                SmartDashboard.putBoolean("Right Switch Auto", false);
                //addSequential(new AutoSpit(5.0));
            }
            else {
                addSequential(new FollowPath("MiddleToRightSwitch")); // Right switch from middle
                SmartDashboard.putBoolean("Right Switch Auto", true);
                SmartDashboard.putBoolean("Left Switch Auto", false);
                addSequential(new AutoSpit(5.0));
            }*/
            
            addSequential(new FollowPath("MiddleToRightSwitch"));
            if (switchPos == AutoChooser.RIGHT)
                addSequential(new AutoSpit(5.0));
            return;
        }
        
        if (!crossOver) {
            addSequential(new LiftClaw());
            addSequential(new FollowPath("DriveNextToSwitch"));
            
            if (robotPos == AutoChooser.LEFT)
                addSequential(new Turn(90, false));
            else if (robotPos == AutoChooser.RIGHT)
                addSequential(new Turn(270, false));
            
            addSequential(new FollowPath("DriveUpToSwitch"));
            addSequential(new AutoSpit(5.0));
        }
    }
}
