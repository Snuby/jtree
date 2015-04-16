package com.jay.open.libs.common.annotations;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-10-24
 * Time: 17:00
 * To change this template use File | Settings | File Templates.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldMapping {

    String target() default "";
}
