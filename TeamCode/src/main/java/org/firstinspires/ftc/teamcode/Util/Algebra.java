package org.firstinspires.ftc.teamcode.Util;

public class Algebra {
    private Algebra() {}

    public static <T extends Number & Comparable<T>> T clamp(T min, T max, T val) {
        if (min.compareTo(max) > 0) {
            T tmp = min;
            min = max;
            max = tmp;
        }

        if (val.compareTo(min) < 0) return min;
        if (val.compareTo(max) > 0) return max;
        return val;
    }
}
