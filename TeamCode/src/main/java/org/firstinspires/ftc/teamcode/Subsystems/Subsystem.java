package org.firstinspires.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Util.Logger;

public abstract class Subsystem {
    public enum Requirement {
        OPTIONAL,
        REQUIRED
    }

    private final HardwareMap hardwareMap;
    protected final Logger logger;
    protected final Requirement requirement;

    private boolean available = true;

    public Subsystem(HardwareMap hardwareMap, Logger logger) {
        this.hardwareMap = hardwareMap;
        this.logger = logger;
        this.requirement = Requirement.OPTIONAL;
    }
    public Subsystem(HardwareMap hardwareMap, Logger logger, Requirement requirement) {
        this.hardwareMap = hardwareMap;
        this.logger = logger;
        this.requirement = requirement;
    }

    protected <T> T getHardware(Class<? extends T> type, String name) {
        T device = hardwareMap.tryGet(type, name);

        if (device != null) {
            logger.debug(
                    "%s -> %s",
                    name,
                    type.getSimpleName()
            );
            return device;
        }

        if (requirement == Requirement.REQUIRED) {
            logger.error(
                    "Required hardware missing: %s (%s)",
                    name,
                    type.getSimpleName()
            );

            throw new IllegalStateException("Required hardware not found: " + name);
        }

        available = false;
        logger.debug(
                "Optional hardware missing: %s (%s)",
                name,
                type.getSimpleName()
        );

        return null;
    }

    public final void init() {
        if (!available)
            return;
        onInit();
    }

    public final void loop() {
        if (!available)
            return;
        onLoop();
    }

    public final void stop() {
        if (!available)
            return;
        onStop();
    }

    public final boolean isAvailable() {
        return this.available;
    }

    protected void onInit() {}
    protected void onLoop() {}
    protected void onStop() {}
}
