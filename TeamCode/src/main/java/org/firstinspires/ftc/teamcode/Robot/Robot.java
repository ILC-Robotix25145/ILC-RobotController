package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Subsystems.ChassisSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.Subsystem;
import org.firstinspires.ftc.teamcode.Subsystems.TurretSubsystem;
import org.firstinspires.ftc.teamcode.Util.Logger;

import java.util.Arrays;
import java.util.List;

public class Robot {
    public final ChassisSubsystem chassis;
    public final IntakeSubsystem intake;
    public final TurretSubsystem turret;

    private final List<Subsystem> subsystems;

    public Robot(HardwareMap hardwareMap, Logger logger) {
        chassis = new ChassisSubsystem(hardwareMap, logger);
        intake = new IntakeSubsystem(hardwareMap, logger);
        turret = new TurretSubsystem(hardwareMap, logger);

        subsystems = Arrays.asList(
                chassis,
                intake,
                turret
        );
    }

    public void init() {
        for (Subsystem s : subsystems)
            s.init();
    }

    public void start() {
        for (Subsystem s : subsystems)
            s.start();
    }

    public void loop() {
        for (Subsystem s: subsystems)
            s.loop();
    }

    public void stop() {
        for (Subsystem s : subsystems)
            s.stop();
    }
}
