package control;

import boardifier.control.ControllerAction;
import boardifier.model.Model;
import boardifier.view.RootPane;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import view.K_GameModePane;
import view.K_HomeRootPane;
import view.KamisadoView;

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
            model.addHumanPlayer("Player X");
            model.addHumanPlayer("Player O");

            try {
                control.startGame();
                stage.setFullScreen(true);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
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
}
