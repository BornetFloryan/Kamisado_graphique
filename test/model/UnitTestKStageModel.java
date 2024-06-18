package model;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.StageFactory;
import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.view.RootPane;
import boardifier.view.View;
import control.K_Controller;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.K_BoardLook;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTestKStageModel {
    private Model model;
    private View view;
    private Stage stage;
    private RootPane rootPane;
    private K_Controller controller;

    @BeforeAll
    public static void setupSpec() throws Exception {
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("java.awt.headless", "true");
    }

    @BeforeEach
    void setUp(){
        final CountDownLatch latch = new CountDownLatch(1);

        new JFXPanel();

        Platform.runLater(() -> {
            try {
                model = new Model();
                stage = new Stage();
                rootPane = new RootPane();
                view = new View(model, stage, rootPane);

                StageFactory.registerModelAndView("kamisado", "model.K_StageModel", "view.K_StageView");
                controller = new K_Controller(model, view);

                controller.setFirstStageName("kamisado");

                model.getPlayers().clear();
                model.addHumanPlayer("Player X");
                model.addHumanPlayer("Player O");

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
    void testIsWinPlayerX(){
        K_StageModel stageModel = (K_StageModel) model.getGameStage();
        HoleBoard board = stageModel.getBoard();

        List<String> actions = new ArrayList<>();
        actions.add("D3");
        actions.add("E2");
        actions.add("G7");
        actions.add("A7");
        actions.add("F1");

        String fromDefault = "D8";
        Pawn pawn = null;

        for (int i = 0; i < actions.size(); i++) {
            if (fromDefault != null) {
                int fromX = fromDefault.charAt(0) - 'A';
                int fromY = fromDefault.charAt(1) - '1';

                GameElement element = board.getElement(fromY, fromX);

                if (element instanceof Pawn) {
                    pawn = (Pawn) element;
                } else {
                    throw new RuntimeException("No pawn found at " + fromDefault);
                }
            }

            fromDefault = null;
            int destX = actions.get(i).charAt(0) - 'A';
            int destY = actions.get(i).charAt(1) - '1';

            K_BoardLook boardLook = (K_BoardLook) controller.getElementLook(board);
            String color = boardLook.getColor(destY, destX);
            stageModel.setLockedColor(color);

            ActionList actionList = ActionFactory.generateMoveWithinContainer(controller, model, pawn, destY, destX);

            ActionPlayer action = new ActionPlayer(model, controller, actionList);
            actionList.setDoEndOfTurn(true);
            action.start();

            model.setNextPlayer();

            pawn = stageModel.searchPawnFromLockedColor();
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertEquals("Player X", model.getPlayers().get(model.getIdWinner()).getName());
    }

    @Test
    void testIsWinPlayerO(){
        K_StageModel stageModel = (K_StageModel) model.getGameStage();
        HoleBoard board = stageModel.getBoard();

        List<String> actions = new ArrayList<>();
        actions.add("D5");
        actions.add("H7");
        actions.add("C5");
        actions.add("G8");

        String fromDefault = "G8";
        Pawn pawn = null;

        for (int i = 0; i < actions.size(); i++) {
            if (fromDefault != null) {
                int fromX = fromDefault.charAt(0) - 'A';
                int fromY = fromDefault.charAt(1) - '1';

                GameElement element = board.getElement(fromY, fromX);

                if (element instanceof Pawn) {
                    pawn = (Pawn) element;
                } else {
                    throw new RuntimeException("No pawn found at " + fromDefault);
                }
            }

            fromDefault = null;
            int destX = actions.get(i).charAt(0) - 'A';
            int destY = actions.get(i).charAt(1) - '1';

            K_BoardLook boardLook = (K_BoardLook) controller.getElementLook(board);
            String color = boardLook.getColor(destY, destX);
            stageModel.setLockedColor(color);

            ActionList actionList = ActionFactory.generateMoveWithinContainer(controller, model, pawn, destY, destX);

            ActionPlayer action = new ActionPlayer(model, controller, actionList);
            actionList.setDoEndOfTurn(true);
            action.start();

            model.setNextPlayer();

            pawn = stageModel.searchPawnFromLockedColor();

        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertEquals("Player O", model.getPlayers().get(model.getIdWinner()).getName());
    }
}
