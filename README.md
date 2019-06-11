# Simple NotePad


## Code Smells
1. All the functionality is inside one class making it less maintainable and messy. It is not object-oriented and the GUI application should be decoupled from the functionality. --> **Bloater (large class)**

2. public void actionPerformed() is a long, convoluted method and it is not readable, and it is hard to understand --> **Bloater (long method)**

3. The if-else statements (lines 71-116) makes the code clumsy and it is harder to maintain when you need to add more methods/functionality --> **Object Orientation abusers (switch statement)**

4. Lines 112 - 114 for "paste" functionality is unnecessary and does not add anything important to the functionality of the code --> **Dispensable (dead code)**

5. Line 37 and 39 are repeated lines of code --> **Dispensable (duplicate code)**

6. Undo method (lines 31, 56, 116) is not a part of the program specifications and hence is unnecessary --> **Dispensable (speculative generality)**


## Refactoring
1. Created separate classes for implementing file menu functions (FileManager) and edit menu functions (EditProperties) so as to decouple GUI from functionality, and thus make the classes smaller, reusable and object oriented.

2. FileManager & EditProperties classes: Extracted methods for new file, print file, open file, open recents, copy, paste, replace to make it more readable, modular, and maintainable.

3. Lines 55-61: Separately added an ActionListener for each menu option to get rid of if-else block and to make it easier to add newer functionality.

4. Removed first three lines of code for "paste" because it was unnecessary and did not add to the functionality.

5. Removed "undo" components because it was unused code.

6. Lines 76-81: Created new method addToMenu() to reduce repetition of code for adding a separator.

7. Entire program: Changed variable names to more descriptive ones: mb -> menu, em -> editMenu, fm -> fileMenu, d -> display, nf -> newFile, sf -> saveFile, pf -> printFile, c -> copy, p -> paste, pjob -> printerJob.

8. Lines 20-26: Made menu components private because they should not be accessible from outside the class.

9. Entire program: Added comments to make the code more readable and understandable to the client.
