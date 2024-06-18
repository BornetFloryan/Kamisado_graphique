package control;

import boardifier.control.StageFactory;
import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.view.RootPane;
import boardifier.view.View;
import javafx.embed.swing.JFXPanel;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.K_Board;
import model.K_StageModel;
import org.junit.jupiter.api.BeforeEach;
import javafx.application.Platform;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

public class UnitTestKControllerMouse {
    private Model model;
    private Stage stage;
    private RootPane rootPane;
    private View view;
    private K_Controller controller;
    private K_ControllerMouse KcontrollerMouse;
    private K_Board board;
    private MouseEvent event;
    private MouseButton button;
    double x;
    double y;
    private K_StageModel stageModel;
    private GameElement element;
    private double width;
    private double height;
    private double boardWidth;
    private double boardHeight;
    private double boardX;
    private double boardY;

    @BeforeEach
    void setUp() {
        final CountDownLatch latch = new CountDownLatch(1);

        new JFXPanel();

        Platform.runLater(() -> {
            try {
                width = Screen.getPrimary().getBounds().getWidth();
                height = Screen.getPrimary().getBounds().getHeight();

                boardWidth = width * 0.8;
                boardHeight = height * 0.9;

                boardX = (width - boardWidth) / 2;
                boardY = (height - boardHeight) / 2;

                model = new Model();
                stage = new Stage();
                rootPane = new RootPane();

                view = new View(model, stage, rootPane);

                model.addHumanPlayer("Player X");
                model.addHumanPlayer("Player O");

                StageFactory.registerModelAndView("kamisado", "model.K_StageModel", "view.K_StageView");
                controller = new K_Controller(model, view);
                KcontrollerMouse = new K_ControllerMouse(model, view, controller);

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

        stageModel = (K_StageModel) model.getGameStage();

        board = stageModel.getBoard();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                element = board.getElement(i, j);
                if (element != null) {
                    x = element.getX() + boardY;
                    y = element.getY() + boardX;

                    model.setCaptureMouseEvent(false);

                    event = new MouseEvent(MouseEvent.MOUSE_CLICKED, x, y, x, y, button, clickCount,
                            false, false, false, false, true, false, false, true,
                            false, false, null);

                    KcontrollerMouse.handle(event);

                    model.setCaptureMouseEvent(true);

                    event = new MouseEvent(MouseEvent.MOUSE_CLICKED, x, y, x, y, button, clickCount,
                            false, false, false, false, true, false, false, true,
                            false, false, null);

                    KcontrollerMouse.handle(event);
                }
            }
        }


        //xPawn

        //Select Pawn location
        element = board.getElement(7, 0);
        element.getGameStage().setState(1);

        x = element.getX() + boardY;
        y = element.getY() + boardX;

        event = new MouseEvent(MouseEvent.MOUSE_CLICKED, x, y, x, y, button, clickCount,
                false, false, false, false, true, false, false, true,
                false, false, null);
        KcontrollerMouse.handle(event);

        //Move Pawn location
        x = element.getX() + boardY;
        y = element.getY() + boardX - 70;

        event = new MouseEvent(MouseEvent.MOUSE_CLICKED, x, y, x, y, button, clickCount,
                false, false, false, false, true, false, false, true,
                false, false, null);
        KcontrollerMouse.handle(event);


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

        x = element.getX() + boardY;
        y = element.getY() + boardX;

        event = new MouseEvent(MouseEvent.MOUSE_CLICKED, x, y, x, y, button, clickCount,
                false, false, false, false, true, false, false, true,
                false, false, null);
        KcontrollerMouse.handle(event);

        //Move Pawn location
        x = element.getX() + boardY;
        y = element.getY() + boardX + 70;

        event = new MouseEvent(MouseEvent.MOUSE_CLICKED, x, y, x, y, button, clickCount,
                false, false, false, false, true, false, false, true,
                false, false, null);
        KcontrollerMouse.handle(event);
    }
}

