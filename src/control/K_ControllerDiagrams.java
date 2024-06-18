package control;

import boardifier.control.ControllerAction;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import view.K_HowToPlayPane;
import view.KamisadoView;

public class K_ControllerDiagrams extends ControllerAction {
    private KamisadoView view;
    private final view.K_DiagramsPane rootPane;
    private final Stage stage;

    public K_ControllerDiagrams(Model model, View view, K_Controller control, Stage stage) {
        super(model, view, control);
        this.view = (KamisadoView) view;
        this.stage = stage;

        // get root pane
        this.rootPane = (view.K_DiagramsPane) this.view.getRootPane();

        this.rootPane.getBackToHowToPlayButton().setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == rootPane.getBackToHowToPlayButton()) {
            view = new KamisadoView(model, stage, new K_HowToPlayPane(rootPane.getWidth(), rootPane.getHeight()));
            control.setControlAction(new K_ControllerHowToPlay(model, view, (K_Controller) control, stage));
        }
    }
}
