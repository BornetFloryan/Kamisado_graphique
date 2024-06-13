package view;

import boardifier.model.Model;
import boardifier.view.View;
import boardifier.view.RootPane;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.scene.control.MenuBar;

public class KamisadoView extends View {
    public KamisadoView(Model model, Stage stage, RootPane rootPane) {
        super(model, stage, rootPane);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
    }

    public RootPane getRootPane() {
        return rootPane;
    }
}
