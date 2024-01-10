Interactive Sudoku Game in Java
===============================

Description
-----------

This Java project implements an interactive Sudoku game. It includes classes for managing the game board (`Board.java`), processing user moves (`UserChoice.java`), and integrating game logic (`Sudoku.java`). The game features real-time validation of user inputs and offers an engaging puzzle-solving experience.

Features
--------

*   Interactive game interface.
*   Real-time move validation according to Sudoku rules.
*   Easy-to-use input system for Sudoku moves.

How to Run
----------

1.  Compile the program: `javac -cp .;./stdlib.jar Board.java UserChoice.java Sudoku.java`
2.  Run the application: `java -cp .;./stdlib.jar Sudoku <N> <game-file>`

Replace `<N>` with the Sudoku size and `<game-file>` with the filename of the Sudoku board to be loaded.

How to Play
-----------

*   Start the game using the command-line as mentioned above.
*   Follow the on-screen prompts to input your moves.
*   The game will provide immediate feedback if a move violates Sudoku rules.

Requirements
------------

*   Java Runtime Environment (JRE)
*   Standard libraries as required (e.g., `stdlib.jar`)

Contributions
-------------

This project is developed by Andreas Demosthenous. Contributions and feedback are welcome.

License
-------

[MIT License](LICENSE.md) - feel free to use and modify this project as you see fit.
