package org.firstinspires.ftc.teamcode.Util.Config;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Util.Gamepad.GamepadInput;
import org.firstinspires.ftc.teamcode.Util.Gamepad.GamepadManager;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ConfigMenu {

    private final Telemetry telemetry;
    private final GamepadManager pad;
    private final OptionsManager manager;
    private final List<ConfigRow> rows = new ArrayList<>();
    private int selected = 0;

    public ConfigMenu(Telemetry telemetry, GamepadManager pad, OptionsManager manager, Class<?> settingsClass) {
        this.telemetry = telemetry;
        this.pad = pad;
        this.manager = manager;
        manager.loadAll();

        for (Field field : settingsClass.getDeclaredFields()) {
            if (isValidField(field)){
                rows.add(new ConfigRow(field));
            }
        }

        if (rows.isEmpty()) {
            throw new IllegalArgumentException(settingsClass.getSimpleName()
                    + " has no public static non-final variables to show");
        }
    }

    public static boolean isValidField(Field field){
        int mods = field.getModifiers();

        if (field.isSynthetic()) return false;
        if (!Modifier.isPublic(mods)) return false;
        if (!Modifier.isStatic(mods))  return false;
        if (Modifier.isFinal(mods)) return false;

        return true;
    }

    public void update() {
        int count = rows.size();

        if (pad.isJustDown(GamepadInput.DpadDown)) {
            selected = (selected + 1) % count;
        }
        if (pad.isJustDown(GamepadInput.DpadUp)) {
            selected = (selected - 1 + count) % count;
        }
        if (pad.isJustDown(GamepadInput.A)){
            manager.saveAll();
        }

        ConfigRow row = rows.get(selected);
        if (pad.isJustDown(GamepadInput.DpadRight)) {
            row.next();
        }
        if (pad.isJustDown(GamepadInput.DpadLeft)) {
            row.prev();
        }
    }

    public void draw() {
        telemetry.addLine("=== Robot Config ===");

        for (int i = 0; i < rows.size(); i++) {
            ConfigRow row = rows.get(i);
            String marker = (i == selected) ? "> " : "  ";
            telemetry.addLine(marker + row.getLabel() + ": " + row.display());
        }

        telemetry.addLine("D-pad: up/down = select, left/right = change, A = save config");
    }
}