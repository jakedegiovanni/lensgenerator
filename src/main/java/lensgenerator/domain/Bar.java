package lensgenerator.domain;

import lensgenerator.lens.annotations.LensField;
import lensgenerator.lens.annotations.LensSource;

@LensSource
public class Bar {

    public static final String HELLO_FIELD = "BarHello";
    public static final String ZEE_FIELD = "BarZee";

    @LensField(name = HELLO_FIELD)
    private String hello;

    @LensField(name = ZEE_FIELD)
    private Zee zee;

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    public Zee getZee() {
        return zee;
    }

    public void setZee(Zee zee) {
        this.zee = zee;
    }
}
