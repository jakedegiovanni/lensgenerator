package lensgenerator.lens;

import java.util.function.BiFunction;
import java.util.function.Function;

// https://gist.github.com/mathieuancelin/bb30a104c17037e34f0b
public class Lens<T, V> {
    private Function<T, V> getter;
    private BiFunction<T, V, T> setter;

    private Lens(Function<T, V> getter, BiFunction<T, V, T> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public static <T, V> Lens<T, V> of(Function<T, V> getter, BiFunction<T, V, T> setter) {
        return new Lens<>(getter, setter);
    }

    public V get(T obj) {
        return getter.apply(obj);
    }

    public T set(T obj, V newVal) {
        return setter.apply(obj, newVal);
    }

    public T modify(Function<V, V> transform, T obj) {
        V val = getter.apply(obj);
        return setter.apply(obj, transform.apply(val));
    }

    public <Z> Lens<T, Z> compose(Lens<V, Z> other) {
        return Lens.of(
                (T t) -> other.getter.apply(getter.apply(t)),
                (T t, Z z) -> {
                    V v = getter.apply(t);
                    V newV = other.setter.apply(v, z);
                    return setter.apply(t, newV);
                }
        );
    }
}
