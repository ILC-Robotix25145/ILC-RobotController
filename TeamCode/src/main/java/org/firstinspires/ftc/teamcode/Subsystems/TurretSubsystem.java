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

    private static final double TICKS_PER_REV = 28.0;

    public static double ROTATION_TICKS_PER_REV = 28.0;
    public static double ROTATION_DIRECTION = 1.0;

    public static double ROTATION_MIN_DEG = -150.0;
    public static double ROTATION_MAX_DEG = 150.0;

    public static double AIM_KP = 0.015;
    public static double AIM_KD = 0.0004;
    public static double AIM_KS = 0.05;
    public static double AIM_MAX_POWER = 0.6;
    public static double AIM_TOLERANCE_DEG = 1.0;

    private static final boolean SECOND_MOTOR_HAS_ENCODER = false;

    private double speed = 0.0;
    private double rotationSpeed = 0.0;
    private int turretRotationTicks = 0;

    private double targetTurretAngleDeg = 0.0;
    private double distanceToTargetCm = 0.0;

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
        rotationMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        hoodServo.setDirection(Servo.Direction.FORWARD);
        setHoodPosition(hoodPosition);
    }

    @Override
    public void onLoop() {
        turretRotationTicks = rotationMotor.getCurrentPosition();

        flywheelMotor.setPower(speed);
        flywheelMotor2.setPower(speed);
        rotationMotor.setPower(rotationSpeed);
    }

    @Override
    public void onStop() {
        speed = 0.0;
        rotationSpeed = 0.0;
        flywheelMotor.setPower(0.0);
        flywheelMotor2.setPower(0.0);
        rotationMotor.setPower(0.0);
    }

    public boolean autoAim(double targetX, double targetY,
                           double robotX, double robotY, double robotHeadingDeg) {
        if (!isAvailable())
            return false;

        double dx = targetX - robotX;
        double dy = targetY - robotY;
        distanceToTargetCm = Math.hypot(dx, dy);

        if (distanceToTargetCm < 1.0) {
            rotationSpeed = 0.0;
            return false;
        }

        double fieldAngleDeg = Math.toDegrees(Math.atan2(dy, dx));
        double desiredDeg = normalizeDeg(fieldAngleDeg - robotHeadingDeg);

        double currentDeg = getTurretAngleDeg();
        targetTurretAngleDeg = pickReachableAngle(desiredDeg, currentDeg);
        double errorDeg = targetTurretAngleDeg - currentDeg;

        if (Math.abs(errorDeg) <= AIM_TOLERANCE_DEG) {
            rotationSpeed = 0.0;
            return true;
        }

        double velocityDegPerSec = ROTATION_DIRECTION * rotationMotor.getVelocity() / ROTATION_TICKS_PER_REV * 360.0;

        double output = AIM_KP * errorDeg - AIM_KD * velocityDegPerSec;
        output += Math.copySign(AIM_KS, errorDeg);
        output = Algebra.clamp(-AIM_MAX_POWER, AIM_MAX_POWER, output);

        rotationSpeed = ROTATION_DIRECTION * output;
        return false;
    }


    private double pickReachableAngle(double desiredDeg, double currentDeg) {
        double best = Algebra.clamp(ROTATION_MIN_DEG, ROTATION_MAX_DEG, desiredDeg);
        double bestScore = Double.MAX_VALUE;
        for (int k = -1; k <= 1; k++) {
            double candidate = desiredDeg + 360.0 * k;
            double clamped = Algebra.clamp(ROTATION_MIN_DEG, ROTATION_MAX_DEG, candidate);
            double score = Math.abs(candidate - clamped) * 1000.0 + Math.abs(clamped - currentDeg);
            if (score < bestScore) {
                bestScore = score;
                best = clamped;
            }
        }
        return best;
    }

    private static double normalizeDeg(double deg) {
        deg %= 360.0;
        if (deg > 180.0) deg -= 360.0;
        if (deg <= -180.0) deg += 360.0;
        return deg;
    }

    public int getRotationTicks() { return turretRotationTicks; }

    public double getTurretAngleDeg() {
        return ROTATION_DIRECTION * turretRotationTicks / ROTATION_TICKS_PER_REV * 360.0;
    }

    public double getTargetTurretAngleDeg() {
        return targetTurretAngleDeg;
    }

    public double getDistanceToTargetCm() {
        return distanceToTargetCm;
    }

    // Viteza absoluta in ticks/s
    private double readFlywheelVelocity() {
        double v1 = Math.abs(flywheelMotor.getVelocity());
        if (!SECOND_MOTOR_HAS_ENCODER)
            return v1;

        double v2 = Math.abs(flywheelMotor2.getVelocity());
        return (v1 + v2) / 2.0;
    }

    public double getFlywheelVelocity() {
        return readFlywheelVelocity();
    }

    public double getFlywheelRpm() {
        return readFlywheelVelocity() * 60.0 / TICKS_PER_REV;
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