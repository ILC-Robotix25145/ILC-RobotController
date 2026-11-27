
package org.firstinspires.ftc.teamcode.OpModes.Testing;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.pedropathing.paths.interpolator.Interpolator;

import org.firstinspires.ftc.teamcode.Pedro.Constants;

@Autonomous(name = "AutoPath2", group = "Test")
public class AutoPath2 extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(55.3545, 8.4675, 90);

    private final Pose point1 = poseFactory.of(22.1401, 34.4233, 135);
    private final Pose point1Control1 = poseFactory.of(50.1925, 25.1314, 0);

    private final Pose point1Segment1Start = poseFactory.of(22.1401, 34.4233, 90);
    private final Pose point1Segment1End = poseFactory.of(22.1401, 34.4233, 90);
    private final Pose point1Segment2Start = poseFactory.of(22.1401, 34.4233, 90);
    private final Pose point1Segment2End = poseFactory.of(22.1401, 34.4233, 135);

    private final Pose point2 = poseFactory.of(48.171, 108.0588, 135);
    private final Pose point2Control1 = poseFactory.of(14.6822, 36.39, 0);
    private final Pose point2Control2 = poseFactory.of(10.7641, 112.1009, 0);

    private final Pose point3 = poseFactory.of(83.3616, 108.048, 135);


    // FORWARD + REVERSE ROUTINE
    public Command autoRoutine() {
        return sequential(

                // NORMAL PATH
                follow(follower, path1()),
                follow(follower, path2()),
                follow(follower, path3()),

                // RETURN PATH
                follow(follower, reversePath3()),
                follow(follower, reversePath2()),
                follow(follower, reversePath1())

        );
    }


    @Override
    public void runOpMode() {

        Scheduler.reset();

        follower = Constants.create(hardwareMap);

        follower.setPose(start);
        follower.update();

        waitForStart();

        if (isStopRequested()) return;

        schedule(autoRoutine());

        while (opModeIsActive()) {

            follower.update();
            Scheduler.execute();

            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("heading", follower.pose().heading());

            if (follower.currentPath() != null) {
                telemetry.addData(
                        "Current path distance remaining",
                        follower.distanceToEndpoint()
                );

                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }


    // =========================
    // NORMAL PATHS
    // =========================

    public Path path1() {
        return Paths.curve(start, point1Control1, point1)
                .heading(
                        Interpolator.piecewise()
                                .until(0.1, Interpolator.linear(
                                        point1Segment1Start,
                                        point1Segment1End
                                ))
                                .until(1, Interpolator.linear(
                                        point1Segment2Start,
                                        point1Segment2End
                                ))
                );
    }

    public Path path2() {
        return Paths.curve(
                point1,
                point2Control1,
                point2Control2,
                point2
        ).constant(point2);
    }

    public Path path3() {
        return Paths.line(point2, point3).constant(point3);
    }


    // =========================
    // REVERSE PATHS
    // =========================

    // Point 3 -> Point 2
    public Path reversePath3() {
        return Paths.line(point3, point2).constant(point2);
    }

    // Point 2 -> Point 1
    public Path reversePath2() {
        return Paths.curve(
                point2,
                point2Control2,
                point2Control1,
                point1
        ).constant(point1);
    }

    // Point 1 -> Start
    public Path reversePath1() {
        return Paths.curve(
                point1,
                point1Control1,
                start
        ).heading(
                Interpolator.piecewise()
                        .until(0.1, Interpolator.linear(
                                point1Segment2End,
                                point1Segment2Start
                        ))
                        .until(1, Interpolator.linear(
                                point1Segment1End,
                                point1Segment1Start
                        ))
        );
    }
}
