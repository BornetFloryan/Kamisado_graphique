import boardifier.control.StageFactory;
import boardifier.model.Model;
import control.K_ControllerMenueAction;
import control.K_Controller;
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.K_HomeRootPane;
import view.K_View;

public class Kamisado extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();

//        Logger.setLevel(Logger.LOGGER_DEBUG);

        // create the global model
        Model model = new Model();

        // register a single stage for the game, called hole
        StageFactory.registerModelAndView("kamisado", "model.K_StageModel", "view.K_StageView");

        // create the root pane, using the subclass HoleRootPane
        K_HomeRootPane homeRootPane = new K_HomeRootPane(width, height);

        // create the global view.
        K_View view = new K_View(model, stage, homeRootPane);

        // create the controllers.
        K_Controller control = new K_Controller(model, view);
        control.setControlAction(new K_ControllerMenueAction(model, view, control, stage));

        // set the name of the first stage to create when the game is started
        control.setFirstStageName("kamisado");

        // set the stage title
        stage.setTitle("Kamisado Game");

        // show the JavaFx main stage
        stage.show();
    }
}
