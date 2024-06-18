package control;

import boardifier.control.ControllerAction;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import view.K_GameModePane;
import view.K_HomeRootPane;
import view.K_HowToPlayPane;
import view.K_View;

public class K_ControllerMenueAction extends ControllerAction implements EventHandler<ActionEvent> {
    private final K_HomeRootPane rootPane;
    private final Stage stage;
    private K_View view;

    public K_ControllerMenueAction(Model model, View view, K_Controller control, Stage stage) {
        super(model, view, control);
        this.view = (K_View) view;
        this.stage = stage;

        // get root pane
        this.rootPane = (K_HomeRootPane) this.view.getRootPane();

        this.rootPane.getStartButton().setOnAction(this);
        this.rootPane.getHowToPlayButton().setOnAction(this);
        this.rootPane.getQuitButton().setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == rootPane.getStartButton()) {
            view = new K_View(model, stage, new K_GameModePane(rootPane.getWidth(), rootPane.getHeight()));
            control.setControlAction(new K_ControllerGameModeAction(model, view, (K_Controller) control, stage));
        } else if (event.getSource() == rootPane.getHowToPlayButton()) {
            view = new K_View(model, stage, new K_HowToPlayPane(rootPane.getWidth(), rootPane.getHeight()));
            control.setControlAction(new K_ControllerHowToPlay(model, view, (K_Controller) control, stage));
        } else if (event.getSource() == rootPane.getQuitButton()) {
            System.exit(0);
        }
    }
}
