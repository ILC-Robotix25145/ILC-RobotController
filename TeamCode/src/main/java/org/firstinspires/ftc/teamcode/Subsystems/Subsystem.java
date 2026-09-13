package org.firstinspires.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Util.Hardware;

public abstract class Subsystem {
    protected Hardware hardware;

    public Subsystem(Hardware hardware) {
        this.hardware = hardware;
    }

    public abstract void init(Gamepad gamepad);
    public abstract void update();
}
