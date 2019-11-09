package lensgenerator.lens.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LensGenerator {
    Class<?> clazz();
}
