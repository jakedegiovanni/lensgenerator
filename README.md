# Motivation

Knowing about the internal structure of Java objects can cause a lot of pain when refactoring
if we have accessed the fields in our code. To avoid this pain we usually isolate the location
of the Data access. This is an attempt at, instead of isolating the access, abstracting it
away in such a manner that changing the models won't cause any further changes to propagate
through our architecture.

This is a Proof of Concept for auto generating a Lens for User defined Java Objects;
the proof of concept should work regardless of if the Object is simple (only containing
types provided by Java/a library) or complex (containing other user defined objects).
The generation is driven off of Annotations.

The proof of concept also includes a Spring Bean processor so that you can automatically inject
a Lens of the specified type into a class field as long as that class is a Spring bean and the
field is annotated with `LensGenerator` and it is of type `LensContainer`.

Lens's allow us to get and set values in an object without the caller having to know the
details about the internal structure of that object; we can therefore treat our POJOs as
black boxes when working with them outside the data layer. Auto generating a Lens 
allows us to reduce the boilerplate associated with creating a Lens for each field we 
want to work with.

I used the code in this [gist](https://gist.github.com/mathieuancelin/bb30a104c17037e34f0b) as
a basis for my Lens class.

To see the code in action you can run `AppTest` within `src/test/java/lensgenerator`.

Alternatively you can run `BarLensServiceTest` within `src/test/java/lensgenerator/spring/service`
to see how the Spring annotation of all this works.

View the [requirements](#Requirements) below for what you need to run everything.

# What is a Lens?

This [blog post](https://bartoszmilewski.com/category/lens/) by Bartosz Milewski explains
this in full detail; below is a TLDR summarizing what we care about for the use case
of improving the architecture of our Java applications.

A Lens is a getter and setter object that can reach into the internal structure of an
object and perform the instructed action. The caller of the Lens can treat the object
as a black box, knowing only that the field it wants is somewhere inside. 

For example, if an object Foo has a Bar field which in turn has a String field called hello
a Lens of type Foo (`Lens<Foo>`) stored in x can retrieve the value inside hello by calling
`x.get('hello')`. __NOTE: this isn't the exact syntax we will use.__  

# Known shortcomings of current implementation

- Type safety has not been accounted for as of the time of writing (2019-11-09), there are
unchecked casts.

# Requirements

- Java JDK 13 (the code will most likely work on JDK versions 8+ but I haven't tested it)
- Gradle 6