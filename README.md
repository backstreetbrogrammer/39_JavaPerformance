# Java Performance

> This is a Java course to understand performance, garbage collection and JVM tuning techniques.

Tools used:

- JDK 11
- Maven
- JUnit 5, Mockito
- IntelliJ IDE

## Table of contents

1. [Introduction to Java Performance](https://github.com/backstreetbrogrammer/39_JavaPerformance#chapter-01-introduction-to-java-performance)
    - [Virtual machine optimization methods](https://github.com/backstreetbrogrammer/39_JavaPerformance#interview-problem-1-merrill-lynch---what-is-the-difference-between-latency-bandwidth-and-throughput)
    - [Interview Problem 1 (Merrill Lynch) - What is the difference between latency, bandwidth and throughput?](https://github.com/backstreetbrogrammer/39_JavaPerformance#interview-problem-1-merrill-lynch---what-is-the-difference-between-latency-bandwidth-and-throughput)
2. Just In Time Compilation
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

