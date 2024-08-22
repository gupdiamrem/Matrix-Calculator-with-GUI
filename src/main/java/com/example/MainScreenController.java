package com.example;

import java.io.IOException;

import com.example.MatrixOperations.matrixCalculatorClasses.Determinant;
import com.example.MatrixOperations.matrixCalculatorClasses.MatrixOperations;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainScreenController {

    private static MainScreenController instance;

    public MainScreenController() {

    }

    @FXML
    private GridPane matrixGrid1;

    @FXML
    private GridPane matrixGrid2;

    @FXML
    private TextField rows1Input;

    @FXML
    private TextField columns1Input;

    @FXML
    private TextField rows2Input;

    @FXML
    private TextField columns2Input;

    int rows1;
    int rows2;
    int columns1;
    int columns2;
    double prod[][];

    public static synchronized MainScreenController getInstance() {
        if (instance == null) {
            instance = new MainScreenController();
        }
        return instance;
    }

    @FXML
    private void switchToAddition() throws IOException {
        App.setRoot("matrixCalcAdditionScreen");
    }

    @FXML
    private void switchToSubtraction() throws IOException {
        App.setRoot("matrixCalcSubtractionScreen");
    }

    @FXML
    private void switchToMultiply() throws IOException {
        App.setRoot("matrixCalcMultiplyScreen");
    }

    @FXML
    private void switchToTranspose() throws IOException {
        App.setRoot("matrixCalcTransposeScreen");
    }

    @FXML
    private void switchToInverse() throws IOException {
        App.setRoot("matrixCalcInverseScreen");
    }

    @FXML
    private void switchToDeterminant() throws IOException {
        App.setRoot("matrixCalcDeterminantScreen");
    }

    public void createMatrixGrid(int rows, int columns, GridPane matrixGrid) {
        matrixGrid.getChildren().clear(); // Clear existing grid contents

        // Iterate through rows and columns to add TextFields
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                TextField textField = new TextField();
                textField.setPrefWidth(30); // Set preferred width
                textField.setPrefHeight(25); // Set preferred height
                matrixGrid.add(textField, j, i); // Add the TextField to the grid
            }
        }
    }

    @FXML
    public void dualMatrixCreation() {
        rows1 = Integer.parseInt(rows1Input.getText());
        columns1 = Integer.parseInt(columns1Input.getText());
        rows2 = Integer.parseInt(rows2Input.getText());
        columns2 = Integer.parseInt(columns2Input.getText());
        createMatrixGrid(rows1, columns1, matrixGrid1);
        createMatrixGrid(rows2, columns2, matrixGrid2);
    }

    @FXML
    public void singleMatrixCreation() {
        rows1 = Integer.parseInt(rows1Input.getText());
        columns1 = Integer.parseInt(columns1Input.getText());
        createMatrixGrid(rows1, columns1, matrixGrid1);
    }

    @FXML
    public void singleDimensionMatrix() {
        rows1 = Integer.parseInt(rows1Input.getText());
        columns1 = Integer.parseInt(rows1Input.getText());
        createMatrixGrid(rows1, columns1, matrixGrid1);
    }

    @FXML
    private double[][] getMatrixData(GridPane grid, int rows, int cols) {
        double[][] matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                TextField textField = (TextField) getNodeFromGridPane(grid, i, j);
                if (textField != null) {
                    matrix[i][j] = Double.parseDouble(textField.getText());
                }
            }
        }
        return matrix;
    }

    private Node getNodeFromGridPane(GridPane gridPane, int row, int col) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return node;
            }
        }
        return null;
    }

    @FXML
    private void submitMultiply() {
        double[][] matrix1 = getMatrixData(matrixGrid1, rows1, columns1);
        double[][] matrix2 = getMatrixData(matrixGrid2, rows2, columns2);
        prod = MatrixOperations.calculateMult(matrix1, matrix2, columns1, rows1, columns2, rows2);
        showResultWindow(prod);
    }

    @FXML
    private void submitAddition() {
        double[][] matrix1 = getMatrixData(matrixGrid1, rows1, columns1);
        double[][] matrix2 = getMatrixData(matrixGrid2, rows2, columns2);
        prod = MatrixOperations.calculateAddition(matrix1, matrix2, rows1, columns1);
        showResultWindow(prod);

    }

    @FXML
    private void submitSubtraction() {
        double[][] matrix1 = getMatrixData(matrixGrid1, rows1, columns1);
        double[][] matrix2 = getMatrixData(matrixGrid2, rows2, columns2);
        prod = MatrixOperations.calculateSubtraction(matrix1, matrix2, rows1, columns1);
        showResultWindow(prod);

    }

    @FXML
    private void submitTranspose() {
        double[][] matrix = getMatrixData(matrixGrid1, rows1, columns1);
        prod = MatrixOperations.transpose(matrix);
        showResultWindow(prod);
    }

    @FXML
    private void submitInverse() {
        double[][] matrix = getMatrixData(matrixGrid1, rows1, rows1);
        double determinant = Determinant.calcDeterimant(matrix);
        prod = MatrixOperations.calcInverse(matrix, determinant);
        showResultWindow(prod);
    }

    @FXML
    private void submitDeterminant() {
        double[][] matrix = getMatrixData(matrixGrid1, rows1, columns1);
        double determiannt = Determinant.calcDeterimant(matrix);
        prod = new double[1][1];
        prod[0][0] = determiannt;
        showResultWindow(prod);
    }

    public void showResultWindow(double[][] result) {
        try {
            // Load the FXML file for the new window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/calculatorScreens/resultWindow.fxml"));
            Parent root = loader.load();

            // Get the controller instance from the loader
            ResultWindowController controller = loader.getController();

            // Set the result matrix in the controller
            controller.setResultMatrix(result);

            // Create a new Stage (window)
            Stage resultStage = new Stage();
            resultStage.setScene(new Scene(root));
            resultStage.setTitle("Results");

            // Show the new window
            resultStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showErrorWindow(int errorCode) {
        try {
            // Load the FXML file for the new window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/calculatorScreens/errorWindow.fxml"));
            Parent root = loader.load();

            // Get the controller instance from the loader
            ErrorWindowController controller = loader.getController();
            controller.setErrorMessage(errorCode);

            // Create a new Stage (window)
            Stage resultStage = new Stage();
            resultStage.setScene(new Scene(root));
            resultStage.setTitle("ERROR");

            // Show the new window
            resultStage.show();

            throw new IllegalArgumentException(
                    "Number of columns in the first matrix must equal number of rows in the second matrix.");

        } catch (Exception e) {
            // e.printStackTrace();
        }
    }
}
