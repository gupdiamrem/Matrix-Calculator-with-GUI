package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ErrorWindowController {

    @FXML
    private Text errorText; // This is now a Text node

    // Method to set the error message dynamically
    public void setErrorMessage(int errorCode) {

        switch (errorCode) {
            case 1:
                errorText.setText(
                        "Number of columns in the first matrix must equal number of rows in the second matrix.");
                break;
            case 2:
                errorText.setText("Matrices must have the same dimensions for addition.");
                break;
            case 3:
                errorText.setText("Matrices must have the same dimensions for subtraction.");
                break;
            case 4:
                // errorText.setText();
                break;

        }

        // errorText.setText(message);
    }

    // Close the error window (optional close button functionality)
    @FXML
    public void handleClose(ActionEvent event) {
        Stage stage = (Stage) errorText.getScene().getWindow();
        stage.close();
    }
}
