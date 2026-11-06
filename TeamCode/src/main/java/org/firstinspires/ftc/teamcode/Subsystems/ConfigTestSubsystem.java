package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Util.Config.Option;

public class ConfigTestSubsystem {

    @Option (min=-1, max=1, step=0.1)
    public static double doubleTest = 0;
    @Option
    public static DcMotor.ZeroPowerBehavior enumTest = DcMotor.ZeroPowerBehavior.UNKNOWN;
}
