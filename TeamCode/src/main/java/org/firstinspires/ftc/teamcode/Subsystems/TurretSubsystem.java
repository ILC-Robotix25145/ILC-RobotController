package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Util.Algebra;
import org.firstinspires.ftc.teamcode.Util.Logger;

public class TurretSubsystem extends Subsystem {
    private double speed = 0.5;
    private final DcMotor turretMotor;
    private final CRServo hoodServo;

    public TurretSubsystem(HardwareMap hardwareMap, Logger logger) {
        super(hardwareMap, logger, Requirement.OPTIONAL);
        logger.infoLine("Initializing Turret Subsystem");
        turretMotor = getHardware(DcMotor.class, "turretMotor");
        hoodServo = getHardware(CRServo.class, "hoodServo");
    }

    @Override
    public void onInit() {
        if (turretMotor != null) {
            turretMotor.setDirection(DcMotor.Direction.FORWARD);
            turretMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            turretMotor.setPower(speed);
        }

        if (hoodServo != null) {
            hoodServo.setDirection(CRServo.Direction.FORWARD);
            hoodServo.setPower(0.0);
        }
    }

    @Override
    public void onLoop() {
        if (turretMotor != null) {
            turretMotor.setPower(speed);
        }
    }

    @Override
    public void onStop() {
        speed = 0.0;
        if (turretMotor != null) {
            turretMotor.setPower(0.0);
        }
        if (hoodServo != null) {
            hoodServo.setPower(0.0);
        }
    }

    public void adjustSpeed(double increment) {
        if (!isAvailable())
            return;

        setSpeed(speed + increment);
    }

    public void adjustHood(double power) {
        if (!isAvailable())
            return;

        double clampedPower = Algebra.clamp(-1.0, 1.0, power);
        final double finalPower = Math.abs(clampedPower) < 0.05 ? 0.0 : clampedPower;

        if (hoodServo != null) {
            hoodServo.setPower(finalPower);
        }
    }

    public void setSpeed(double newSpeed) {
        if (!isAvailable())
            return;

        speed = Algebra.clamp(-1.0, 1.0, newSpeed);
    }

    public double getSpeed(){
        return speed;
    }
}
