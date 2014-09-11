## AssertJ examples

The **master** branch contains examples with the latest released version of AssertJ modules => you should be able to build it with `mvn clean install` command.

There are several branches that contain examples for the ongoing development versions of AssertJ modules. That means you have to build the according AssertJ modules by your own before new features compile. The following table contains these special branches that are merged into the master each time the module is released.

| Branch                        | Modules                                    |
| ----------------------------- | ------------------------------------------ |
| with-latest-snapshot-versions | Core + all modules without specific branch |
| with-latest-assert-guava-snapshot | Latest unreleased Guava assertions  |
| with-latest-swing-snapshot    | Swing                                      |

AssertJ examples is divided in two : assertions-examples (core, guava and joda assertions) and AssertJ swing modules.

### AssertJ assertions examples

**assertj-examples/assertions-examples** contains executable AssertJ assertions examples that you can run as JUnit tests.
Please have a look at **[assertions examples sources](assertions-examples/src/test/java/org/assertj/examples)**.

The **master** branch contains examples with the latest released version of AssertJ modules, you can build it with `mvn clean install` command.

Building **with-latest-snapshot-versions** is a little more complicated :
- you need to build the needed SNAPSHOT dependencies before - most probably assertj-core and maybe other modules. 
- run `mvn clean install` in `assertj-examples/assertions-examples`.
- In your IDE, add `target/generated-test-sources` to the project java sources if you IDE shows errors/missing classes.  

### AssertJ-Swing examples

**assertj-swing-aut** contains the AUT (application under test) that is tested by the examples.

**assertj-swing-junit-examples** contains executable AssertJ-Swing examples that you can inspect and run as JUnit tests.
Please have a look at **[swing-junit-examples sources](assertj-swing-junit-examples/src/test/java/org/assertj/swing/junit/examples)**.

**assertj-swing-testng-examples** contains executable AssertJ-Swing examples that you can inspect and run as TestNG tests.
Please have a look at **[swing-testng-examples sources](assertj-swing-testng-examples/src/test/java/org/assertj/swing/testng/examples)**.


## Contributing

Contributing is easy, only two rules to follow : 
* Checkout the snapshot branch corresponding to your examples, it should be `with-latest-snapshot-versions` most of the time (not master!) 
* Use **[AssertJ code Eclipse formatting preferences](https://github.com/joel-costigliola/assertj-core/blob/master/src/ide-support/assertj-eclipse-formatter.xml)** (for Idea users, it is possible to import it)
* Add FUN examples ! ;-)

Thanks !
