package com.jad.classe;

import com.jad.IBaz;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public enum EBaz implements IBaz {
    BAZ1("Baz1"),
    BAZ2("Baz2"),
    BAZ3("Baz3"),
    ;

    private final String name;

    @Override
    public void doSomethingLikeABaz() {

    }
}
