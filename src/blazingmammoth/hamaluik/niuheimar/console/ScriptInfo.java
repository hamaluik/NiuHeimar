package blazingmammoth.hamaluik.niuheimar.console;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ScriptInfo {
	public String alias();
	public String[] args() default {};
	public String description();
	public String longDescription() default "";
}