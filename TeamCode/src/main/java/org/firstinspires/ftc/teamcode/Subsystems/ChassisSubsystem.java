package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Util.Logger;

public class ChassisSubsystem extends Subsystem {
    private static final double STRAFE_MULTIPLIER = 1.1;
    private static final double DEADZONE = 0.05;

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
    }

    public void drive(double y, double x, double rotation) {
        if (!isAvailable())
            return;

        y = applyDeadzone(y);
        x = applyDeadzone(x) * STRAFE_MULTIPLIER;
        rotation = applyDeadzone(rotation);

        double denominator = Math.max(
                Math.abs(y) + Math.abs(x) + Math.abs(rotation),
                1.0
        );

        frontLeftPower = (y + x + rotation) / denominator;
        backLeftPower = (y - x + rotation) / denominator;
        frontRightPower = (y - x - rotation) / denominator;
        backRightPower = (y + x - rotation) / denominator;
    }

    private double applyDeadzone(double value) {
        return Math.abs(value) < DEADZONE ? 0.0 : value;
    }
}
