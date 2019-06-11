# Simple NotePad

## Code Smells
1. All the functionality is inside one class making it less maintainable and messy. It is not object-oriented and the GUI application should be decoupled from the functionality. --> Bloater (large class)

2. public void actionPerformed() is a long, convoluted method and it is not readable, and it is hard to understand --> Bloater (long method)

3. The if-else statements (lines 71-116) makes the code clumsy and it is harder to maintain when you need to add more methods/functionality --> Object Orientation abusers (switch statement)

4. Lines 112 - 114 for "paste" functionality is unnecessary and does not add anything important to the functionality of the code --> Dispensable (dead code)

5. Line 37 and 39 are repeated lines of code --> Dispensable (duplicate code)

6. Undo method (lines 31, 56, 116) is not a part of the program specifications and hence is unnecessary --> Dispensable (speculative generality)
