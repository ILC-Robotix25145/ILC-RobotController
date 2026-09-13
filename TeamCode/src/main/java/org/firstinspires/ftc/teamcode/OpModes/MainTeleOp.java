package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ChassisSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Util.Hardware;
import org.firstinspires.ftc.teamcode.Util.Logger;

@TeleOp(name = "Main TeleOp", group = "Kickoff")
public class MainTeleOp extends OpMode {
    // Utils
    Logger logger;
    Hardware hardware;

    // Subsystems
    IntakeSubsystem intake;
    ChassisSubsystem chassis;

    // State
    boolean lastLeftBumper = false;

    @Override
    public void init() {
        logger = new Logger(telemetry);
        hardware = new Hardware(hardwareMap, logger);

        intake = new IntakeSubsystem(hardware, logger);
        chassis = new ChassisSubsystem(hardware, logger);

        intake.init();
        chassis.init();

        telemetry.update();
    }

    @Override
    public void loop() {
        // Intake
        boolean leftBumper = gamepad1.left_bumper;
        if (gamepad1.left_bumper && !lastLeftBumper)
            intake.toggle();
        lastLeftBumper = leftBumper;

        // Chassis
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rotation = gamepad1.right_stick_x;
        chassis.drive(y, x, rotation);

        telemetry.update();
    }
}
