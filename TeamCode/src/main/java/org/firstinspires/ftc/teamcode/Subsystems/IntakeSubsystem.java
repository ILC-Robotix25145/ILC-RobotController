package org.firstinspires.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Util.Algebra;
import org.firstinspires.ftc.teamcode.Util.Logger;

public class IntakeSubsystem extends Subsystem {
    public static double DESIRED_POWER = 1.0;

    private final DcMotor intakeMotor;
    private double power = 0.0;

    public IntakeSubsystem(HardwareMap hardwareMap, Logger logger) {
        super(hardwareMap, logger);
        logger.infoLine("Initializing Intake subsystem");
        intakeMotor = getHardware(DcMotor.class, "intake");

    }

    @Override
    public void onInit() {
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotor.setPower(0);
    }

    @Override
    public void onLoop() {
        intakeMotor.setPower(power);
    }

    @Override
    public void onStop() {
        power = 0.0;
        intakeMotor.setPower(0);
    }

    public void toggle() {
        if (!isAvailable())
            return;

        setPower(power != 0.0 ? 0.0 : DESIRED_POWER);
    }

    public void adjustPower(double increment) {
        setPower(power + increment);
    }
    public double getPower() {
        return power;
    }
    public void setPower(double newPower) {
        power = Algebra.clamp(-1.0, 1.0, newPower);
    }
}
