package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Util.Hardware;

@TeleOp(name = "Intake", group = "Kickoff")
public class ActiveIntake extends OpMode {
    Hardware hardware = new Hardware();

    @Override
    public void init() {
        hardware.init(Hardware.NO_FAIL, telemetry, hardwareMap);
        hardware.initSubsystemChassis();
        hardware.initSubsystemIntake();
    }

    @Override
    public void loop() {

    }
}
