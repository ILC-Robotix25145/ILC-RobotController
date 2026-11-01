package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.OpModes.Testing.Turret;
import org.firstinspires.ftc.teamcode.Subsystems.ChassisSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.Util.Config.ConfigMenu;
import org.firstinspires.ftc.teamcode.Util.Config.Options;
import org.firstinspires.ftc.teamcode.Util.Config.OptionsManager;
import org.firstinspires.ftc.teamcode.Util.Gamepad.GamepadManager;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "Robot Config", group = "Config")
public class ConfigMode extends LinearOpMode {

    @Override
    public void runOpMode() {
        GamepadManager pad = new GamepadManager(gamepad1);

        List<Class<?>> configClasses = new ArrayList<>();
        configClasses.add(ChassisSubsystem.class);
        configClasses.add(IntakeSubsystem.class);
        configClasses.add(TurretSubsystem.class);

        OptionsManager manger = new OptionsManager(hardwareMap.appContext, configClasses);
        ConfigMenu menu = new ConfigMenu(telemetry, pad, manger, configClasses);

        while (!isStopRequested()) {
            menu.update();
            menu.draw();
            telemetry.update();
            pad.update();

            sleep(20);
        }
    }
}