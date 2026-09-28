package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.Util.Logger;

public class Turret extends OpMode {
    Logger logger;
    TurretSubsystem turretSubsystem;

    // State
    boolean lastLeftBumper = false;
    boolean lastRightBumper = true;

    @Override
    public void init() {
        logger = new Logger(telemetry);

        logger.infoLine("Initializing subsystems");
        turretSubsystem = new TurretSubsystem(hardwareMap, logger);
        turretSubsystem.init();
    }

    @Override
    public void loop() {
        if (!turretSubsystem.isAvailable())
            return;

        boolean leftBumper = gamepad1.left_bumper;
        if (gamepad1.left_bumper && !lastLeftBumper)
            turretSubsystem.speed -= 0.1;
        lastLeftBumper = leftBumper;

        boolean rightBumper = gamepad1.right_bumper;
        if (gamepad1.right_bumper && !lastRightBumper)
            turretSubsystem.speed += 0.1;
        turretSubsystem.speed = Math.max(0.1, Math.min(1.0, turretSubsystem.speed));
        lastRightBumper = rightBumper;

        double hoodAdjustment = -gamepad1.left_stick_y;
        turretSubsystem.adjustHood(hoodAdjustment * 0.01);

        logger.debug("speed=%.2f", turretSubsystem.speed);
    }
}
