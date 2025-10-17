import lombok.Getter;

@Getter
public class Corge {
    private Foo foo;

    public Corge(Foo foo) {
        this.setFoo(foo);
    }

    public void setFoo(Foo foo) {
        if (this.foo==foo) return;
        this.foo = foo;
        if (foo != null) {
            this.foo.setCorge(this);
        }
    }
}
