package com.jad.classe;

import com.jad.ICorge;
import com.jad.IFoo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class Corge implements ICorge {
    private IFoo foo;

    public void setFoo(IFoo foo) {
        if (this.foo==foo) return;
        this.foo = foo;
        if (foo != null) {
            this.foo.setCorge(this);
        }
    }
}
