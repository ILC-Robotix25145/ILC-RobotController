package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot.Robot;
import org.firstinspires.ftc.teamcode.Util.Gamepad.GamepadInput;
import org.firstinspires.ftc.teamcode.Util.Gamepad.GamepadManager;
import org.firstinspires.ftc.teamcode.Util.Logger;

@TeleOp(name = "Main TeleOp", group = "Kickoff")
public class MainTeleOp extends OpMode {
    // Utils
    Logger logger;
    Robot robot;

    GamepadManager gm1;


    @Override
    public void init() {
        logger = new Logger(telemetry);
        robot = new Robot(hardwareMap, logger);
        robot.init();
        gm1 = new GamepadManager(gamepad1);
    }

    @Override
    public void loop() {
        if (gm1.isJustDown(GamepadInput.LeftBumper))
            robot.intake.toggle();

        if (robot.chassis.isAvailable()) {
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double rotation = gamepad1.right_stick_x;
            logger.debug(
                    "Chassis: y=%.2f x=%.2f rotation=%.2f",
                    y,
                    x,
                    rotation
            );
            robot.chassis.drive(y, x, rotation);
        }

        robot.loop();
        telemetry.update();
    }

    @Override
    public void stop() {
        robot.stop();
    }
}
