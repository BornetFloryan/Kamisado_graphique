package view;

import boardifier.model.Model;
import boardifier.view.View;
import boardifier.view.RootPane;

import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class K_View extends View {
    public K_View(Model model, Stage stage, RootPane rootPane) {
        super(model, stage, rootPane);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    }

    public RootPane getRootPane() {
        return rootPane;
    }
}
