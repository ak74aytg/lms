# JVM Basics

The **JDK** (Java Development Kit) is the toolkit used to develop Java programs. It includes tools such as `javac`, the Java compiler. The **JRE** (Java Runtime Environment) contains what is needed to run Java programs, including the JVM and standard libraries. The **JVM** (Java Virtual Machine) is the program that loads and executes Java bytecode.

When Java source code is compiled, `javac` turns it into **bytecode**, normally stored in a `.class` file. Bytecode is an intermediate instruction format understood by a JVM rather than directly by a specific operating system processor.

“Write once, run anywhere” means that the same compiled Java program can run on different operating systems when a compatible JVM is available. The JVM provides the platform-specific layer, so the application does not need a separate source implementation for Windows, macOS, and Linux.

