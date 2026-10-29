package org.firstinspires.ftc.teamcode.Util.Config;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Options {
    @Range(min=-1, max=1, step=0.1)
    public static double motorSpeed = 0;
    public static DcMotor.ZeroPowerBehavior brakeMode = DcMotor.ZeroPowerBehavior.BRAKE;
    public static DcMotorSimple.Direction direction = DcMotorSimple.Direction.FORWARD;
}