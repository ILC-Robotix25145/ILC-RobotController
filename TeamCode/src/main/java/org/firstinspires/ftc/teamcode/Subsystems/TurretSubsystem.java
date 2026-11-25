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

    // Ticks per rotatie a encoderului de pe flywheel (28 = motor goBILDA/REV fara reductie).
    // Schimba-l in functie de motorul tau.
    private static final double TICKS_PER_REV = 28.0;

    // Pune true daca si turretMotor1 are encoderul conectat (atunci se face media vitezelor).
    // Daca e false, viteza se citeste doar de la turretMotor.
    private static final boolean SECOND_MOTOR_HAS_ENCODER = false;

    private boolean justShot = false;
    private double previousVelocity = 0;

    private double speed = 0.0;
    private double rotationSpeed = 0.0;

    private final DcMotorEx flywheelMotor;
    private final DcMotorEx flywheelMotor2;
    private final DcMotorEx rotationMotor;
    private final Servo hoodServo;

    private double hoodPosition = 0.0;

    public TurretSubsystem(HardwareMap hardwareMap, Logger logger) {
        super(hardwareMap, logger, Requirement.OPTIONAL);
        logger.infoLine("Initializing Turret Subsystem");
        flywheelMotor = getHardware(DcMotorEx.class, "turretMotor");
        flywheelMotor2 = getHardware(DcMotorEx.class, "turretMotor1");
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

        // Al doilea motor. Daca cele doua motoare sunt montate fata in fata,
        // are nevoie de REVERSE ca sa se invarta in acelasi sens fizic.
        // Daca se lupta unul cu altul, schimba in FORWARD.
        flywheelMotor2.setDirection(DcMotor.Direction.REVERSE);
        flywheelMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flywheelMotor2.setPower(speed);

        flywheelMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flywheelMotor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rotationMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rotationMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        hoodServo.setDirection(Servo.Direction.FORWARD);
        setHoodPosition(hoodPosition);
    }

    @Override
    public void onLoop() {
        flywheelMotor.setPower(speed);
        flywheelMotor2.setPower(speed);
        rotationMotor.setPower(rotationSpeed);

        double velocity = readFlywheelVelocity();
        justShot = previousVelocity - velocity > SHOT_VELOCITY_DROP && previousVelocity > 200;
        previousVelocity = velocity;
    }

    @Override
    public void onStop() {
        speed = 0.0;
        flywheelMotor.setPower(0.0);
        flywheelMotor2.setPower(0.0);
        rotationMotor.setPower(0.0);
    }

    // Viteza absoluta in ticks/s
    private double readFlywheelVelocity() {
        double v1 = Math.abs(flywheelMotor.getVelocity());
        if (!SECOND_MOTOR_HAS_ENCODER)
            return v1;

        double v2 = Math.abs(flywheelMotor2.getVelocity());
        return (v1 + v2) / 2.0;
    }

    /** Viteza flywheel-ului in ticks/s. */
    public double getFlywheelVelocity() {
        return readFlywheelVelocity();
    }

    /** Viteza flywheel-ului in RPM. */
    public double getFlywheelRpm() {
        return readFlywheelVelocity() * 60.0 / TICKS_PER_REV;
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