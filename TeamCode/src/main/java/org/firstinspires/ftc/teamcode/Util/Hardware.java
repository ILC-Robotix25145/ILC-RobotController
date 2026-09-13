package org.firstinspires.ftc.teamcode.Util;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Hardware {
    // Flags
    public static final int NO_FAIL = 1 << 0;
    public static final int NO_LOG = 1 << 1;
    public static final int DEFAULT_FLAGS = 0;

    private int flags = 0;
    private Telemetry telemetry;
    private HardwareMap hardwareMap;


    public void init(int flags, Telemetry telemetry, HardwareMap hardwareMap) {
        this.flags = flags;
        this.telemetry = telemetry;
        this.hardwareMap = hardwareMap;

        if ((flags & NO_LOG) == 0) {
            telemetry.addLine("Initializing Hardware");
            telemetry.addData(
                    "Flags",
                    "NO_FAIL=%b, NO_LOG=%b",
                    (flags & NO_FAIL) != 0,
                    (flags & NO_LOG) != 0
            );
        }
    }

    public <T> T get(Class<? extends T> type, String name) {
        try {
            T device = hardwareMap.get(type, name);
            if (!hasFlag(NO_LOG))
                telemetry.addData("Hardware", "%s -> %s", name, type.getSimpleName());
            return device;
        } catch (Exception e) {
            if (!hasFlag(NO_LOG))
                telemetry.addData(
                        "Hardware Error",
                        "%s (%s): %s",
                        name,
                        type.getSimpleName(),
                        e.getMessage()
                );

            if (hasFlag(NO_FAIL))
                return null;
            throw e;
        }
    }

    private boolean hasFlag(int flag) {
        return (flags & flag) != 0;
    }
}
