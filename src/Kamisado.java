import boardifier.control.StageFactory;
import boardifier.model.Model;
import control.K_ControllerMenueAction;
import control.KamisadoController;
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.K_HomeRootPane;
import view.KamisadoView;

public class Kamisado extends Application {
    private static int mode;

    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                mode = Integer.parseInt(args[0]);
                if ((mode < 0) || (mode > 2)) mode = 0;
            } catch (NumberFormatException e) {
                mode = 0;
            }
        }
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // create the global model
        Model model = new Model();


        // register a single stage for the game, called hole
        StageFactory.registerModelAndView("kamisado", "model.KamisadoStageModel", "view.KamisadoStageView");

        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();


        // create the root pane, using the subclass HoleRootPane
        K_HomeRootPane homeRootPane = new K_HomeRootPane(width, height);

        // create the global view.
        KamisadoView view = new KamisadoView(model, stage, homeRootPane);

        // create the controllers.
        KamisadoController control = new KamisadoController(model, view);
        control.setControlAction(new K_ControllerMenueAction(model, view, control, stage));

        // set the name of the first stage to create when the game is started
        control.setFirstStageName("kamisado");

        // set the stage title
        stage.setTitle("Kamisado Game");

        // show the JavaFx main stage
        stage.show();
    }
}
