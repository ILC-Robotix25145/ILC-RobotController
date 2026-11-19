package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.Util.Gamepad.GamepadManager;
import org.firstinspires.ftc.teamcode.Util.Logger;

@TeleOp(name = "Shooter", group = "Test")
public class Shooter extends OpMode {
    Logger logger;
    ShooterSubsystem shooterSubsystem;
    GamepadManager gamepad;

    @Override
    public void init() {
        logger = new Logger(telemetry);
        gamepad = new GamepadManager(gamepad1);

        logger.infoLine("Initializing Shooter Subsystem");
        shooterSubsystem = new ShooterSubsystem(hardwareMap, logger);
        shooterSubsystem.init();

        telemetry.update();
    }

    @Override
    public void loop() {
        if (shooterSubsystem.isAvailable()) {
            double flywheelPower = gamepad1.right_trigger;
            shooterSubsystem.setFlywheelPower(flywheelPower);

            shooterSubsystem.loop();
            logger.debug("flywheel=%.2f", shooterSubsystem.getFlywheelPower());
        }

        gamepad.update();
        telemetry.update();
    }
}
