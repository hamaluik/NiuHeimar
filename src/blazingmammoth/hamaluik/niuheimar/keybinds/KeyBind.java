package blazingmammoth.hamaluik.niuheimar.keybinds;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import blazingmammoth.hamaluik.niuheimar.NiuHeimar;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface KeyBind {
	public int key();
	public int[] keyMods() default {};
	public Class<?> scope() default NiuHeimar.class;
}
