package org.firstinspires.ftc.teamcode.Util;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Logger {
    public enum Level {
        NONE,
        ERROR,
        WARN,
        INFO,
        DEBUG
    }
    public Level level;

    private final Telemetry telemetry;

    public Logger(Telemetry telemetry) {
        this(telemetry, Level.INFO);
    }
    public Logger(Telemetry telemetry, Level level) {
        this.telemetry = telemetry;
        this.level = level;
    }

    public void error(String message, Object... args) {
        log(Level.ERROR, "ERROR", message, args);
    }
    public void warn(String message, Object... args) {
        log(Level.WARN, "WARN", message, args);
    }
    public void info(String message, Object... args) {
        log(Level.INFO, "INFO", message, args);
    }
    public void debug(String message, Object... args) {
        log(Level.DEBUG, "DEBUG", message, args);
    }

    public void errorLine(String line) {
        logLine(Level.ERROR, line);
    }
    public void warnLine(String line) {
        logLine(Level.WARN, line);
    }
    public void infoLine(String line) {
        logLine(Level.INFO, line);
    }
    public void debugLine(String line) {
        logLine(Level.DEBUG, line);
    }

    private void log(Level messageLevel, String tag, String message, Object... args) {
        if (!shouldLog(messageLevel))
            return;

        telemetry.addData(tag, message, args);
    }
    private void logLine(Level messageLevel, String line) {
        if (!shouldLog(messageLevel))
            return;

        telemetry.addLine(line);
    }

    private boolean shouldLog(Level messageLevel) {
        return level != Level.NONE
                && messageLevel.ordinal() <= level.ordinal();
    }
}
