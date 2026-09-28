package org.firstinspires.ftc.teamcode.Util;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamepadManager {

    private final Gamepad gamepad;
    private final Gamepad prevGamepad;

    public GamepadManager(Gamepad gamepad) {
        this.gamepad = gamepad;
        this.prevGamepad = new Gamepad();
    }

    public boolean isDown(GamepadInput input) {
        return checkDown(this.gamepad, input);
    }

    public boolean isJustDown(GamepadInput input) {
        return isDown(input) && !wasDown(input);
    }

    public void update() {
        prevGamepad.copy(gamepad);
    }

    private boolean wasDown(GamepadInput input) {
        return checkDown(this.prevGamepad, input);
    }

    private static boolean checkDown(Gamepad gamepad, GamepadInput input) {
        switch (input) {
            case A:
                return gamepad.a;
            case B:
                return gamepad.b;
            case X:
                return gamepad.x;
            case Y:
                return gamepad.y;
            case LeftBumper:
                return gamepad.left_bumper;
            case RightBumper:
                return gamepad.right_bumper;
            case LeftTrigger:
                return gamepad.left_trigger > 0.3;
            case RightTrigger:
                return gamepad.right_trigger > 0.3;
            case DpadUp:
                return gamepad.dpad_up;
            case DpadDown:
                return gamepad.dpad_down;
            case DpadLeft:
                return gamepad.dpad_left;
            case DpadRight:
                return gamepad.dpad_right;
            case Start:
                return gamepad.start;
            case Back:
                return gamepad.back;
            case LeftStickButton:
                return gamepad.left_stick_button;
            case RightStickButton:
                return gamepad.right_stick_button;
            default:
                return false;
        }
    }
}
