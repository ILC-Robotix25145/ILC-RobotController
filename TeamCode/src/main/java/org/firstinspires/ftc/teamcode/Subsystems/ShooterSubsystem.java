package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Util.Algebra;
import org.firstinspires.ftc.teamcode.Util.Logger;

public class ShooterSubsystem extends Subsystem {
    private final DcMotor flywheelMotor;
    private double flywheelPower = 0.0;

    public ShooterSubsystem(HardwareMap hardwareMap, Logger logger) {
        super(hardwareMap, logger, Requirement.OPTIONAL);
        logger.infoLine("Initializing Shooter Subsystem");
        flywheelMotor = getHardware(DcMotor.class, "flywheel");
    }

    @Override
    public void onInit() {
        if (flywheelMotor != null) {
            flywheelMotor.setDirection(DcMotor.Direction.FORWARD);
            flywheelMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            flywheelMotor.setPower(0.0);
        }
    }

    @Override
    public void onLoop() {
        if (flywheelMotor != null) {
            flywheelMotor.setPower(flywheelPower);
        }
    }

    @Override
    public void onStop() {
        flywheelPower = 0.0;
        if (flywheelMotor != null) {
            flywheelMotor.setPower(0.0);
        }
    }

    public void setFlywheelPower(double power) {
        if (!isAvailable())
            return;
        this.flywheelPower = Algebra.clamp(0.0, 1.0, power);
    }

    public double getFlywheelPower() {
        return flywheelPower;
    }
}
