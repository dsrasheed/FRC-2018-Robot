package com.robodogs.frc2018.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.robodogs.frc2018.commands.LiftClaw;
import com.robodogs.frc2018.commands.LowerClaw;
import com.robodogs.frc2018.commands.Ascend;
import com.robodogs.frc2018.commands.Descend;

/**
 *
 */
public class ScaleAuto extends CommandGroup {

    public ScaleAuto(boolean crossOver, char robotPos) {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
        if (!crossOver) {
            addSequential(new LiftClaw());
            addSequential(new FollowPath("DriveNextToScale"));
            
            if (robotPos == AutoChooser.LEFT)
                addSequential(new Turn(270,false));
            else
                addSequential(new Turn(90,false));
            
            addSequential(new Ascend());
            addSequential(new LowerClaw());
            addSequential(new AutoSpit(1.5));
            addSequential(new LiftClaw());
            addSequential(new Descend());
            addSequential(new LowerClaw());
        }
    }
}
