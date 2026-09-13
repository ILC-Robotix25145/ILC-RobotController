package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Util.Hardware;

public class ChassisSubsystem extends Subsystem {
    private static final double STRAFE_MULTIPLIER = 1.1;
    private static final double DEADZONE = 0.05;
    private static final double NORMAL_SPEED = 1.0;
    private static final double SLOW_SPEED = 0.4;

    private Gamepad gamepad;

    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;

    public ChassisSubsystem(Hardware hardware) {
        super(hardware);
    }

    @Override
    public void init(Gamepad gamepad) {
        this.gamepad = gamepad;
        frontLeft = hardware.get(DcMotor.class, "frontLeft");
        backLeft = hardware.get(DcMotor.class, "backLeft");
        frontRight = hardware.get(DcMotor.class, "frontRight");
        backRight = hardware.get(DcMotor.class, "backRight");

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
    public void update() {
        drive(
                -gamepad.left_stick_y,
                gamepad.left_stick_x,
                gamepad.right_stick_x,
                gamepad.left_bumper ? SLOW_SPEED : NORMAL_SPEED
        );
    }

    private void drive(double y, double x, double rx, double speed) {
        y = applyDeadzone(y);
        x = applyDeadzone(x) * STRAFE_MULTIPLIER;
        rx = applyDeadzone(rx);

        double denominator = Math.max(
                Math.abs(y) + Math.abs(x) + Math.abs(rx),
                1.0
        );

        double frontLeftPower = (y + x + rx) / denominator * speed;
        double backLeftPower = (y - x + rx) / denominator * speed;
        double frontRightPower = (y - x - rx) / denominator * speed;
        double backRightPower = (y + x - rx) / denominator * speed;

        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);
    }

    private double applyDeadzone(double value) {
        return Math.abs(value) < DEADZONE ? 0.0 : value;
    }
}
