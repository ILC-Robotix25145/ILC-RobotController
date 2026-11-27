package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Util.Logger;

public class ChassisSubsystem extends Subsystem {
    public static double STRAFE_MULTIPLIER = 1.1;
    public static double DEADZONE = 0.05;
    private static final double SMOOTHING = 0.2;
    private static final double DRIVE_EXPONENT = 2.0;
    private static final double ROTATION_EXPONENT = 1.5;
    private static final double MAX_DRIVE_POWER = 0.75;

    private double smoothY = 0;
    private double smoothX = 0;
    private double smoothRotation = 0;

    private final DcMotor frontLeft;
    private final DcMotor backLeft;
    private final DcMotor frontRight;
    private final DcMotor backRight;

    private double frontLeftPower = 0.0;
    private double backLeftPower = 0.0;
    private double frontRightPower = 0.0;
    private double backRightPower = 0.0;

    public ChassisSubsystem(HardwareMap hardwareMap, Logger logger) {
        super(hardwareMap, logger);
        logger.infoLine("Initializing Chassis subsystem");
        frontLeft = getHardware(DcMotor.class, "frontLeft");
        backLeft = getHardware(DcMotor.class, "backLeft");
        frontRight = getHardware(DcMotor.class, "frontRight");
        backRight = getHardware(DcMotor.class, "backRight");
    }

    @Override
    public void onInit() {
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void onLoop() {
        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);
    }

    @Override
    public void onStop() {
        frontLeftPower = 0.0;
        backLeftPower = 0.0;
        frontRightPower = 0.0;
        backRightPower = 0.0;

        frontLeft.setPower(0.0);
        backLeft.setPower(0.0);
        frontRight.setPower(0.0);
        backRight.setPower(0.0);

        smoothY = 0.0;
        smoothX = 0.0;
        smoothRotation = 0.0;
    }

    public void drive(double y, double x, double rotation, double speedMultiplier) {
        if (!isAvailable())
            return;

        y = applyDeadzone(y);
        x = applyDeadzone(x) * STRAFE_MULTIPLIER;
        rotation = applyDeadzone(rotation);

        y = applyCurve(y, DRIVE_EXPONENT);
        x = applyCurve(x, DRIVE_EXPONENT);
        rotation = applyCurve(rotation, ROTATION_EXPONENT);

        smoothY = SMOOTHING * smoothY + (1 - SMOOTHING) * y;
        smoothX = SMOOTHING * smoothX + (1 - SMOOTHING) * x;
        smoothRotation = SMOOTHING * smoothRotation + (1 - SMOOTHING) * rotation;

        y = smoothY;
        x = smoothX;
        rotation = smoothRotation;

        double fl = y + x + rotation;
        double bl = y - x + rotation;
        double fr = y - x - rotation;
        double br = y + x - rotation;

        double denominator = Math.max(
                1.0,
                Math.max(
                        Math.max(Math.abs(fl), Math.abs(bl)),
                        Math.max(Math.abs(fr), Math.abs(br))
                )
        );

        frontLeftPower = fl / denominator * MAX_DRIVE_POWER * speedMultiplier;
        backLeftPower = bl / denominator * MAX_DRIVE_POWER * speedMultiplier;
        frontRightPower = fr / denominator * MAX_DRIVE_POWER * speedMultiplier;
        backRightPower = br / denominator * MAX_DRIVE_POWER * speedMultiplier;
    }

    private double applyCurve(double value, double exponent) {
        return Math.copySign(
                Math.pow(Math.abs(value), exponent),
                value
        );
    }

    private double applyDeadzone(double value) {
        if (Math.abs(value) < DEADZONE)
            return 0.0;

        return Math.copySign(
                (Math.abs(value) - DEADZONE) / (1.0 - DEADZONE),
                value
        );
    }
}
