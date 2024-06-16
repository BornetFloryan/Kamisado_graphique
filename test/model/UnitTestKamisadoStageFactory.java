package model;

import boardifier.model.Model;
import boardifier.model.TextElement;
import boardifier.view.RootPane;
import boardifier.view.View;
import control.ControllerHoleMouse;
import control.KamisadoController;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.K_Color;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UnitTestKamisadoStageFactory {
    private String[] defaultXpawnsColor = {K_Color.BROWN, K_Color.GREEN, K_Color.RED, K_Color.YELLOW, K_Color.PINK, K_Color.PURPLE, K_Color.BLUE, K_Color.ORANGE};
    private String[] defaultOpawnsColor = {K_Color.ORANGE, K_Color.BLUE, K_Color.PURPLE, K_Color.PINK, K_Color.YELLOW, K_Color.RED, K_Color.GREEN, K_Color.BROWN};
    private Model model;
    private Stage stage;
    private RootPane rootPane;
    private View view;
    private KamisadoStageModel stageModel;
    private KamisadoStageFactory stageFactory;
    private HoleBoard board;
    private KamisadoController controller;
    private ControllerHoleMouse controllerHoleMouse;
    private TextElement text;
    private int width = 800;
    private int height = 800;


    @BeforeEach
    void setUp(){
        final CountDownLatch latch = new CountDownLatch(1);

        new JFXPanel();

        Platform.runLater(() -> {
            try {
                model = new Model();
                model.addHumanPlayer("Player X");
                model.addComputerPlayer("Computer O");

                stageModel = new KamisadoStageModel("Kamisado", model);
                stageFactory = new KamisadoStageFactory(stageModel, width, height);
                stageFactory.setup();

                board = (HoleBoard) stageModel.getBoard();
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
    void testSetup(){
        text = stageModel.getPlayerName();
        double boardWidth = width * 0.8;
        double boardHeight = height * 0.9;
        double boardX = (width - boardWidth) / 2;
        double boardY = (height - boardHeight) / 2;
        assertNotNull(text);
        assertEquals(boardX + 50, text.getX());
        assertEquals(boardY + 100, text.getY());

        assertNotNull(board);
        assertEquals(8, board.getNbRows());
        assertEquals(8, board.getNbCols());
        Pawn[] XPawns = stageModel.getXPawns();
        assertNotNull(XPawns);
        assertEquals(8, XPawns.length);
        for (int i = 0; i < 8; i++) {
            Pawn pawn = XPawns[i];
            assertEquals(defaultXpawnsColor[i], pawn.getColor());
        }

        Pawn[] OPawns = stageModel.getOPawns();
        assertNotNull(OPawns);
        assertEquals(8, OPawns.length);
        for (int i = 0; i < 8; i++) {
            Pawn pawn = OPawns[i];
            assertEquals(defaultOpawnsColor[i], pawn.getColor());
            assertEquals('O', pawn.getSymbol());
        }
    }
}
