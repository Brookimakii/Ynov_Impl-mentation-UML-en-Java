package Activity1;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Grault {
    private Foo foo;
    public Grault(Foo foo) {
        this.foo = foo;
    }
}
