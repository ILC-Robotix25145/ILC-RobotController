package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

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
        turretMotor.setDirection(DcMotor.Direction.FORWARD);
        turretMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        turretMotor.setPower(speed);

        hoodServo.setDirection(CRServo.Direction.FORWARD);
        hoodServo.setPower(0.0);
    }

    @Override
    public void onLoop() {
        turretMotor.setPower(speed);
    }

    @Override
    public void onStop() {
        speed = 0.0;
        turretMotor.setPower(0.0);
    }

    public void adjustSpeed(double increment) {
        speed += increment;
        speed = Math.max(-1.0, Math.min(1.0, speed));
    }

    public double getSpeed(){
        return speed;
    }

    public void adjustHood(double power) {
        if (!isAvailable())
            return;

        power = Math.max(-1.0, Math.min(1.0, power));
        if (Math.abs(power) < 0.05)
            power = 0.0;

        hoodServo.setPower(power);
    }
}
