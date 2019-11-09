package lensgenerator.lens.processor;

import lensgenerator.lens.Lens;
import lensgenerator.lens.annotations.LensField;
import lensgenerator.lens.annotations.LensSource;
import lensgenerator.lens.exception.LensException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class LensFactory {

    public LensContainer constructLens(Class clazz) throws LensException {
        Map<String, Lens> x = createLensMap(clazz);

        LensContainer container = new LensContainer();
        container.setX(x);
        container.setClazz(clazz);

        return container;
    }

    private Map<String, Lens> createLensMap(Class clazz) throws LensException {
        if (!clazz.isAnnotationPresent(LensSource.class)) throw new LensException("Class is not a LensSource");

        Field[] fields = clazz.getDeclaredFields();

        Map<String, Lens> lensMap = new HashMap<>();

        for (Field field : fields) {
            if (!field.isAnnotationPresent(LensField.class)) continue;

            LensField lensField = field.getAnnotation(LensField.class);
            String name = lensField.name();
            Lens l = createLens(field);
            lensMap.put(name, l);

            if (field.getType().isAnnotationPresent(LensSource.class)) {
                Map<String, Lens> nestedTypeLensMap = createLensMap(field.getType());
                nestedTypeLensMap.forEach((k, v) -> lensMap.put(k, l.compose(v)));
            }
        }

        return lensMap;
    }

    private <T, V> Lens<T, V> createLens(Field field) {
        return Lens.of(wrapGet(field), wrapSet(field));
    }

    private <T, V> Function<T, V> wrapGet(Field field) {
        return (T t) -> {
            try {
                return (V) get(field, t);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new LensException("illegal get access");
            }
        };
    }

    // todo: type safe?
    private Object get(Field field, Object obj) throws IllegalAccessException {
        boolean a = field.canAccess(obj);
        if (!a) field.setAccessible(true);
        Object o = field.get(obj);
        if (!a) field.setAccessible(false);
        return o;
    }

    private <T, V> BiFunction<T, V, T> wrapSet(Field field) {
        return (T t, V v) -> {
            try {
                return set(field, t, v);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new LensException("illegal set access");
            }
        };
    }

    private <T> T set(Field field, T obj, Object value) throws IllegalAccessException {
        boolean a = field.canAccess(obj);
        if (!a) field.setAccessible(true);
        field.set(obj, value);
        if (!a) field.setAccessible(false);
        return obj;
    }
}
