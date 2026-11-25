
package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Util.Algebra;
import org.firstinspires.ftc.teamcode.Util.Logger;

public class TurretSubsystem extends Subsystem {
    private static final double HOOD_CENTER = 0.5;
    private static final double HOOD_RANGE = 0.08;
    private static final double SHOT_VELOCITY_DROP = 100;

    private boolean justShot = false;
    private double previousVelocity = 0;

    private double speed = 0.0;

    private final DcMotorEx turretMotor;
    private final Servo hoodServo;

    private double hoodPosition = 0.0;

    public TurretSubsystem(HardwareMap hardwareMap, Logger logger) {
        super(hardwareMap, logger, Requirement.OPTIONAL);
        logger.infoLine("Initializing Turret Subsystem");
        turretMotor = getHardware(DcMotorEx.class, "turretMotor");
        hoodServo = getHardware(Servo.class, "hoodServo");
    }

    @Override
    public void onInit() {
        turretMotor.setDirection(DcMotor.Direction.FORWARD);
        turretMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        turretMotor.setPower(speed);

        turretMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turretMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        hoodServo.setDirection(Servo.Direction.FORWARD);
        setHoodPosition(hoodPosition);
    }

    @Override
    public void onLoop() {
        turretMotor.setPower(speed);

        double velocity = Math.abs(turretMotor.getVelocity());
        justShot = previousVelocity - velocity > SHOT_VELOCITY_DROP && previousVelocity > 200;
        previousVelocity = velocity;
    }

    @Override
    public void onStop() {
        speed = 0.0;
        turretMotor.setPower(0.0);
    }

    public double getEncoderVelocity() {
        return turretMotor.getVelocity();
    }

    public boolean didJustShoot() {
        return justShot;
    }

    public void adjustSpeed(double increment) {
        if (!isAvailable())
            return;

        setSpeed(speed + increment);
    }

    public void setSpeed(double value) {
        if (!isAvailable())
            return;

        speed = Algebra.clamp(-1.0, 1.0, value);
    }

    public double getSpeed() {
        return speed;
    }

    public void adjustHood(double increment) {
        if (!isAvailable())
            return;
        setHoodPosition(hoodPosition + increment);
        hoodServo.setPosition(hoodPosition);
    }

    public void setHoodPosition(double pos) {
        if (!isAvailable())
            return;

        hoodPosition = Algebra.clamp(
                HOOD_CENTER - HOOD_RANGE / 2.0,
                HOOD_CENTER + HOOD_RANGE / 2.0,
                pos);
    }

    public double getHoodPosition() {
        return hoodPosition;
    }
}
