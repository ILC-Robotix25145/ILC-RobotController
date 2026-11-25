
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
    private double rotationSpeed = 0.0;

    private final DcMotorEx flywheelMotor;
    private final DcMotorEx rotationMotor;
    private final Servo hoodServo;

    private double hoodPosition = 0.0;

    public TurretSubsystem(HardwareMap hardwareMap, Logger logger) {
        super(hardwareMap, logger, Requirement.OPTIONAL);
        logger.infoLine("Initializing Turret Subsystem");
        flywheelMotor = getHardware(DcMotorEx.class, "turretMotor");
        rotationMotor = getHardware(DcMotorEx.class, "rotationMotor");
        hoodServo = getHardware(Servo.class, "hoodServo");
    }

    @Override
    public void onInit() {
        flywheelMotor.setDirection(DcMotor.Direction.FORWARD);
        flywheelMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flywheelMotor.setPower(speed);

        flywheelMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flywheelMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rotationMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rotationMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        hoodServo.setDirection(Servo.Direction.FORWARD);
        setHoodPosition(hoodPosition);
    }

    @Override
    public void onLoop() {
        flywheelMotor.setPower(speed);
        rotationMotor.setPower(rotationSpeed);

        double velocity = Math.abs(flywheelMotor.getVelocity());
        justShot = previousVelocity - velocity > SHOT_VELOCITY_DROP && previousVelocity > 200;
        previousVelocity = velocity;
    }

    @Override
    public void onStop() {
        speed = 0.0;
        flywheelMotor.setPower(0.0);
        rotationMotor.setPower(0.0);
    }

    public double getFlywheelVelocity() {
        return flywheelMotor.getVelocity();
    }

    public boolean didJustShoot() {
        return justShot;
    }

    public void adjustSpeed(double increment) {
        if (!isAvailable())
            return;

        setSpeed(speed + increment);
    }

    public void adjustRotationSpeed(double increment) {
        setRotationSpeed(rotationSpeed + increment);
    }

    public void setRotationSpeed(double value) {
        if (!isAvailable())
            return;

        rotationSpeed = Algebra.clamp(-1.0, 1.0, value);
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
