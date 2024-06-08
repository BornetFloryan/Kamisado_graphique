package view;

import boardifier.model.Model;
import boardifier.view.View;
import boardifier.view.RootPane;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.scene.control.MenuBar;

public class KamisadoView extends View {
    private MenuItem menuStart;
    private MenuItem menuHome;
    private MenuItem menuQuit;
    private MenuItem manuGameMode;

    public KamisadoView(Model model, Stage stage, RootPane rootPane) {
        super(model, stage, rootPane);
    }

    @Override
    protected void createMenuBar() {
        menuBar = new MenuBar();

        Menu menu = new Menu("Game");
        menuHome = new MenuItem("Home");
        manuGameMode = new MenuItem("Set game mode");
        menuStart = new MenuItem("New game");
        menuQuit = new MenuItem("Quit");

        menu.getItems().add(menuHome);
        menu.getItems().add(manuGameMode);
        menu.getItems().add(menuStart);
        menu.getItems().add(menuQuit);

        menuBar.getMenus().add(menu);
    }

    public MenuItem getMenuStart() {
        return menuStart;
    }

    public MenuItem getManuGameMode() {
        return manuGameMode;
    }

    public MenuItem getMenuHome() {
        return menuHome;
    }

    public MenuItem getMenuQuit() {
        return menuQuit;
    }
}
