package org.firstinspires.ftc.teamcode.Util;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Hardware {
    private final HardwareMap hardwareMap;
    private final Logger logger;

    public Hardware(HardwareMap hardwareMap, Logger logger) {
        this.hardwareMap = hardwareMap;
        this.logger = logger;
        logger.infoLine("Initializing Hardware");
    }

    public <T> T getRequired(Class<? extends T> type, String name) {
        T device = getOptional(type, name);

        if (device == null) {
            logger.error(
                    "Hardware",
                    "Required hardware missing: %s (%s)",
                    name,
                    type.getSimpleName()
            );

            throw new IllegalStateException(
                    "Required hardware not found: " + name
            );
        }

        return device;
    }

    public <T> T getOptional(Class<? extends T> type, String name) {
        T device = hardwareMap.tryGet(type, name);

        if (device != null) {
            logger.debug(
                    "Hardware",
                    "%s -> %s",
                    name,
                    type.getSimpleName()
            );
        } else {
            logger.debug(
                    "Hardware",
                    "%s -> not found",
                    name
            );
        }

        return device;
    }
}
