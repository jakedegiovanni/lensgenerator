package lensgenerator.domain;

import lensgenerator.lens.annotations.LensField;
import lensgenerator.lens.annotations.LensSource;

@LensSource
public class Zee {

    public static final String HELLO_FIELD = "ZeeHello";

    @LensField(name = HELLO_FIELD)
    private String hello;

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }
}
