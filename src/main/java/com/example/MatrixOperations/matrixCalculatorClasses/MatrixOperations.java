package com.example.MatrixOperations.matrixCalculatorClasses;

import com.example.MainScreenController;

public class MatrixOperations {

    public static double[][] calculateMult(double[][] mat1, double[][] mat2, int mat1Col, int mat1Row, int mat2Col,
            int mat2Row) {

        // Ensure the matrices can be multiplied
        if (mat1Col != mat2Row && mat2Col != mat1Row) {
            MainScreenController.getInstance().showErrorWindow(1);
            return null;
        }

        double[][] prod = new double[mat1Row][mat2Col];
        // Perform multiplication
        for (int i = 0; i < mat1Row; i++) {
            for (int j = 0; j < mat2Col; j++) {
                for (int k = 0; k < mat1Col; k++) {
                    prod[i][j] += mat1[i][k] * mat2[k][j];
                }
            }
        }

        return prod;
    }

    public static double[][] calculateAddition(double[][] mat1, double[][] mat2, int rows, int columns) {
        // The result matrix will have the same dimensions as the input matrices

        if (mat1.length != rows || mat2.length != rows || mat1[0].length != columns || mat2[0].length != columns) {
            MainScreenController.getInstance().showErrorWindow(2);
            return null;
        }

        double[][] sum = new double[rows][columns];

        // Perform matrix addition
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                sum[i][j] = mat1[i][j] + mat2[i][j];
            }
        }

        return sum;
    }

    public static double[][] calculateSubtraction(double[][] mat1, double[][] mat2, int rows, int columns) {
        // Ensure the matrices have the same dimensions
        if (mat1.length != rows || mat2.length != columns || mat1[0].length != rows || mat2[0].length != columns) {
            MainScreenController.getInstance().showErrorWindow(3);
            return null;
        }

        double[][] difference = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                difference[i][j] = mat1[i][j] - mat2[i][j];
            }
        }

        return difference;
    }

    protected static double[][] matrixOfMinors(double[][] matrix) {
        int n = matrix.length;
        double[][] minors = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                minors[i][j] = Determinant.calcDeterimant(minor(matrix, i, j));
            }
        }
        return minors;
    }

    protected static double[][] minor(double[][] matrix, int row, int col) {
        int n = matrix.length;
        double[][] minor = new double[n - 1][n - 1];
        int minorRow = 0, minorCol = 0;
        for (int i = 0; i < n; i++) {
            if (i == row)
                continue;
            minorCol = 0;
            for (int j = 0; j < n; j++) {
                if (j == col)
                    continue;
                minor[minorRow][minorCol] = matrix[i][j];
                minorCol++;
            }
            minorRow++;
        }
        return minor;
    }

    protected static double[][] matrixOfCofactors(double[][] matrix) {
        int n = matrix.length;
        double[][] cofactors = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cofactors[i][j] = Math.pow(-1, i + j) * matrix[i][j];
            }
        }
        return cofactors;
    }

    public static double[][] transpose(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        // Swapping dimensions for transpose
        double[][] transpose = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Swap indices for transposition
                transpose[j][i] = matrix[i][j];
            }
        }
        return transpose;
    }

    protected static boolean isFullRank(double[][] matrix) {
        int numRows = matrix.length;
        int numCols = matrix[0].length;

        // Perform Gaussian elimination to check linear independence
        for (int i = 0; i < numRows; i++) {
            // Perform row reduction to make matrix upper triangular
            for (int j = i + 1; j < numRows; j++) {
                double factor = matrix[j][i] / matrix[i][i];
                for (int k = i; k < numCols; k++) {
                    matrix[j][k] -= factor * matrix[i][k];
                }
            }
        }

        // Check if any diagonal element in reduced form is zero
        for (int i = 0; i < numRows; i++) {
            boolean allZeros = true;
            for (int j = 0; j < numCols; j++) {
                // tolerance for numerical stability
                if (Math.abs(matrix[i][j]) > 1e-10) {
                    allZeros = false;
                    break;
                }
            }
            if (allZeros) {
                // Matrix is not full rank
                return false;
            }
        }
        // Matrix is full rank
        return true;
    }

    public static double[][] calcInverse(double[][] matrix, double determinant) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        if (rows != columns) {

            return null;
        }

        double[][] minors = matrixOfMinors(matrix);
        double[][] cofactors = matrixOfCofactors(minors);
        double[][] adjugate = transpose(cofactors);

        double[][] inverse = new double[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                inverse[i][j] = adjugate[i][j] / determinant;
            }
        }

        return inverse;
    }

}