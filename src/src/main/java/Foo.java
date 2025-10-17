import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
public class Foo {
    @Setter
    private Bar bar;
    @Setter
    private ArrayList<Baz> bazs;
    @Setter
    private Qux qux;
    private Corge corge;
    @Setter
    private ArrayList<Grault> graults;

    public Foo(Bar bar) {
        this.bar = bar;
        this.bazs = new ArrayList<>();
        this.graults = new ArrayList<>();
        this.qux = new Qux();
    }

    public void addBaz(Baz baz) {
        this.bazs.add(baz);
    }

    public void addGrault() {
        Grault grault = new Grault(this); // Composition, Grault belongs to Foo
        graults.add(grault);
    }

    public void setCorge(Corge corge) {
        if (this.corge == corge) return;
        if (this.corge != null) {
            this.corge.setFoo(null);
        }
        this.corge = corge;
        this.corge.setFoo(this);
    }
}
