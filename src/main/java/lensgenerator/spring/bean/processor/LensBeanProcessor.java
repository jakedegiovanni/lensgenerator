package lensgenerator.spring.bean.processor;

import lensgenerator.lens.annotations.LensGenerator;
import lensgenerator.lens.processor.LensContainer;
import lensgenerator.lens.processor.LensFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

@Component
public class LensBeanProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(LensGenerator.class) && field.getType() == LensContainer.class) {
                Class classToGenerateLensFor = field.getAnnotation(LensGenerator.class).clazz();
                LensContainer container = new LensFactory().constructLens(classToGenerateLensFor);
                boolean a = field.canAccess(bean);
                if (!a) ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, bean, container);
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
