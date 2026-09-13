package org.firstinspires.ftc.teamcode.Util;

public final class RobotConfig {
    private RobotConfig() {}

    public enum TeamColor {
        RED,
        BLUE
    }

    public enum SoftwareType {
        COMPETITION,
        PRACTICE,
        DEBUG
    }

    public static final int TEAM_NUMBER = 25145;
    public static final String TEAM_NAME = "ILC Robotix";
    public static TeamColor TEAM_COLOR = TeamColor.BLUE;

    public static final String SOFTWARE_VERSION = "0.1.0";
    public static SoftwareType SOFTWARE_TYPE = SoftwareType.DEBUG;

    public static String description() {
        return String.format(
                "FTC Team %d | %s | %s Alliance | %s Build | v%s",
                TEAM_NUMBER,
                TEAM_NAME,
                TEAM_COLOR,
                SOFTWARE_TYPE,
                SOFTWARE_VERSION
        );
    }
}
