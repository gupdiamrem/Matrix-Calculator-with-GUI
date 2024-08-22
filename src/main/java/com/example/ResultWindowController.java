package com.example;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;

public class ResultWindowController {

    @FXML
    private GridPane resultGrid;

    public void setResultMatrix(double[][] resultMatrix) {
        resultGrid.setAlignment(Pos.CENTER);
        int rows = resultMatrix.length;
        int cols = resultMatrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                TextField textField = new TextField(String.valueOf(resultMatrix[i][j]));
                textField.setEditable(false); // Make the result fields non-editable
                resultGrid.add(textField, j, i);
            }
        }
    }

}
