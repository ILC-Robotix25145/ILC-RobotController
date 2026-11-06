package org.firstinspires.ftc.teamcode.Util.Config;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Option {
    double min() default 0;
    double max() default 0;
    double step() default 1;
}
