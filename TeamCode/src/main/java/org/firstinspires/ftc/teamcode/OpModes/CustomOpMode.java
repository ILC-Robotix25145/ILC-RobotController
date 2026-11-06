package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robot.Robot;
import org.firstinspires.ftc.teamcode.Util.Gamepad.GamepadManager;
import org.firstinspires.ftc.teamcode.Util.Logger;

public abstract  class CustomOpMode extends OpMode {
    protected Logger logger;
    protected Robot robot;

    protected GamepadManager gm1;
    protected GamepadManager gm2;

    @Override
    public final void init() {
        logger = new Logger(telemetry);

        gm1 = new GamepadManager(gamepad1);
        gm2 = new GamepadManager(gamepad2);

        robot = new Robot(hardwareMap, logger);
        robot.init();

        onInit();
    }

    @Override
    public final void start() {
        robot.start();
        onStart();
    }

    @Override
    public final void loop() {
        gm1.update();
        gm2.update();

        onLoop();

        robot.loop();

        telemetry.update();
    }

    @Override
    public final void stop() {
        robot.stop();
        onStop();
    }

    protected void onInit() {}
    protected void onStart() {}
    protected abstract void onLoop();
    protected void onStop() {}
}
