package ru.kozorez;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;


/**
 * class for managing game over window.
 */
public class PopupController {

    Controller controller;

    @FXML
    Label scoreLabel;

    @FXML
    ComboBox<Integer> foodCountComboBox;

    @FXML
    CheckBox withBotCheckBox;

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
        withBotCheckBox.fire();
        foodCountComboBox.getItems().addAll(1, 2, 3, 4, 5);
        foodCountComboBox.getSelectionModel().select(0); // Select the first option
    }

    /**
     * handle the restart button action.
     */
    @FXML
    void restartButtonAction() {
        int selectedFoodCount = foodCountComboBox.getValue();
        boolean withBot = withBotCheckBox.isSelected();
        Stage stage = (Stage) restartButton.getScene().getWindow();
        stage.close();
        controller.setFoodCount(selectedFoodCount);
        controller.setWithBot(withBot);
        controller.gameLoop.start();
        controller.restartGame();
    }
}
