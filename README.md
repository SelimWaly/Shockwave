<p align="center">
    <img src="assets/logo.png" alt="Banner" width="50%" height="50%">
</p>

<h1 align="center">Shockwave</h1>

<h4 align="center">Object-Orientally Programmed Java Chess Engine</h4>

---



![Build Status](https://github.com/SelimWaly/Shockwave/actions/workflows/build.yml/badge.svg)


Introduction
------------
Shockwave is a chess engine written using Object-Oriented Programming (OOP) in Java

The engine requires Java 11 for compilation and
execution.

This engine is not intended for competitive use as it is a simple engine for testing the limits of Java programming.


Compilation
--------
Shockwave uses [Maven](http://maven.apache.org/) to compile the Java for build systems. To build it from source, use the following
steps.

- Fork the repository
```shell
git clone https://github.com/SelimWaly/Shockwave.git
```

- Compile executable
```shell
./mvnw package
```

- Compress into ZIP file
```shell
cp target/shockwave-<version>.zip
<installation directory>
```


Features
--------
Only a couple of basic chess engine features are implemented to keep the
source code clean and readable. Below is a list of the major building
blocks.

- **UCI protocol**
Shockwave Chess uses [JCPI](https://github.com/fluxchess/jcpi) for implementing the UCI
protocol in Java.

- **0x88 board representation**
This engine uses a 0x88 board representation. In
addition piece lists are kept in Bitboards.

- **Only material and mobility evaluation**
Currently only material and mobility (to add some variation) are used
for calculating the evaluation function. However it should be quite easy to extend it with other evaluation features.

- **Using integers for type representation**
Although Java is quite efficient and fast in memory management, it is not fast enough for chess engines. Instead of using objects for important data structures, Shockwave Chess uses integers to
exploit the Java stack.

- **Pseudo-legal move generator**
To keep the source code clean and simple, a pseudo-legal move generator
is used. This has the advantage to skip writing a complicated legal move checking method.

- **Basic search**
Shockwave uses a basic Alpha-beta pruning algorithm with iterative
deepening. This allows us to use a very simple time management. In
addition there's a basic Quiescent search to improve the game play.


---
