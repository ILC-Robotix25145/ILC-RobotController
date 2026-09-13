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

    public void drive(double y, double x, double rotation) {
        y = applyDeadzone(y);
        x = applyDeadzone(x) * STRAFE_MULTIPLIER;
        rotation = applyDeadzone(rotation);

        double denominator = Math.max(
                Math.abs(y) + Math.abs(x) + Math.abs(rotation),
                1.0
        );

        double frontLeftPower = (y + x + rotation) / denominator;
        double backLeftPower = (y - x + rotation) / denominator;
        double frontRightPower = (y - x - rotation) / denominator;
        double backRightPower = (y + x - rotation) / denominator;

        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);
    }

    private double applyDeadzone(double value) {
        return Math.abs(value) < DEADZONE ? 0.0 : value;
    }
}
