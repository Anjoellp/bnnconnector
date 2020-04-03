package de.bioshop.bnnconnector.model;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target({FIELD})
public @interface BNNInfo {

	public String tableId();

	public int id();

	public boolean required() default true;

	public InfoType infoType() default InfoType.HEADER;
}
