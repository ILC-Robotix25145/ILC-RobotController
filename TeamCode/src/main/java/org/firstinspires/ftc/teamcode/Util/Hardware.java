package org.firstinspires.ftc.teamcode.Util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Hardware {
    // Flags
    public static final int NO_FAIL = 1 << 0;
    public static final int NO_LOG = 1 << 1;

    private int flags = 0;
    private Telemetry telemetry;
    private HardwareMap hardwareMap;

    // Chasis Subsystem
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;


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

    public void initSubsystemIntake() {
        if (((flags & NO_LOG) == 0)) {
            telemetry.addLine("Initializing Intake");
        }
    }

    public void initSubsystemChassis() {
        if ((flags & NO_LOG) == 0) {
            telemetry.addLine("Initializing Chassis");
        }

        try {
            frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
            frontRight = hardwareMap.get(DcMotor.class, "frontRight");
            backLeft = hardwareMap.get(DcMotor.class, "backLeft");
            backRight = hardwareMap.get(DcMotor.class, "backRight");
        } catch (Exception e) {
            if ((flags & NO_FAIL) == 0)
                throw e;

            if ((flags & NO_LOG) == 0) {
                telemetry.addData("Caught exception", "msg=\"%s\"", e.getMessage());
            }
        }
    }
}
