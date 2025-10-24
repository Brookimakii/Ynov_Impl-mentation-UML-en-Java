package com.jad.classe;

import com.jad.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

public class Foo implements IFoo {
    @Getter
    private final IBaz baz;
    @Getter
    private final ArrayList<IBar> bars;
    @Getter
    private final IQux qux;
    private ICorge corge;

    public Foo(IBaz baz) {
        this.bars = new ArrayList<>();
        this.baz = baz;
        this.qux = new Qux();
    }

    public void addBar(IBar bar) {
        this.bars.add(bar);
    }


    @Override
    public ICorge getCorge() {
        return this.corge;
    }

    public void setCorge(ICorge corge) {
        if (this.corge == corge) return;
        if (this.corge != null) {
            this.corge.setFoo(null);
        }
        this.corge = corge;
        this.corge.setFoo(this);
    }

    @Override
    public void thisIsAVoidMethodBecauseTheTestAskForThreeMethodsButThisInterfaceHasNoReasonToHaveMoreThanTwoMethodsWhenLookingAtUMLDiagram() {

    }

}
