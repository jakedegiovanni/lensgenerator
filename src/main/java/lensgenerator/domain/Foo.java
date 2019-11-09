package lensgenerator.domain;

import lensgenerator.lens.annotations.LensField;
import lensgenerator.lens.annotations.LensSource;

@LensSource
public class Foo {

    public static final String HELLO_FIELD = "hello";
    public static final String WORLD_FIELD = "world";

    @LensField(name = HELLO_FIELD)
    private String hello;

    @LensField(name = WORLD_FIELD)
    private String world;

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }
}
