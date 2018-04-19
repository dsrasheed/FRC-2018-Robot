package com.robodogs.frc2018.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.robodogs.frc2018.commands.LiftClaw;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwitchAuto extends CommandGroup {

    public SwitchAuto(boolean crossOver,char robotPos, char switchPos, boolean inMatch) {
        
        if (robotPos == AutoChooser.MIDDLE) {
            addSequential(new LiftClaw());
            
            if (!inMatch) {
                if (switchPos == AutoChooser.LEFT) {
                    addSequential(new FollowPath("MiddleToLeftSwitch"));
                    SmartDashboard.putString("Middle Auto", "Left");
                }
                else {
                    addSequential(new FollowPath("MiddleToRightSwitch"));
                    SmartDashboard.putString("Middle Auto", "Right");
                }
                addSequential(new AutoSpit(5.0));
            } else {
                addSequential(new FollowPath("MiddleToRightSwitch"));
                if (switchPos == AutoChooser.RIGHT)
                    addSequential(new AutoSpit(5.0));
            }
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
