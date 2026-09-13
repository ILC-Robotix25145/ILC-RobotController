package org.firstinspires.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Util.Hardware;
import org.firstinspires.ftc.teamcode.Util.Logger;

public class IntakeSubsystem extends Subsystem {
    static final double POWER = 1.0;
    private final DcMotor intakeMotor;
    private boolean running = false;

    public IntakeSubsystem(Hardware hardware, Logger logger) {
        super(hardware, logger);
        intakeMotor = hardware.getOptional(DcMotor.class, "intake");
    }

    @Override
    public void init() {
        if (intakeMotor == null) return;
        intakeMotor.setPower(0);
    }

    public void start() {
        if (intakeMotor == null) return;
        intakeMotor.setPower(POWER);
        running = true;
    }

    @Override
    public void stop() {
        if (intakeMotor == null) return;
        intakeMotor.setPower(0);
        running = false;
    }

    public void toggle() {
        if (intakeMotor == null) return;
        if (running)
            stop();
        else
            start();
    }

}
