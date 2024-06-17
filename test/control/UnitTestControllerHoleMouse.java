package control;

import boardifier.control.StageFactory;
import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.view.RootPane;
import boardifier.view.View;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.HoleBoard;
import model.KamisadoStageModel;
import model.Pawn;
import org.junit.jupiter.api.BeforeEach;
import javafx.application.Platform;
import org.junit.jupiter.api.Test;
import view.K_Color;

import java.util.concurrent.CountDownLatch;

public class UnitTestControllerHoleMouse {
    private Model model;
    private Stage stage;
    private RootPane rootPane;
    private View view;
    private KamisadoController controller;
    private ControllerHoleMouse controllerHoleMouse;
    private HoleBoard board;
    private MouseEvent event;
    private MouseButton button;
    double x;
    double y;
    private KamisadoStageModel stageModel;
    private GameElement element;
    private double width = 1920;
    private double height = 1080;
    private double boardWidth = width * 0.8 ;
    private double boardHeight = height * 0.9;
    private double boardX = (width - boardWidth) / 2;
    private double boardY = (height - boardHeight) / 2;

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

                model.addHumanPlayer("Player X");
                model.addHumanPlayer("Player O");

                StageFactory.registerModelAndView("kamisado", "model.KamisadoStageModel", "view.KamisadoStageView");
                controller = new KamisadoController(model, view);
                controllerHoleMouse = new ControllerHoleMouse(model, view, controller);

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

    @Test
    void testHandle() {
        button = MouseButton.PRIMARY;
        int clickCount = 1;

        stageModel = (KamisadoStageModel) model.getGameStage();

        board = stageModel.getBoard();


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                element = board.getElement(i, j);
                if (element != null) {
                    x = element.getX() + boardY - 35;
                    y = element.getY() + boardX - 35;

                    model.setCaptureMouseEvent(false);

                    event = new MouseEvent(MouseEvent.MOUSE_CLICKED, x, y, x, y, button, clickCount,
                            false, false, false, false, true, false, false, true,
                            false, false, null);

                    controllerHoleMouse.handle(event);

                    model.setCaptureMouseEvent(true);

                    event = new MouseEvent(MouseEvent.MOUSE_CLICKED, x, y, x, y, button, clickCount,
                            false, false, false, false, true, false, false, true,
                            false, false, null);

                    controllerHoleMouse.handle(event);
                }
            }
        }


        //xPawn

        //Select Pawn location
        element = board.getElement(7, 0);
        element.getGameStage().setState(1);

        x = element.getX() + boardY - 35;
        y = element.getY() + boardX - 35;

        event = new MouseEvent(MouseEvent.MOUSE_CLICKED, x, y, x, y, button, clickCount,
                false, false, false, false, true, false, false, true,
                false, false, null);
        controllerHoleMouse.handle(event);

        //Move Pawn location
        x = element.getX() + boardY - 35;
        y = element.getY() + boardX - 105;

        event = new MouseEvent(MouseEvent.MOUSE_CLICKED, x, y, x, y, button, clickCount,
                false, false, false, false, true, false, false, true,
                false, false, null);
        controllerHoleMouse.handle(event);


        //Change player
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


        //oPawn

        //Select Pawn location
        element = board.getElement(0, 2);
        element.getGameStage().setState(1);

        x = element.getX() + boardY - 35;
        y = element.getY() + boardX - 35;

        event = new MouseEvent(MouseEvent.MOUSE_CLICKED, x, y, x, y, button, clickCount,
                false, false, false, false, true, false, false, true,
                false, false, null);
        controllerHoleMouse.handle(event);

        //Move Pawn location
        x = element.getX() + boardY - 35;
        y = element.getY() + boardX + 35;

        event = new MouseEvent(MouseEvent.MOUSE_CLICKED, x, y, x, y, button, clickCount,
                false, false, false, false, true, false, false, true,
                false, false, null);
        controllerHoleMouse.handle(event);
    }
}
