package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Util.GamepadInput;
import org.firstinspires.ftc.teamcode.Util.GamepadManager;
import org.firstinspires.ftc.teamcode.Util.Logger;

@TeleOp(name = "Gamepad", group = "Test")
public class Gamepad extends OpMode {

    Logger logger;
    GamepadManager gamepad;

    @Override
    public void init() {
        logger = new Logger(telemetry);
        telemetry.update();
        gamepad = new GamepadManager(gamepad1);
    }

    @Override
    public void loop() {
        for (GamepadInput input : GamepadInput.values()) {

            if (gamepad.isDown(input)){
                logger.infoLine(String.format("%s pressed", input.name()));
            }
            double leftStickX = gamepad.getStickPos(GamepadInput.LeftStickX);
            double leftStickY = gamepad.getStickPos(GamepadInput.LeftStickY);
            double rightStickX = gamepad.getStickPos(GamepadInput.RightStickX);
            double rightStickY = gamepad.getStickPos(GamepadInput.RightStickY);

            if (leftStickY != 0 || leftStickX != 0){
                logger.infoLine(String.format("LeftStick (%f, %f)", leftStickX, leftStickY));
            }
            if (rightStickY != 0 || rightStickX != 0){
                logger.infoLine(String.format("RightStick (%f, %f)", rightStickX, rightStickY));
            }

            telemetry.update();
        }
    }
}