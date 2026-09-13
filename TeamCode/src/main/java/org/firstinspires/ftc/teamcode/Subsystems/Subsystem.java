package org.firstinspires.ftc.teamcode.Subsystems;
import org.firstinspires.ftc.teamcode.Util.Hardware;
import org.firstinspires.ftc.teamcode.Util.Logger;

public abstract class Subsystem {
    protected Hardware hardware;

    public Subsystem(Hardware hardware, Logger logger) {
        this.hardware = hardware;
    }

    public void init() {}
    public void update() {}
    public void stop() {}
}
