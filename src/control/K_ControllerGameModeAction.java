package control;

import boardifier.control.ControllerAction;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.AISelector;
import view.K_GameModePane;
import view.K_HomeRootPane;
import view.KamisadoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class K_ControllerGameModeAction extends ControllerAction implements EventHandler<ActionEvent> {
    private KamisadoView view;
    private final K_GameModePane rootPane;
    private final Stage stage;

    public K_ControllerGameModeAction(Model model, View view, KamisadoController control, Stage stage) {
        super(model, view, control);
        this.view = (KamisadoView) view;
        this.stage = stage;

        // get root pane
        this.rootPane = (K_GameModePane) this.view.getRootPane();

        this.rootPane.getPvPButton().setOnAction(this);
        this.rootPane.getPvCButton().setOnAction(this);
        this.rootPane.getCvCButton().setOnAction(this);
        this.rootPane.getBackToHomeButton().setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == rootPane.getPvPButton()) {
            startGamePvP();
        }
        else if (event.getSource() == rootPane.getPvCButton()) {
            startGamePvC();
        }
        else if (event.getSource() == rootPane.getCvCButton()) {
            startGameCvC();
        }
        else if (event.getSource() == rootPane.getBackToHomeButton()) {
            view = new KamisadoView(model, stage, new K_HomeRootPane(rootPane.getWidth(), rootPane.getHeight()));
            control.setControlAction(new K_ControllerMenueAction(model, view, (KamisadoController) control, stage));
        }
    }

    private Dialog<List<String>> getPvPDialog() {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Player Names");

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField player1NameField = new TextField();
        player1NameField.setPromptText("Player 1");
        TextField player2NameField = new TextField();
        player2NameField.setPromptText("Player 2");

        grid.add(new Label("Player 1 Name:"), 0, 0);
        grid.add(player1NameField, 1, 0);
        grid.add(new Label("Player 2 Name:"), 0, 1);
        grid.add(player2NameField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                List<String> result = new ArrayList<>();
                result.add(player1NameField.getText());
                result.add(player2NameField.getText());
                return result;
            }
            return null;
        });

        return dialog;
    }

    private Dialog<List<String>> getPvCDialog() {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Player and Computer Settings");

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField playerNameField = new TextField();
        playerNameField.setPromptText("Player Name");

        TextField computerNameField = new TextField();
        computerNameField.setPromptText("Computer Name");

        ComboBox<String> difficultyComboBox = new ComboBox<>();
        difficultyComboBox.getItems().addAll("Easy", "Hard");
        difficultyComboBox.getSelectionModel().selectFirst();

        grid.add(new Label("Player Name:"), 0, 0);
        grid.add(playerNameField, 1, 0);
        grid.add(new Label("Computer Name:"), 0, 1);
        grid.add(computerNameField, 1, 1);
        grid.add(new Label("Computer Difficulty:"), 0, 2);
        grid.add(difficultyComboBox, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                List<String> result = new ArrayList<>();
                result.add(playerNameField.getText());
                result.add(computerNameField.getText());
                result.add(difficultyComboBox.getValue());
                return result;
            }
            return null;
        });

        return dialog;
    }

    private Dialog<List<String>> getCpCDialog() {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Computer Settings");

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField computer1NameField = new TextField();
        computer1NameField.setPromptText("Computer 1 Name");

        TextField computer2NameField = new TextField();
        computer2NameField.setPromptText("Computer 2 Name");

        ComboBox<String> difficultyComboBox1 = new ComboBox<>();
        difficultyComboBox1.getItems().addAll("Easy", "Hard");
        difficultyComboBox1.getSelectionModel().selectFirst();

        ComboBox<String> difficultyComboBox2 = new ComboBox<>();
        difficultyComboBox2.getItems().addAll("Easy", "Hard");
        difficultyComboBox2.getSelectionModel().selectFirst();

        grid.add(new Label("Computer 1 Name:"), 0, 0);
        grid.add(computer1NameField, 1, 0);
        grid.add(new Label("Computer 1 Difficulty:"), 0, 1);
        grid.add(difficultyComboBox1, 1, 1);
        grid.add(new Label("Computer 2 Name:"), 0, 2);
        grid.add(computer2NameField, 1, 2);
        grid.add(new Label("Computer 2 Difficulty:"), 0, 3);
        grid.add(difficultyComboBox2, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                List<String> result = new ArrayList<>();
                result.add(computer1NameField.getText());
                result.add(difficultyComboBox1.getValue());
                result.add(computer2NameField.getText());
                result.add(difficultyComboBox2.getValue());
                return result;
            }
            return null;
        });

        return dialog;
    }


    private void startGamePvP() {
        stage.setFullScreen(false);

        model.getPlayers().clear();

        Dialog<List<String>> dialog = getPvPDialog();
        Optional<List<String>> result = dialog.showAndWait();

        result.ifPresent(playerNames -> {
            String player1Name = playerNames.get(0).isEmpty() ? "Player 1" : playerNames.get(0);
            String player2Name = playerNames.get(1).isEmpty() ? "Player 2" : playerNames.get(1);

            model.addHumanPlayer(player1Name);
            model.addHumanPlayer(player2Name);
        });

        stage.setFullScreen(true);
        try {
            control.startGame();
            stage.setFullScreen(true);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void startGamePvC() {
        stage.setFullScreen(false);

        model.getPlayers().clear();

        Dialog<List<String>> dialog = getPvCDialog();
        Optional<List<String>> result = dialog.showAndWait();

        result.ifPresent(playerNames -> {
            String player1Name = playerNames.get(0).isEmpty() ? "Player 1" : playerNames.get(0);
            String computer1Name = playerNames.get(1).isEmpty() ? "Computer 2" : playerNames.get(1);

            if (playerNames.get(2).equals("Easy"))
                AISelector.setAI(1, "Easy");
            else {
                AISelector.setAI(1, "Hard");
            }

            model.addHumanPlayer(player1Name);
            model.addComputerPlayer(computer1Name);
        });

        stage.setFullScreen(true);
        try {
            control.startGame();
            stage.setFullScreen(true);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void startGameCvC() {
        stage.setFullScreen(false);

        model.getPlayers().clear();

        Dialog<List<String>> dialog = getCpCDialog();
        Optional<List<String>> result = dialog.showAndWait();

        result.ifPresent(playerNames -> {
            String computer1Name = playerNames.get(0).isEmpty() ? "Computer 1" : playerNames.get(0);
            String computer2Name = playerNames.get(2).isEmpty() ? "Computer 2" : playerNames.get(2);

            if (playerNames.get(1).equals("Easy"))
                AISelector.setAI(0, "Easy");
            else {
                AISelector.setAI(0, "Hard");
            }

            if (playerNames.get(3).equals("Easy"))
                AISelector.setAI(1, "Easy");
            else {
                AISelector.setAI(1, "Hard");
            }

            model.addComputerPlayer(computer1Name);
            model.addComputerPlayer(computer2Name);
        });

        stage.setFullScreen(true);
        try {
            control.startGame();

            stage.setFullScreen(true);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
