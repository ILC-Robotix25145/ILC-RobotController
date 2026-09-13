package org.firstinspires.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Util.Hardware;

public class IntakeSubsystem extends Subsystem {
    static final double POWER = 1.0;

    private DcMotor intakeMotor;
    private Gamepad gamepad;

    public IntakeSubsystem(Hardware hardware) {
        super(hardware);
    }

    @Override
    public void init(Gamepad gamepad) {
        this.intakeMotor = hardware.get(DcMotor.class, "intakeMotor");
        this.gamepad = gamepad;
    }

    @Override
    public void update() {
        double power = (gamepad.right_bumper ? 1 : 0)
                     - (gamepad.left_bumper ? 1 : 0) * POWER;
        intakeMotor.setPower(power);
    }
}
