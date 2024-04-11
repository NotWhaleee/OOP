package ru.kozorez;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;


/**
 * class for managing game over window.
 */
public class PopupController {

    Stage primaryStage;

    Controller controller;

    @FXML
    private Label scoreLabel;

    @FXML
    private ComboBox<Integer> foodCountComboBox;

    @FXML
    private Button restartButton;

    /**
     * set score.
     *
     * @param score int
     */
    public void setScore(int score) {
        scoreLabel.setText("Score: " + score + "\nSelect the food count:");
    }

    /**
     * initialize the rolling option and restart button.
     */
    public void initialize() {
        foodCountComboBox.getItems().addAll(1, 2, 3, 4, 5);
        foodCountComboBox.getSelectionModel().select(0); // Select the first option
    }

    /**
     * handle the restart button action.
     */
    @FXML
    private void restartButtonAction() {
        int selectedFoodCount = foodCountComboBox.getValue();
        // Restart the game with the selected number of foods
        Stage stage = (Stage) restartButton.getScene().getWindow();
        stage.close();

        primaryStage.setTitle("Sankey!");
        primaryStage.show();
        controller.setFoodCount(selectedFoodCount);

        Controller parentController = (Controller) primaryStage.getUserData();
        parentController.setFoodCount(selectedFoodCount);

        parentController.restartGame();
    }
}
