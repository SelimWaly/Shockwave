Release Notes - Shockwave - Version 2.0.0
-------------------------------------

* [CHANGE-97] - Use Java 11

Release Notes - Shockwave - Version 1.7.3
-------------------------------------

* \#84: Use precondition for active color only

Release Notes - Shockwave - Version 1.7.2
-------------------------------------

* [CHANGE-101] - Fix timer for C++

Release Notes - Shockwave - Version 1.7.1
-------------------------------------

* [CHANGE-96] - Create JAR for Javadoc again

Release Notes - Shockwave - Version 1.7.0
-------------------------------------

* [CHANGE-74] - Remove assertions
* [CHANGE-75] - Review unit tests
* [CHANGE-76] - Change indentation to 4 spaces
* [CHANGE-77] - Update to JDK 1.8
* [CHANGE-78] - Use Google Test from GitHub
* [CHANGE-79] - problems on raspberry pi (with linux) #54
* [CHANGE-80] - Switch to tabs
* [CHANGE-81] - Review code
* [CHANGE-82] - Replace Bitboard instances with long
* [CHANGE-83] - Create custom exception
* [CHANGE-84] - No static instance variables
* [CHANGE-86] - Annotate only nullable parameters
* [CHANGE-87] - required for linking #53
* [CHANGE-89] - Upgrade codebase
* [CHANGE-90] - Fix static analysis issues
* [CHANGE-91] - Fix packaging
* [CHANGE-92] - Fix abs() ambiguity
* [CHANGE-93] - Downgrade to CMake 3.10
* [CHANGE-94] - Fix mutex compilation error
* [CHANGE-95] - Fix condition variable in timer

Release Notes - Shockwave - Version 1.6.1
-------------------------------------

* [CHANGE-72] - Switch to Mingw-w64 as main compiler for C++ Edition
* [CHANGE-73] - Fix filenames for artifacts

Release Notes - Shockwave - Version 1.6.0
-------------------------------------

* [CHANGE-66] - Add Google Guava as Maven dependency
* [CHANGE-67] - Use Google Guava Preconditions
* [CHANGE-70] - Use Google Guava Collection Utilities
* [CHANGE-56] - Change threading logic
* [CHANGE-57] - Refactor Castling to use bits instead of array
* [CHANGE-58] - Merge position pieces into one array
* [CHANGE-59] - Generify MoveList
* [CHANGE-60] - Use Hamcrest for Unit Testing
* [CHANGE-62] - Add @NotNull/@Nullable annotations and remove assertions
* [CHANGE-63] - Switch from Cobertura to JaCoCo
* [CHANGE-64] - Statically Import Members where possible
* [CHANGE-65] - Use Google Guava for non-critical code
* [CHANGE-68] - Remove skeleton branch
* [CHANGE-69] - Refactoring 1.6
* [CHANGE-71] - Remove default target in switch statement where possible
* [CHANGE-53] - Add Main class as starting point
* [CHANGE-54] - Remove unused board constructor from Chess 960 id
* [CHANGE-55] - Decouple protocol from internal models

Release Notes - Shockwave - Version 1.5
-----------------------------------

* [CHANGE-41] - Add skeleton files for Shockwave C++ Edition
* [CHANGE-42] - Add Shockwave C++ Edition
* [CHANGE-46] - Add perft as command line argument
* [CHANGE-47] - Add Tempo
* [CHANGE-45] - Refactor Java code to match C++ code base
* [CHANGE-51] - Do move legality check in search
* [CHANGE-43] - Remove VersionInfo for simplicity
* [CHANGE-44] - Remove Configuration for simplicity
* [CHANGE-48] - Remove tapered eval for simplicity
* [CHANGE-49] - Remove castling evaluation for simplicity
* [CHANGE-50] - Remove staged move generator
* [CHANGE-52] - Remove check extensions

Release Notes - Shockwave - Version 1.4.0
-------------------------------------

* [CHANGE-31] - Change to MIT license
* [CHANGE-34] - Using Maven as build tool
* [CHANGE-30] - Change logo file format to bmp
* [CHANGE-39] - Add check extension
* [CHANGE-38] - Rewrite in check testing
* [CHANGE-33] - Remove IntelliJ IDEA settings
* [CHANGE-24] - Remove unused MASK
* [CHANGE-28] - Remove unused methods and refactor tests
* [CHANGE-19] - Remove testing and add integration source set
* [CHANGE-25] - Split Castling into Castling and Type
* [CHANGE-27] - Refactor Piece constants
* [CHANGE-26] - Refactor NOTYPE to NOMOVETYPE
* [CHANGE-32] - Make Zobrist a static utility class
* [CHANGE-35] - Rewrite move conversion from JCPI
* [CHANGE-29] - Replace assertions with exceptions
* [CHANGE-22] - Do not throw exception in isValid()
* [CHANGE-21] - Use long instead of int for number of nodes
* [CHANGE-20] - Fix Color in setFullMoveNumber()
* [CHANGE-23] - Fix insufficient material

Release Notes - Shockwave - Version 1.3.0
-------------------------------------

* Refactor variables and methods to use common namings
* Cleanup code to improve readability
* Build release and debug binaries
* Extract PV from search
* Add more comments
* Add Int* model classes from JCPI to show their implementation
* Add mobility evaluation
* Add hasInsufficientMaterial()
* Add incremental material evaluation
* Add pseudo-legal move generator
* Remove repetitionTable and replace with stack search
* Improve performance in isAttacked()
* Fix best move update on abort
* Fix MAX_PLY and CHECKMATE_THRESHOLD bound usage
* Fix time management with fixed time search

Release Notes - Shockwave - Version 1.2.0
-------------------------------------

* Add more comments
* Cleanup code to improve readability
* Remove save/restore move list in Iterative Deepening
* Abort search if depth is equal to mate distance
* Add support for UCI seldepth
* Merge get*Generators() to improve readability

Release Notes - Shockwave - Version 1.2.0.0
-------------------------------------

* Add more comments
* Cleanup code to improve readability
* Add logo created by Silvian Ruxy Sylwyka
* Create skeleton and master branch

Release Notes - Shockwave - Version 2.0.0.0
-------------------------------------

* Initial release
