# Les interfaces

Après avoir effectué les plans de Plan.md, J'ai modifié les interfaces afin que les tests passent.

J'ai rencontré un problem avec IFoo, en effet, il n'est pas nécessaire que l'interface ai 3 methode pourtant 3 méthodes
sont demandé.
A part ça tous les tests des interfaces sont passés

# Les Classes

## Implémentation

J'ai ensuite implémenté les interfaces dans les classes. J'ai dû modifier les methode afin qu'elles prennent les
interfaces en paramètre plutôt que les classes.

## Foo

Foo est une autre paire de manches. Beaucoup de tests ne passent pas.
J'ai premièrement réalisé que Bar et Baz sont inversé par rapport à l'activité 1 dans Foo (l'un est une list l'autre est
un objet). Je les ai donc inversés.

Et ainsi tous les tests passe.

# Sauf...

- com.jad.FooTest#countMethodsTest
- com.jad.IFooTest#countMethodsTest

Ces deux tests sont mutuellement exclusifs avec les paramères donnée dans les tests.