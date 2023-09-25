# Java Performance

> This is a Java course to understand performance, garbage collection and JVM tuning techniques.

Tools used:

- JDK 11
- Maven
- JUnit 5, Mockito
- IntelliJ IDE

## Table of contents

1. [Introduction to Java Performance](https://github.com/backstreetbrogrammer/39_JavaPerformance#chapter-01-introduction-to-java-performance)
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

