package org.firstinspires.ftc.teamcode.Util.Config;

import android.content.Context;
import android.content.SharedPreferences;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class OptionsManager {
    private static final String PREFS_NAME = "FTC_Options";
    private static SharedPreferences prefs;

    public OptionsManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static boolean checkSave(Field field) {
        String fieldName = field.getName();

        try {
            field.setAccessible(true);

            if (field.getType() == double.class) {
                String valueStr = prefs.getString(fieldName, "0.0");
                double valueDouble = Double.parseDouble(valueStr);
                return valueDouble == field.getDouble(null);
            }
            else if (field.getType() == int.class) {
                int value = prefs.getInt(fieldName, 0);
                return value == field.getInt(null);
            }
            else if (field.getType() == boolean.class) {
                boolean value = prefs.getBoolean(fieldName, false);
                return value == field.getBoolean(null);
            }
            else if (field.getType().isEnum()) {
                String value = prefs.getString(fieldName, null);
                if (value != null) {
                    Object enumValue = Enum.valueOf((Class<Enum>) field.getType(), value);
                    return enumValue.equals(field.get(null));
                }
            }

        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public void saveAll() {
        SharedPreferences.Editor editor = prefs.edit();

        Field[] fields = Options.class.getDeclaredFields();
        for (Field field : fields) {

            if (!ConfigMenu.isValidField(field)){
                continue;
            }

            field.setAccessible(true);
            String fieldName = field.getName();

            try {
                Object value = field.get(null);

                if (field.getType() == double.class) {
                    editor.putString(fieldName, value.toString());
                }
                else if (field.getType() == int.class) {
                    editor.putInt(fieldName, (int) value);
                }
                else if (field.getType() == boolean.class) {
                    editor.putBoolean(fieldName, (boolean) value);
                }
                else {
                    editor.putString(fieldName, value.toString());
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        editor.apply();
    }

    public void loadAll() {
        Field[] fields = Options.class.getDeclaredFields();

        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            field.setAccessible(true);
            String fieldName = field.getName();

            try {
                if (field.getType() == double.class) {
                    String valueStr = prefs.getString(fieldName, "0.0");
                    double valueDouble = Double.parseDouble(valueStr);
                    field.setDouble(null, valueDouble);
                }
                else if (field.getType() == int.class) {
                    int value = prefs.getInt(fieldName, 0);
                    field.setInt(null, value);
                }
                else if (field.getType() == boolean.class) {
                    boolean value = prefs.getBoolean(fieldName, false);
                    field.setBoolean(null, value);
                }
                else if (field.getType().isEnum()) {
                    // Handle enums
                    String value = prefs.getString(fieldName, null);
                    if (value != null) {
                        Object enumValue = Enum.valueOf((Class<Enum>) field.getType(), value);
                        field.set(null, enumValue);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void clearAll() {
        prefs.edit().clear().apply();
    }
}
