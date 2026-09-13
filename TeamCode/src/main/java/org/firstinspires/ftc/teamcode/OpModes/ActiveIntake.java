package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Util.Hardware;

@TeleOp(name = "Intake", group = "Kickoff")
public class ActiveIntake extends OpMode {
    private DcMotor intakeMotor;

    Hardware hardware = new Hardware();
    Intake intake;

    @Override
    public void init() {
        hardware.init(Hardware.NO_FAIL, telemetry, hardwareMap);
        intake = new Intake(hardware);
        intakeMotor = hardware;
    }

    @Override
    public void loop() {
        intake.update();
        telemetry.update();
    }
}
