package org.firstinspires.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Util.Logger;

public class IntakeSubsystem extends Subsystem {
    static final double POWER = 1.0;

    private final DcMotor intakeMotor;
    private double power = 0.0;
    private boolean running = false;

    public IntakeSubsystem(HardwareMap hardwareMap, Logger logger) {
        super(hardwareMap, logger);
        logger.infoLine("Initializing Intake subsystem");
        intakeMotor = getHardware(DcMotor.class, "intake");
    }

    @Override
    public void onInit() {
        intakeMotor.setPower(0);
    }

    @Override
    public void onLoop() {
        intakeMotor.setPower(power);
    }

    @Override
    public void onStop() {
        intakeMotor.setPower(0);
    }

    public void toggle() {
        if (!isAvailable())
            return;

        if (!running) {
            power = 1.0;
            running = true;
        } else {
            power = 0.0;
            running = false;
        }
    }

}
