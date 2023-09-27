# Java Performance

> This is a Java course to understand performance, garbage collection and JVM tuning techniques.

Tools used:

- JDK 11
- Maven
- JUnit 5, Mockito
- IntelliJ IDE

## Table of contents

1. [Introduction to Java Performance](https://github.com/backstreetbrogrammer/39_JavaPerformance#chapter-01-introduction-to-java-performance)
    - [Virtual machine optimization methods](https://github.com/backstreetbrogrammer/39_JavaPerformance#virtual-machine-optimization-methods)
    - [Interview Problem 1 (Merrill Lynch) - What is the difference between latency, bandwidth and throughput?](https://github.com/backstreetbrogrammer/39_JavaPerformance#interview-problem-1-merrill-lynch---what-is-the-difference-between-latency-bandwidth-and-throughput)
2. [Just In Time Compilation](https://github.com/backstreetbrogrammer/39_JavaPerformance#chapter-02-just-in-time-compilation)
    - [Interview Problem 2 (Barclays) - What is JVM warmup and how does it improve JVM performance?](https://github.com/backstreetbrogrammer/39_JavaPerformance#interview-problem-2-barclays---what-is-jvm-warmup-and-how-does-it-improve-jvm-performance)
    - [Interview Problem 3 (Point72 Hedge Fund) - Print Code Compilation](https://github.com/backstreetbrogrammer/39_JavaPerformance#print-code-compilation)
    - [Compilers C1 and C2](https://github.com/backstreetbrogrammer/39_JavaPerformance#print-code-compilation)
3. Java Memory Model
    - Escaping References
    - JVM memory tuning
4. Garbage Collection
    - Monitoring and Tuning Heap
    - Garbage Collector Tuning
5. Performance Benchmarking
    - Using a profiler
    - Using JMH

---

## Chapter 01. Introduction to Java Performance

`Java` was historically considered slower than the fastest 3rd generation typed languages such as `C` and `C++`.

The main reason being a different language design, where after compiling, Java programs run on a `Java virtual
machine (JVM)` rather than directly on the computer's processor as `native code`, as do C and C++ programs.

Performance was a matter of concern because much business software has been written in Java after the language quickly
became popular in the late 1990s and early 2000s.

Since the late 1990s, the execution speed of Java programs improved significantly via introduction of `just-in-time
compilation (JIT)` (in 1997 for Java 1.1), the addition of language features supporting better code analysis,
and optimizations in the JVM (such as `HotSpot` becoming the default for Sun's JVM in 2000).

Hardware execution of Java `bytecode`, was also explored to offer significant performance improvements.

The performance of a Java bytecode compiled Java program depends on how optimally its given tasks are managed by the
host Java virtual machine (JVM), and how well the JVM exploits the features of the computer hardware and operating
system (OS) in doing so.

Thus, any Java performance test or comparison has to always report the version, vendor, OS and hardware architecture of
the used JVM.

In a similar manner, the performance of the equivalent natively compiled program will depend on the quality of its
generated machine code, so the test or comparison also has to report the name, version and vendor of the used compiler,
and its activated compiler optimization directives.

### Virtual machine optimization methods

Many optimizations have improved the performance of the JVM over time.

**_Just-in-time compiling_**

Early JVMs always interpreted Java bytecodes. This had a large performance penalty of between a factor 10 and 20 for
Java versus C in average applications.

To combat this, a **just-in-time (JIT) compiler** was introduced into Java 1.1.

Due to the high cost of compiling, an added system called **HotSpot** was introduced in Java 1.2 and was made the
default in
Java 1.3.

Using this framework, the Java virtual machine continually analyses program performance for hot spots which
are executed frequently or repeatedly.

These are then targeted for optimizing, leading to high performance execution with a minimum of overhead for less
performance-critical code. Some benchmarks show a 10-fold speed gain by this means.

However, due to time constraints, the compiler cannot fully optimize the program, and thus the resulting program is
slower than native code alternatives.

**_Adaptive optimizing_**

Adaptive optimizing performs dynamic recompilation of parts of a program based on the current execution profile.

With a simple implementation, an adaptive optimizer may simply make a trade-off between just-in-time compiling and
interpreting instructions.

At another level, adaptive optimizing may exploit local data conditions to optimize away branches and use inline
expansion.

A Java virtual machine like **HotSpot** can also de-optimize code formerly **JITed**.

This allows performing aggressive (and potentially unsafe) optimizations, while still being able to later de-optimize
the code and fall back to a safe path.

**_Garbage collection_**

The 1.0 and 1.1 Java virtual machines (JVMs) used a **mark-sweep** collector, which could fragment the heap after a
garbage collection.

Starting with Java 1.2, the JVMs changed to a **generational** collector, which has a much better defragmentation
behaviour.

Modern JVMs use a variety of methods that have further improved garbage collection performance.

**_Compressed Oops_**

Compressed Oops allow Java 5.0+ to address up to `32 GB` of heap with `32-bit` references.

Java does not support access to individual bytes, only objects which are `8-byte` aligned by default.

Because of this, the lowest `3 bits` of a heap reference will always be `0`.

By lowering the resolution of `32-bit` references to `8 byte` blocks, the addressable space can be increased to `32 GB`.

This significantly reduces memory use compared to using `64-bit` references as Java uses references much more than some
languages like C++.

**Java 8** supports larger alignments such as `16-byte` alignment to support up to `64 GB` with `32-bit` references.

**_Split bytecode verification_**

Before executing a class, the JVM **verifies** its Java bytecodes.

This verification is performed lazily: classes' bytecodes are only loaded and verified when the specific class is loaded
and prepared for use, and not at the beginning of the program.

However, as the Java class libraries are also regular Java classes, they must also be loaded when they are used, which
means that the start-up time of a Java program is often longer than for C++ programs, for example.

A method named **split-time verification**, first introduced in the **Java Platform, Micro Edition (J2ME)**, is used in
the JVM since Java version 6. It splits the verification of Java bytecode in two phases:

- Design-time – when compiling a class from source to bytecode
- Runtime – when loading a class

In practice, this method works by capturing knowledge that the Java compiler has of class flow and annotating the
compiled method bytecodes with a synopsis of the class flow information. This does not make runtime verification
appreciably less complex, but does allow some shortcuts.

**_Escape analysis and lock coarsening_**

Java is able to manage multithreading at the language level.

Multithreading allows programs to perform multiple processes concurrently, thus improving the performance for programs
running on computer systems with multiple processors or cores.

Also, a multithreaded application can remain responsive to input, even while performing long running tasks.

However, programs that use multithreading need to take extra care of objects shared between threads, locking access to
shared methods or blocks when they are used by one of the threads.

Locking a block or an object is a time-consuming operation due to the nature of the underlying operating system-level
operation involved.

As the Java library does not know which methods will be used by more than one thread, the standard library always locks
blocks when needed in a multithreaded environment.

Before Java 6, the virtual machine always locked objects and blocks when asked to by the program, even if there was no
risk of an object being modified by two different threads at once.

Starting with Java 6, code blocks and objects are locked only when needed.

Since version 6u23, Java includes support for escape analysis.

**_Register allocation improvements_**

Before Java 6, allocation of registers was very primitive in the client virtual machine (they did not live across
blocks), which was a problem in CPU designs which had fewer processor **registers** available, as in `x86s`.

If there are no more registers available for an operation, the compiler must copy from register to memory (or memory to
register), which takes time (registers are significantly faster to access).

However, the server virtual machine used a color-graph allocator and did not have this problem.

An optimization of register allocation was introduced in Sun's JDK 6; it was then possible to use the same registers
across blocks (when applicable), reducing accesses to the memory.

This led to a reported performance gain of about 60% in some benchmarks.

**_Class data sharing_**

Class data sharing (called CDS by Sun) is a mechanism which reduces the startup time for Java applications, and also
reduces memory footprint.

When the JRE is installed, the installer loads a set of classes from the system JAR file (the JAR file holding all the
Java class library, called rt.jar) into a private internal representation, and dumps that representation to a file,
called a **"shared archive"**.

During subsequent JVM invocations, this shared archive is memory-mapped in, saving the cost of loading those classes and
allowing much of the JVM's metadata for these classes to be shared among multiple JVM processes.

The corresponding improvement in start-up time is more obvious for small programs.

### Interview Problem 1 (Merrill Lynch) - What is the difference between latency, bandwidth and throughput?

**Water Analogy**

![ThroughputLatency](ThroughputLatency.PNG)

- **Latency** is the amount of time it takes to travel through the tube
- **Bandwidth** is how wide the tube is
- The rate of water flow is the **Throughput**

**Vehicle Analogy**

- Vehicle travel time from source to destination is **latency**
- Types of Roadways are **bandwidth**
- Number of Vehicles traveling is **throughput**

---

## Chapter 02. Just In Time Compilation

**Bytecode**

Java bytecode is the bytecode-structured instruction set of the Java virtual machine (JVM), a virtual machine that
enables a computer to run programs written in the Java programming language and several other programming languages
like Scala, Groovy, Kotlin and Java.

Java bytecode can be **interpreted** and compiled to native OS machine code by JVM to run the program.

This feature let programmers `Write once, Run anywhere (WORA)`, meaning that compiled Java code can run on all platforms
that support Java without the need to recompile.

Java applications are typically compiled to bytecode that can run on any Java virtual machine (JVM) regardless of the
underlying computer architecture.

The syntax of Java is similar to C and C++, but has fewer low-level facilities than either of them. The
Java runtime provides dynamic capabilities (such as reflection and runtime code modification) that are typically not
available in traditional compiled languages.

![Bytecode](Bytecode.PNG)

**Just-in-time compilation**

Just-in-time (JIT) compilation (also dynamic translation or run-time compilation) is compilation of computer code during
execution of a program at run time rather than before execution.

This may consist of source code translation but is more commonly bytecode translation to machine code, which is then
executed directly.

A system implementing a JIT compiler typically **continuously analyses** the code being executed and identifies parts of
the code where the speedup gained from compilation or recompilation would outweigh the overhead of compiling that code.

### Interview Problem 2 (Barclays) - What is JVM warmup and how does it improve JVM performance?

JVM warmup refers to having a piece of code run enough times that the JVM stops interpreting and compiles to native.

The reason is that the JVM gathers statistics about the code in question that it uses during code generation.

As JIT compiler will keep on analysing the code and compile the most used methods or blocks into native code - if it
gets enough time to do the analysis - it can improve performance.

In JVM, there are multiple threads:

- one thread is doing bytecode interpretation
- another thread is doing JIT compilation

Thus, if more time is given for code run - all the native compilation would have been completed and the code will
execute faster

If a code chunk in question is "warmed" with fake data which has different properties than the real data could
well be very good performant.

For a low latency application, we should warmup the **critical path** in our system.

We should have unit tests consisting of fake data that could be run on start up to warmup up the code and compile
the hot spots of the code to native code.

### Interview Problem 3 (Point72 Hedge Fund) - Print Code Compilation

We want to see which part of code is compiled natively.

For example, we have `PrimeNumbersGenerator` class:

```java
import java.util.ArrayList;
import java.util.List;

public class PrimeNumbersGenerator {

    private List<Integer> primes;

    private Boolean isPrime(final Integer testNumber) {
        for (int i = 2; i < testNumber; i++) {
            if (testNumber % i == 0) return false;
        }
        return true;
    }

    private Integer getNextPrimeAbove(final Integer previous) {
        Integer testNumber = previous + 1;
        while (!isPrime(testNumber)) {
            testNumber++;
        }
        return testNumber;
    }

    public void generateNumbers(final Integer max) {
        primes = new ArrayList<>();
        primes.add(2);

        Integer next = 2;
        while (primes.size() <= max) {
            next = getNextPrimeAbove(next);
            primes.add(next);
        }
        System.out.println(primes);
    }

    public static void main(final String[] args) {
        final PrimeNumbersGenerator primeNumbers = new PrimeNumbersGenerator();
        final Integer max = Integer.parseInt(args[0]);
        primeNumbers.generateNumbers(max);
    }

}
```

We can print say first 10 prime numbers: `java PrimeNumbersGenerator 10`

Output:

```
[2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31]
```

Let's print the compilation using the flag **-XX:+PrintCompilation
**: `java -XX:+PrintCompilation PrimeNumbersGenerator 10`

Sample output:

```
    ...
    ...
    249  206       3       java.lang.Math::max (11 bytes)
    250  205       3       java.util.zip.ZipFile$Source::isMetaName (141 bytes)
    250  207       3       sun.nio.fs.WindowsPathParser::isInvalidPathChar (22 bytes)
    252  208 %     4       java.util.zip.ZipFile$Source::hashN @ 2 (26 bytes)
    259  209 % !   4       java.util.zip.ZipFile$Source::checkUTF8 @ 4 (43 bytes)
    260  211       3       java.lang.System::getSecurityManager (4 bytes)
    263  213       3       java.util.Arrays::copyOf (19 bytes)
    265  212       4       java.util.zip.ZipFile$Source::hashN (26 bytes)
    267  193       3       java.util.zip.ZipFile$Source::hashN (26 bytes)   made not entrant
    267  210       4       java.util.zip.ZipFile$Source::addEntry (33 bytes)
    269  204       3       java.util.zip.ZipFile$Source::addEntry (33 bytes)   made not entrant
    269  215   !   4       java.util.zip.ZipFile$Source::checkUTF8 (43 bytes)
    273  216       3       java.lang.StringBuilder::append (8 bytes)
    274  217       3       java.lang.AbstractStringBuilder::append (45 bytes)
    274  194   !   3       java.util.zip.ZipFile$Source::checkUTF8 (43 bytes)   made not entrant
    275  214       4       java.lang.StringLatin1::indexOf (61 bytes)
    276  218       3       java.lang.String::getBytes (44 bytes)
    281   55       3       java.lang.StringLatin1::indexOf (61 bytes)   made not entrant
    282  219       3       java.lang.StringLatin1::lastIndexOf (40 bytes)
    283  220       3       java.lang.AbstractStringBuilder::isLatin1 (19 bytes)
    290  221       3       java.lang.AbstractStringBuilder::newCapacity (55 bytes)
    292   73       4       java.lang.String::charAt (25 bytes)   made not entrant
    292  222       3       java.lang.String::startsWith (138 bytes)
    294  223       3       java.lang.String::charAt (25 bytes)
    296  224       3       java.lang.Class::getName (18 bytes)
    296  225       1       java.lang.Integer::intValue (5 bytes)
[2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31]
    297  226       3       java.lang.invoke.MethodType::hashCode (53 bytes)
    297  227       4       java.util.Objects::requireNonNull (14 bytes)
    297  228     n 0       java.lang.Object::clone (native)   
    297   13       3       java.util.Objects::requireNonNull (14 bytes)   made not entrant

```

Let's explain each column shown in the above output:

- **First column** is the **timer** in `ms`, that it took to run and it's cumulative. Last row is the **total time**.
- **Second column** is the **order** that the line item was run.
- **Third column** is:
    - blank
    - `n` = native
    - `s` = synchronized
    - `!` = some exception handling was going on
    - `%` = code has been natively compiled and is running in memory under the **code cache**
- **Fourth column** has a value from `0` to `4`. This corresponds as an **ENUM** to what type of compilation has taken
  place:
    - `0`: no compilation took place (code was interpreted)
    - `1 through 4`: is a range of compilation complexity (4 being greatest)
- **Fifth and final column** is the line item that was run.

### Compilers C1 and C2

There are two compilers in Java:

- C1 handles native compilation levels 1-3
- C2 handles native level 4 only (which stores native compiles into the code cache).

The JVM determines the compilation level based on how often it is being run and how complex or how time-consuming it
is – through profiling.

As there is a tradeoff in optimizing more complex code (higher native tier/levels), it only will do this with methods
that are called more often, or have greater complexity.