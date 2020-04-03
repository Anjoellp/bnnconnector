package de.bioshop.bnnconnector.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.FIELD)
public @interface BNN {
	public int id();

	public String tableId();

	public boolean required() default false;

	public boolean transientBNN() default false;

	public BNNType type() default BNNType.NO;

	public enum BNNType {
		NO(null), CURRENCY("0.00\\€");
		private String formatText;

		private BNNType(String formatText) {
			this.formatText = formatText;
		}

		public String getFormatText() {
			return formatText;
		}
	}
}
