package control;

import boardifier.control.ControllerAction;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import view.K_GameModePane;
import view.K_HomeRootPane;
import view.KamisadoView;

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
            startGame();
        }
        else if (event.getSource() == rootPane.getPvCButton()) {
            System.out.println("Player vs Computer");
        }
        else if (event.getSource() == rootPane.getCvCButton()) {
            System.out.println("Computer vs Computer");
        }
        else if (event.getSource() == rootPane.getBackToHomeButton()) {
            view = new KamisadoView(model, stage, new K_HomeRootPane(rootPane.getWidth(), rootPane.getHeight()));
            control.setControlAction(new K_ControllerMenueAction(model, view, (KamisadoController) control, stage));
        }
    }

    private Dialog<Pair<String, String>> getPlayerNamesDialog() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
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
                return new Pair<>(player1NameField.getText(), player2NameField.getText());
            }
            return null;
        });

        return dialog;
    }

    private void startGame() {
        stage.setFullScreen(false);

        Dialog<Pair<String, String>> dialog = getPlayerNamesDialog();
        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(playerNames -> {
            String player1Name = playerNames.getKey().isEmpty() ? "Player 1" : playerNames.getKey();
            String player2Name = playerNames.getValue().isEmpty() ? "Player 2" : playerNames.getValue();

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
}
