package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Util.Gamepad.GamepadInput;

@TeleOp(name = "Main TeleOp", group = "Kickoff")
public class MainTeleOp extends CustomOpMode {
    @Override
    public void onLoop() {
        // Intake
        if (gm1.isJustDown(GamepadInput.LeftBumper))
            robot.intake.toggle();

        // Movement
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rotation = gamepad1.right_stick_x;
        robot.chassis.drive(y, x, rotation);
        logger.debug(
                "Chassis: y=%.2f x=%.2f rotation=%.2f",
                y, x, rotation
        );

        // Turret / Hood
        double hoodAdjustment = -1 * gm1.getStickPos(GamepadInput.RightStickY);
        robot.turret.adjustHood(hoodAdjustment);
        logger.debug("Turret: speed=%.2f",
                     robot.turret.getSpeed());

        // Shooter Flywheel
        double flywheelPower = gamepad1.right_trigger;
        robot.shooter.setFlywheelPower(flywheelPower);
        logger.debug("Shooter: flywheel=%.2f",
                robot.shooter.getFlywheelPower());
    }
}
