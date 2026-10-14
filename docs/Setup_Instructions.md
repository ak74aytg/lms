# Setup Instructions

## JDK version used

LearnTrack was compiled and tested with **JDK 21.0.5 LTS** and uses standard Java APIs only. It should also work with Java 8 or later. Verify the installed JDK with:

```bash
java -version
javac -version
```

The `javac` command confirms that the Java Development Kit is installed, rather than only a runtime environment.

## Hello World

Create a file named `HelloWorld.java`:

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

Compile and run it from the same directory:

```bash
javac HelloWorld.java
java HelloWorld
```

Expected output:

```text
Hello, World!
```

The source file is compiled into `HelloWorld.class`, which contains Java bytecode for the JVM.

## Running LearnTrack

From the project root:

```bash
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out com.airtribe.learntrack.ui.Main
```

