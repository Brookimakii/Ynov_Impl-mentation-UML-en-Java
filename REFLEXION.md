# Avant propos

J'ai décider d'utiliser la dépendance Lombok afin de réduire la quantité de texte à écrire.

L'annotation ```@Getter et @Setter``` replace tous les getter et setter associer à la classe.

# Étape 1: Faire Foo, Corge & Grault

J'ai commencer par faire tous les paramères de la classe Foo

```java

@Getter
@Setter
public class Foo {
    private Bar bar;
    private ArrayList<Baz> bazs;
    private Qux qux;
    private Corge corge;
    private ArrayList<Grault> graults;

    public Foo(Bar bar) {
        this.bar = bar;
        this.bazs = new ArrayList<Baz>();
        this.graults = new ArrayList<Grault>();
        this.qux = new Qux();
    }

    public void addBaz(Baz baz) {
        this.bazs.add(baz);
    }

    public void addGrault() {
        Grault grault = new Grault(this); // Composition, Grault belongs to Foo
        graults.add(grault);
    }

}
```

Ensuite j'ai fait Corge & Grault puisqu'elles sont les seules classes (hors Foo) à avoir des proriétés et methodes

```java

@Getter
@Setter
public class Grault {
    private Foo foo;

    public Grault(Foo foo) {
        this.foo = foo;
    }
}

@Getter
@Setter
public class Corge {
    private Corge foo;

    public Grault(Foo foo) {
        this.foo = foo;
    }
}

```

# Étape 2 : Les Tests

Malheureusement, corgeTest() ne fonctionne pas.

```java
void corgeTest() {
    Foo foo = new Foo(new Bar());
    Corge firstCorge = new Corge(foo);
    assertEquals(foo, firstCorge.getFoo());
    assertEquals(firstCorge, foo.getCorge()); //Ce teste rate
}
```

En effet, rien dans le code n'assigne un Corge à Fool lors de l'initialisation d'un corge. Alors modifions Corge comme
suit:

```java

@Getter
@Setter
public class Corge {
    private Foo foo;

    public Corge(Foo foo) {
        this.foo = foo;
        this.foo.setCorge(this);
    }
}
```

mainenant un autre test rate:

```java
void corgeTest() {
    //[...]
    assertEquals(foo, secondCorge.getFoo());
    assertEquals(secondCorge, foo.getCorge());
    assertNull(firstCorge.getFoo()); //Ce teste rate
    //[...]
}
```

Un Deux Corge ne peuvent pas partager un Foo d'après le diagramme. Donc quand le deuxième Corge est créer, le premier
Corge ne doit plus avoir de Foo associer. Pour cela on prend le foo et lorque setCorge et appeler, on supprime le foo
dans le Corge déjà présent.

```java

@Getter
@Setter
public class Foo {
    //[...]
    public void setCorge(Corge corge) {
        if (this.corge != null) {
            this.corge.setFoo(null);
        }
        this.corge = corge;
    }
    //[...]
}
```

Un nouveau test rate. En effet, Foo lors d'un ```setCorge(Corge corge)``` dans Foo, Foo n'est pas assigné dans Corge.

```java
void corgeTest() {
    //[...]
    foo.setCorge(firstCorge);
    assertEquals(firstCorge, foo.getCorge());
    assertEquals(foo, firstCorge.getFoo()); //Ce teste rate
    //[...]
}
```

Modifions un peu la méthod ```setCorge(Corge corge)```.

```java
public void setCorge(Corge corge) {
    if (this.corge != null) {
        this.corge.setFoo(null);
    }
    this.corge = corge;
    this.corge.setFoo(this);
}
```

Maintenant le dernier teste rate.

```java
void corgeTest() {
    //[...]
    secondCorge.setFoo(foo);
    assertEquals(foo, secondCorge.getFoo());
    assertEquals(secondCorge, foo.getCorge());
    assertNull(firstCorge.getFoo()); //Ce teste rate
    //[...]
}
```

En effet Set Corge ne modifie pas Foo. Modifions donc Corge. J'ai décidé de refactorer un peu, en effet, le constructeur
et ```setFoo(Foo foo)``` avaient exactement le meme contenu. Le ```foo != null``` est essenciel puisque plus haut ont assigne null à setFoo()

```java

@Getter
public class Corge {
    private Foo foo;

    public Corge(Foo foo) {
        setFoo(foo);
    }

    public void setFoo(Foo foo) {
        this.foo = foo;
        if (foo != null) {
            this.foo.setCorge(this);
        }
    }
}
```
Maintenant il y a une boucle infini dans le programme:
```
java.lang.StackOverflowError
```
En effet, setFoo() et setCorge() s'appel mutuellement pour stopper ça, on verifie si foo ou corge est déjà set dans l'objet correspondant si c'est le cas, alors on ne fait rien.

```java
public class Corge {
    public void setFoo(Foo foo) {
        if (this.foo==foo) return;
        this.foo = foo;
        if (foo != null) {
            this.foo.setCorge(this);
        }
    }
}
@Getter
public class Foo {
    public void setCorge(Corge corge) {
        if (this.corge==corge) return;
        if (this.corge != null) {
            this.corge.setFoo(null);
        }
        this.corge = corge;
        this.corge.setFoo(this);
    }
}
```

Et ainsi tous les tests passent.