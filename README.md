# LA_Library: Linear Algebra library
## Project description
This is my personal Linear Algebra library written in Java to implement the fundamental algorithms and matrix operations.
## Project's structure
The library can be currently into 5 main folders:
1. ArraysOperations: This folder extends the basic operations between numerical values to arrays in general using an OOP
approach. For X and Y being either scalar, one dimensional array or 2-dimensional array,
the following basic arithmetic operations (element-wise) will be available:
   * X + Y
   * X / Y 
   * X * Y
   * X - Y 
2. Matrices: This folder wraps up the primitive 2d arrays into the main data structures of the library: the Matrix class.
A number of other important types of matrices is implemented as they are crucial for mathematical operations and algorithms:
   * Square matrix
   * Elimination Matrix
   * Permutation Matrix

3. Matrix Operations: This folder implements the different arithmetic (element-wise) and matrix operations such as:
   * Matrix Multiplication
   * Matrix Transpose
   * Reduced Row Echelon Form of a matrix
   * Inverse of a matrix (if exists)
   * LU factorization
   * QR factorization

4. Equations: This folder provides classes to solve some known functions that reveals important characteristics of a matrix such as:
   * $A \cdot x = 0$
   * $A \cdot x = b$

5. Utilities: Folder including a number of helper functions that are not directly related to Linear Algebra

## Future Improvements
The current library is missing a crucial component of Linear Algebra's power which is ***Eigenvalues***. This part is to be implemented next
as it offers a large number of applications and algorithms such as:
* Matrix Decomposition and $n$-th power of a matrix
* Singular Value Decomposition
* solving systems of linear differential equations