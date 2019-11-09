package lensgenerator.spring.service;

import lensgenerator.domain.Bar;
import lensgenerator.domain.Zee;
import lensgenerator.spring.bean.processor.LensBeanProcessor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BarLensService.class, LensBeanProcessor.class})
public class BarLensServiceTest {

    @Autowired
    private BarLensService barLensService;

    @Test
    public void testLensGeneratorBeanProcessor() {
        Bar b = new Bar();
        Zee z = new Zee();

        b.setZee(z);

        barLensService.setValue(b, Zee.HELLO_FIELD, "ZEE_HELLO_TEST");
        Assert.assertEquals(barLensService.getValue(b, Zee.HELLO_FIELD), "ZEE_HELLO_TEST");
    }
}
