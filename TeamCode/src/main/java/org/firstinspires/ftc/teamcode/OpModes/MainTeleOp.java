package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Util.Algebra;
import org.firstinspires.ftc.teamcode.Util.Gamepad.GamepadInput;

@TeleOp(name = "Main TeleOp")
public class MainTeleOp extends CustomOpMode {
    boolean testingMode = false;
    boolean slowMode = false;

    @Override
    public void onLoop() {
        if (gm1.isJustDown(GamepadInput.LeftBumper))
            robot.intake.toggle();
        logger.debug("Intake: power=%.2f DESIRED_POWER=%.2f", robot.intake.getPower(), IntakeSubsystem.DESIRED_POWER);

        if (gm1.isJustDown(GamepadInput.LeftStickButton))
            slowMode = !slowMode;
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rotation = gamepad1.right_stick_x;
        double speedMultiplier = slowMode ? 0.3 : 1.0;
        robot.chassis.drive(y, x, rotation, speedMultiplier);
        logger.debug(
                "Chassis: y=%.2f x=%.2f rotation=%.2f",
                y, x, rotation
        );

        int hoodDir = gm1.getDirectionDown(GamepadInput.DpadUp, GamepadInput.DpadDown);
        robot.turret.adjustHood(0.001 * hoodDir);
        logger.debug(
                "Turret: speed=%.2f, hood=%.4f, velocity=%.2f ticks/s (%.0f RPM)",
                robot.turret.getSpeed(),
                robot.turret.getHoodPosition(),
                robot.turret.getFlywheelVelocity(),
                robot.turret.getFlywheelRpm()
        );

        double turretDir = gm2.getStickPos(GamepadInput.LeftStickX);
        robot.turret.setRotationSpeed(0.5 * turretDir);

        logger.debug(
                "Pinpoint: X=%.2f cm, Y=%.2f cm, Heading=%.2f°",
                robot.pinpoint.getX(),
                robot.pinpoint.getY(),
                robot.pinpoint.getHeadingDegrees()
        );

        if (gm1.isJustDown(GamepadInput.Back))
            testingMode = !testingMode;
        if (testingMode) {
            logger.infoLine("==== Testing Mode ====");

            int speedDir = gm1.getDirectionJustDown(GamepadInput.DpadRight, GamepadInput.DpadLeft);
            robot.turret.adjustSpeed(speedDir * 0.02);

            int intakeDir = gm1.getDirectionJustDown(GamepadInput.RightTrigger, GamepadInput.LeftTrigger);
            IntakeSubsystem.DESIRED_POWER = Algebra.clamp(-1.0, 1.0, IntakeSubsystem.DESIRED_POWER + intakeDir * 0.1);
        }
    }
}