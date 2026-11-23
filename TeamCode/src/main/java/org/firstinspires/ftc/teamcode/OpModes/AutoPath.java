
package org.firstinspires.ftc.teamcode.OpModes;

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

@Autonomous(name = "AutoPath", group = "Autonomous")
public class AutoPath extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56.2, 8.9, 90);

    private final Pose point2Start = poseFactory.of(56.2, 8.9, 90);
    private final Pose point2 = poseFactory.of(35.4284, 24.236, 180);
    private final Pose point2Control1 = poseFactory.of(50.1925, 25.1314, 0);

    private final Pose point3Start = poseFactory.of(35.4284, 24.236, 180);
    private final Pose point3 = poseFactory.of(22.1819, 46.4971, -90);

    private final Pose point4 = poseFactory.of(71.0761, 127.1097, -90);
    private final Pose point4Control1 = poseFactory.of(36.3276, 38, 0);
    private final Pose point4Control2 = poseFactory.of(0, 134.6681, 0);
    private final Pose point4Control3 = poseFactory.of(61.9671, 108.1173, 0);

    private final Pose point4Segment1Start = poseFactory.of(71.0761, 127.1097, -90);
    private final Pose point4Segment1End = poseFactory.of(71.0761, 127.1097, -90);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, path2()),
                follow(follower, path3()),
                follow(follower, path4())
        );
    }

    @Override
    public void runOpMode() {

        Scheduler.reset();

        follower = Constants.create(hardwareMap);

        if (follower == null) {
            telemetry.addLine("ERROR: Follower is NULL!");
            telemetry.update();
            sleep(5000);
            return;
        }

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
                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }

    public Path path2() {
        return Paths.curve(point2Start, point2Control1, point2)
                .linear(point2Start, point2);
    }

    public Path path3() {
        return Paths.line(point3Start, point3)
                .linear(point3Start, point3);
    }

    public Path path4() {
        return Paths.curve(point3, point4Control1, point4Control2, point4Control3, point4)
                .heading(Interpolator.piecewise().until(1,
                        Interpolator.linear(point4Segment1Start, point4Segment1End)));
    }
}
