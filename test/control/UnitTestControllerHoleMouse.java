package control;

import boardifier.control.StageFactory;
import boardifier.model.Model;
import boardifier.view.RootPane;
import boardifier.view.View;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import javafx.application.Platform;

import java.util.concurrent.CountDownLatch;

public class UnitTestControllerHoleMouse {
    Model model;
    Stage stage;
    RootPane rootPane;
    View view;
    KamisadoController controller;


    @BeforeEach
    void setUp() {
        final CountDownLatch latch = new CountDownLatch(1);

        new JFXPanel();

        Platform.runLater(() -> {
            try {
                model = new Model();
                stage = new Stage();
                rootPane = new RootPane();

                view = new View(model, stage, rootPane);

                StageFactory.registerModelAndView("kamisado", "model.KamisadoStageModel", "view.KamisadoStageView");
                controller = new KamisadoController(model, view);

                model.addHumanPlayer("Player X");
                model.addHumanPlayer("Player O");

                controller.setFirstStageName("kamisado");

                try {
                    controller.startGame();
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } finally {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
