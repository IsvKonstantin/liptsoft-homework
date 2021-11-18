# Notes
## General notes

[Solution](src/main/kotlin) is written in Kotlin (It's not the language I'm most confident with, so
I tried to practice with it). This task's description was ambiguous in some
cases (at least for me), my interpretation will be described later in notes.

[Tests](src/test/kotlin) are written using JUnit 5 library. 

## Notes on solution

Each class name has a name and a full name (for `a.b.FooBarBaz` it is
`a.b.FooBarBaz` and `FooBarBaz`). Each class name consists of words, which start
with an upper case english letter (`FooBarBaz` -> `Foo Bar Baz`). It is supposed
that provided class names are valid (contain only letter characters and are
presented in CamelCase), so I validate class names during data parsing.

Each pattern also consists of words which start with an upper case letter 
(`'F*oBar'` -> `'F*o Bar'`). These words should match (prefix match) class name words
in the right order (e.g. `'F*oBar'` matches`a.b.FooZooBar` but doesn't match `a.b.BarZooFoo`).
If provided pattern consists of only lower case characters then it is translated to upper case (`'fbz'` -> `'FBZ'`)
If provided pattern first letter is in lower case, it is also translated to upper case (`'fooBooZoo'` -> `'fooBooZoo'`).
Pattern may contain wildcard characters `*`. Wildcard characters only work within a pattern's word, so it
not the same as regexp wildcard (in `'F*dZoo'` `*` matches `bar` in `FbardGarZoo` and doesn't know about future words).
This may not seem as the most logical solution but in my opinion wildcard in this task should not be the same as
regexp wildcard (e.g `'FooZoo'` already has a hidden wildcard between `Foo` and `Zoo`).
It is supposed that provided patterns are valid (contain only letter and wildcard characters with
an optional single whitespace in the end). I also perform some sort of validation on patterns.

## Notes on tests

I implemented some unit tests for my solution. I tried to provide as many useful test cases as I could.
In some cases code style and general test structure for my tests may seem odd,
but I didn't manage to find a better solution (e.g. there are many asserts within one test function,
some methods in my class are made non-private just for the sake making tests more transparent etc.).
I tried to cover as many methods as I could, but some tests may be missing.

## Possible improvements

As far as I know, under the hood regexp constructs deterministic finite-state machine. I believe that the best
solution (computation-time wise) would be to construct same DFSM here (but I guess it's not what was expected to do).
My prefix matching method (which supports wildcards) is a recursive function (complexity is not linear).
However, considering that there are about 5000 classes in Java, overall extra computation time will not be
that noticeable (if comparing with linear time solution).