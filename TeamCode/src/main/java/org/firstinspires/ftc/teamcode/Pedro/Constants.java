
package org.firstinspires.ftc.teamcode.Pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("frontLeft");
        c.frontRightName.set("frontRight");
        c.backLeftName.set("backLeft");
        c.backRightName.set("backRight");

        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-1.4625107772707002);
        c.yPodOffset.set(-3.292679962680096E-5);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.2867508051835807);
                Controller secondaryTranslationalForward = Controller.proportional(0.10594676039270322);
                Controller primaryTranslationalLateral = Controller.proportional(0.43405146324218896);
                Controller secondaryTranslationalLateral = Controller.proportional(0.16037041759928627);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.012137914501236278));
                c.brake.set(Controller.proportionalFeedforward(0.010317227326050836));

                c.headingFeedback.set(Controller.proportional(5.917401755411617));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.040732966323059644, 0.006683832011125302));

                c.linearBrakeCoefficients.set(Matrix.diag(-0.007633635066388698, 0.0830322958901607));
                c.quadraticBrakeCoefficients.set(Matrix.diag(5.228737827856739E-4, 0.001171134432172158));

                c.maxAchievableForwardVelocity.set(68.34317963335472);
                c.maxAchievableStrafeVelocity.set(57.062276927803055);
                c.naturalForwardDeceleration.set(34.51699180974196);
                c.naturalStrafeDeceleration.set(54.57598508118529);
            }
    );

    public static Follower create(HardwareMap h) {

        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );

    }
}
