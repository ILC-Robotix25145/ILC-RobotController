package org.firstinspires.ftc.teamcode.Util.Config;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // keep the tag while the program runs, so reflection can read it
@Target(ElementType.FIELD)          // it can only be put on variables
public @interface Range {
    double min();
    double max();
    double step() default 1;
}
