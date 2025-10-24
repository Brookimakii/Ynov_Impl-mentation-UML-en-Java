package com.jad.classe;

import com.jad.ICorge;
import com.jad.IFoo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter @RequiredArgsConstructor
public class Foo implements IFoo {
    private final Bar bar;
    private final ArrayList<EBaz> bazs;
    private final Qux qux;
    private ICorge corge;

    public Foo(Bar bar) {
        this.bar = bar;
        this.bazs = new ArrayList<>();
        this.qux = new Qux();
    }

    public void addBaz(EBaz baz) {
        this.bazs.add(baz);
    }

    public void setCorge(ICorge corge) {
        if (this.corge == corge) return;
        if (this.corge != null) {
            this.corge.setFoo(null);
        }
        this.corge = corge;
        this.corge.setFoo(this);
    }


}
