package org.firstinspires.ftc.teamcode.Util.Config;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
public class ConfigRow {
    private final Field field;
    private final String label;
    private final double min;
    private final double max;
    private final double step;

    public ConfigRow(Field field) {
        int mods = field.getModifiers();
        if (!Modifier.isPublic(mods) || !Modifier.isStatic(mods) || Modifier.isFinal(mods)) {
            throw new IllegalArgumentException(
                    field.getName() + " must be public static and not final");
        }

        Class<?> type = field.getType();
        boolean supported = type == boolean.class
                || type == int.class
                || type == double.class
                || type.isEnum();
        if (!supported) {
            throw new IllegalArgumentException(field.getName()
                    + " has unsupported type " + type.getSimpleName()
                    + " (use boolean, int, double or an enum)");
        }

        this.field = field;
        this.label = makeLabel(field.getName());

        Range range = field.getAnnotation(Range.class);
        if (range != null) {
            if (range.min() > range.max()) {
                throw new IllegalArgumentException(field.getName() + ": @Range min is bigger than max");
            }
            if (range.step() <= 0) {
                throw new IllegalArgumentException(field.getName() + ": @Range step must be above 0");
            }
            this.min = range.min();
            this.max = range.max();
            this.step = range.step();
        } else {
            this.min = 0;
            this.max = 100;
            this.step = 1;
        }
    }

    public String getLabel() {
        return label;
    }

    public void next() {
        change(+1);
    }
    public void prev() {
        change(-1);
    }

    public String display() {
        String value;

        try {
            if (field.getType() == boolean.class) {
                value =  field.getBoolean(null) ? "(X)" : "(-)";
            }
            value =  String.valueOf(field.get(null));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (OptionsManager.checkSave(field)){
            value = value + " (SAVED)";
        }
        else {
            value = value + " (UNSAVED)";
        }

        return value;
    }

    private void change(int direction) {
        try {
            if (field.getType() == boolean.class) {
                field.setBoolean(null, !field.getBoolean(null));

            } else if (field.getType() == int.class) {
                double next = field.getInt(null) + direction * step;
                field.setInt(null, (int) Math.round(clamp(next)));

            } else if (field.getType() == double.class) {
                BigDecimal sum = BigDecimal.valueOf(field.getDouble(null))
                        .add(BigDecimal.valueOf(step).multiply(BigDecimal.valueOf(direction)));
                field.setDouble(null, clamp(sum.doubleValue()));

            } else {
                Object[] values = field.getType().getEnumConstants();
                int current = ((Enum<?>) field.get(null)).ordinal();
                int nextIndex = (current + direction + values.length) % values.length;
                field.set(null, values[nextIndex]);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private double clamp(double value) {
        return Math.max(min, Math.min(max, value));
    }

    private static String makeLabel(String name) {
        String spaced = name.replaceAll("([a-z0-9])([A-Z])", "$1 $2");
        return Character.toUpperCase(spaced.charAt(0)) + spaced.substring(1);
    }
}