package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Shooter", group = "Test")
public class Shooter extends OpMode {
    DcMotor motorLeft;
    DcMotor motorRight;

    @Override
    public void init() {
        motorLeft = hardwareMap.get(DcMotor.class, "motor1");
        motorRight = hardwareMap.get(DcMotor.class, "motor2");
    }

    @Override
    public void loop() {
        motorLeft.setPower(1.0);
        motorRight.setPower(1.0);
        telemetry.addData("Motor 1 Power", motorLeft.getPower());
        telemetry.addData("Motor 2 Power", motorRight.getPower());
        telemetry.update();
    }
}
