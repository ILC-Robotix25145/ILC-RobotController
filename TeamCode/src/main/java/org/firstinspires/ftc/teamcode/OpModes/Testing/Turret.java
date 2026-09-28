package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.Util.GamepadInput;
import org.firstinspires.ftc.teamcode.Util.GamepadManager;
import org.firstinspires.ftc.teamcode.Util.Logger;

@TeleOp(name = "Turret", group = "Test")
public class Turret extends OpMode {
    Logger logger;
    TurretSubsystem turretSubsystem;
    GamepadManager gamepad;

    @Override
    public void init() {
        logger = new Logger(telemetry);
        gamepad = new GamepadManager(gamepad1);

        logger.infoLine("Initializing subsystems");
        turretSubsystem = new TurretSubsystem(hardwareMap, logger);
        turretSubsystem.init();

        telemetry.update();
    }

    @Override
    public void loop() {
        if (turretSubsystem.isAvailable()) {
            if (gamepad.isJustDown(GamepadInput.LeftBumper))
                turretSubsystem.speed -= 0.1;
            if (gamepad.isJustDown(GamepadInput.RightBumper))
                turretSubsystem.speed += 0.1;
            turretSubsystem.speed = Math.max(-1.0, Math.min(1.0, turretSubsystem.speed));

            double hoodAdjustment = -gamepad1.right_stick_y;
            turretSubsystem.adjustHood(hoodAdjustment * 0.3);

            turretSubsystem.loop();
            logger.debug("speed=%.2f", turretSubsystem.speed);
        }

        gamepad.update();
        telemetry.update();
    }
}
