package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Util.Logger;

public class TurretSubsystem extends Subsystem {
    public double speed = 0.5; // TODO: Add abstraction over this

    private final DcMotor turretMotor;
    private final Servo hoodServo;

    public TurretSubsystem(HardwareMap hardwareMap, Logger logger) {
        super(hardwareMap, logger, Requirement.OPTIONAL);
        logger.infoLine("Initializing Turret Subsystem");
        turretMotor = getHardware(DcMotor.class, "turretMotor");
        hoodServo = getHardware(Servo.class, "hoodServo");
    }

    @Override
    public void onInit() {
        turretMotor.setDirection(DcMotor.Direction.FORWARD);
        turretMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        turretMotor.setPower(speed);

        hoodServo.setDirection(Servo.Direction.FORWARD);
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

    public void adjustHood(double v) {
        if (!isAvailable())
            return;

        double currentPosition = hoodServo.getPosition();
        double newPosition = currentPosition + v;
        newPosition = Math.max(0.0, Math.min(1.0, newPosition));

        hoodServo.setPosition(newPosition);
    }
}
