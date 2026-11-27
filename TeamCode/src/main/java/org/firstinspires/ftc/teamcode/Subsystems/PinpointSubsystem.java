package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Util.Logger;

public class PinpointSubsystem extends Subsystem {
    GoBildaPinpointDriver pinpoint;

    public PinpointSubsystem(HardwareMap hardwareMap, Logger logger) {
        super(hardwareMap, logger, Requirement.OPTIONAL);
    }

    @Override
    public void onInit() {
        pinpoint = getHardware(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setOffsets(1.1396607076089214, 2.3961534274844674, DistanceUnit.INCH);
        pinpoint.resetPosAndIMU();
    }

    @Override
    public void onLoop() {
        pinpoint.update();
    }

    public double getX() {
        if (!isAvailable())
            return 0.0;

        return pinpoint.getPosX(DistanceUnit.CM);
    }

    public double getY() {
        if (!isAvailable())
            return 0.0;

        return pinpoint.getPosY(DistanceUnit.CM);
    }

    public double getHeadingDegrees() {
        if (!isAvailable())
            return 0.0;

        return pinpoint.getHeading(AngleUnit.DEGREES);
    }
}
