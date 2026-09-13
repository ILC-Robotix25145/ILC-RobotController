package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ChassisSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Util.Hardware;

@TeleOp(name = "Main TeleOp", group = "Kickoff")
public class MainTeleOp extends OpMode {
    Hardware hardware = new Hardware();
    ChassisSubsystem chassis = new ChassisSubsystem(hardware);
    IntakeSubsystem intake = new IntakeSubsystem(hardware);

    @Override
    public void init() {
        hardware.init(Hardware.NO_FAIL, telemetry, hardwareMap);
        chassis.init(gamepad1);
        intake.init(gamepad1);
        telemetry.update();
    }

    @Override
    public void loop() {
        chassis.update();
        intake.update();
        telemetry.update();
    }
}
