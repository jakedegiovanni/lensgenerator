package lensgenerator.spring.service;

import lensgenerator.domain.Bar;
import lensgenerator.lens.annotations.LensGenerator;
import lensgenerator.lens.processor.LensContainer;
import org.springframework.stereotype.Service;

@Service
public class BarLensService {

    @LensGenerator(clazz = Bar.class)
    private LensContainer container;

    public Object getValue(Bar b, String field) {
        return container.getFieldValue(field, b);
    }

    public void setValue(Bar b, String field, Object value) {
        container.setFieldValue(field, b, value);
    }
}
