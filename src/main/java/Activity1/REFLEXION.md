# Avant propos

J'ai décider d'utiliser la dépendance Lombok afin de réduire la quantité de texte à écrire.

L'annotation ```@Getter et @Setter``` replace tous les getter et setter associé à la classe.

Lien du Repo: https://github.com/Brookimakii/Ynov_Impl-mentation-UML-en-Java

Je ne sais pas comment déclencher les actions alors j'ai ajouté le plugin JUnit au projet.

# Étape 1: Faire Foo, Corge & Grault

J'ai commencer par faire tous les paramères de la classe Foo

```java

import Activity1.*;

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

    public void addBaz (Baz baz){
        this.bazs.add(baz);
    }

    public void addGrault () {
        Grault grault = new Grault(this); // Composition, Activity1.Grault belongs to Activity1.Foo
        graults.add(grault);
    }

}
```

Ensuite j'ai fait Activity1.Corge & Activity1.Grault puisqu'elles sont les seules classes (hors Activity1.Foo) à avoir des proriétés et methodes

```java

import Activity1.Foo;

@Getter
@Setter
public class Activité_1.

Grault {
    private Foo foo;

    public Activity1.Grault(Foo foo) {
        this.foo = foo;
    }
}

@Getter
@Setter
public class Activité_1.

Corge {
    private Activity1.Corge foo;

    public Activity1.Grault(Foo foo) {
        this.foo = foo;
    }
}

```

# Étape 2 : Les Tests

Malheureusement, corgeTest() ne fonctionne pas.

```java
import Activity1.Bar;
import Activity1.Corge;
import Activity1.Foo;

void corgeTest() {
    Foo foo = new Foo(new Bar());
    Corge firstCorge = new Corge(foo);
    assertEquals(foo, firstCorge.getFoo());
    assertEquals(firstCorge, foo.getCorge()); //Ce teste rate
}
```

En effet, rien dans le code n'assigne un Activity1.Corge à Fool lors de l'initialisation d'un corge. Alors modifions Activity1.Corge comme
suit:

```java

import Activity1.Foo;

@Getter
@Setter
public class Activité_1.

Corge {
    private Foo foo;

    public Activity1.Corge(Foo foo) {
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

Un Deux Activity1.Corge ne peuvent pas partager un Activity1.Foo d'après le diagramme. Donc quand le deuxième Activity1.Corge est créer, le premier
Activity1.Corge ne doit plus avoir de Activity1.Foo associer. Pour cela on prend le foo et lorque setCorge et appeler, on supprime le foo
dans le Activity1.Corge déjà présent.

```java


@Getter
@Setter
public class Activité_1.

Foo {
    //[...]
    public void setCorge (Corge corge){
        if (this.corge != null) {
            this.corge.setFoo(null);
        }
        this.corge = corge;
    }
    //[...]
}
```

Un nouveau test rate. En effet, Activity1.Foo lors d'un ```setCorge(Activity1.Corge corge)``` dans Activity1.Foo, Activity1.Foo n'est pas assigné dans Activity1.Corge.

```java
void corgeTest() {
    //[...]
    foo.setCorge(firstCorge);
    assertEquals(firstCorge, foo.getCorge());
    assertEquals(foo, firstCorge.getFoo()); //Ce teste rate
    //[...]
}
```

Modifions un peu la méthod ```setCorge(Activity1.Corge corge)```.

```java
import Activity1.Corge;

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

En effet Set Activity1.Corge ne modifie pas Activity1.Foo. Modifions donc Activity1.Corge. J'ai décidé de refactorer un peu, en effet, le constructeur
et ```setFoo(Activity1.Foo foo)``` avaient exactement le meme contenu. Le ```foo != null``` est essenciel puisque plus haut ont assigne null à setFoo()

```java

import Activity1.Foo;

@Getter
public class Activité_1.

Corge {
    private Foo foo;

    public Activity1.Corge(Foo foo) {
        setFoo(foo);
    }

    public void setFoo (Foo foo){
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
public class Activity1.Corge {
    public void setFoo(Activity1.Foo foo) {
        if (this.foo==foo) return;
        this.foo = foo;
        if (foo != null) {
            this.foo.setCorge(this);
        }
    }
}
@Getter
public class Activity1.Foo {
    public void setCorge(Activity1.Corge corge) {
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

## Difficultés rencontrées
Je n'ai pas particulièrement rencontré de difficultés.