package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Util.Config.ConfigMenu;
import org.firstinspires.ftc.teamcode.Util.Config.Options;
import org.firstinspires.ftc.teamcode.Util.Config.OptionsManager;
import org.firstinspires.ftc.teamcode.Util.Gamepad.GamepadManager;

@TeleOp(name = "Robot Config", group = "Config")
public class ConfigMode extends LinearOpMode {

    @Override
    public void runOpMode() {
        GamepadManager pad = new GamepadManager(gamepad1);
        OptionsManager manger = new OptionsManager(hardwareMap.appContext);
        ConfigMenu menu = new ConfigMenu(telemetry, pad, manger, Options.class);

        while (!isStopRequested()) {
            menu.update();
            menu.draw();
            telemetry.update();
            pad.update();

            sleep(20);
        }
    }
}