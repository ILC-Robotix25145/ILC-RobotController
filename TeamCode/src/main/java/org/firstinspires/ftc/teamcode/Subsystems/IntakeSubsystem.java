package org.firstinspires.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Util.Logger;

public class IntakeSubsystem extends Subsystem {
    static final double POWER = 1.0;
    private final DcMotor intakeMotor;
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

    public void toggle() {
        if (!running) {
            intakeMotor.setPower(POWER);
            running = true;
        } else {
            intakeMotor.setPower(0);
            running = false;
        }
    }

}
