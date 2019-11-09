package lensgenerator.lens.processor;

import lensgenerator.lens.Lens;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class LensContainer {
    Map<String, Lens> x; // todo ????
    Class clazz;

    void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Object newInstance() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return clazz.getConstructor().newInstance();
    }

    public Map<String, Lens> getX() {
        return x;
    }

    void setX(Map<String, Lens> x) {
        this.x = x;
    }

    private Lens getFieldLens(String field) {
        return x.get(field);
    }

    public Object getFieldValue(String field, Object obj) {
        return getFieldLens(field).get(obj);
    }

    public Object setFieldValue(String field, Object obj, Object val) {
        return getFieldLens(field).set(obj, val);
    }
}
