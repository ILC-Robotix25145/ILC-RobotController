package org.firstinspires.ftc.teamcode.Util;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamepadManager {

    private final Gamepad gamepad;
    private final Gamepad prevGamepad;
    private static final double triggerDeadZone = 0.3;
    private double stickDeadzone = 0.15;

    public GamepadManager(Gamepad gamepad) {
        this.gamepad = gamepad;
        this.prevGamepad = new Gamepad();
    }

    public boolean isDown(GamepadInput input) {
        return checkDown(this.gamepad, input);
    }
    public double getStickPos(GamepadInput stick){
        double value = 0;

        switch (stick){
            case LeftStickX:
                value = gamepad.left_stick_x;
                break;
            case LeftStickY:
                value = gamepad.left_stick_y;
                break;
            case RightStickX:
                value = gamepad.right_stick_x;
                break;
            case RightStickY:
                value = gamepad.right_stick_y;
                break;
            default:
                return 0;
        }

        if (abs(value) >= stickDeadzone) {
            return value;
        }
        else {
            return 0;
        }
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
                return gamepad.left_trigger > triggerDeadZone;
            case RightTrigger:
                return gamepad.right_trigger > triggerDeadZone;
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
