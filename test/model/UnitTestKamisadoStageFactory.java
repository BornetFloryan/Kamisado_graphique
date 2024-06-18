package model;

import boardifier.model.Model;
import boardifier.model.TextElement;
import control.K_ControllerMouse;
import control.K_Controller;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Screen;
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
    private KamisadoStageModel stageModel;
    private KamisadoStageFactory stageFactory;
    private HoleBoard board;
    private TextElement text;
    private double width;
    private double height;
    private double boardX;
    private double boardY;
    private double boardWidth;
    private double boardHeight;


    @BeforeEach
    void setUp(){
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

                model.addHumanPlayer("Player X");
                model.addComputerPlayer("Computer O");

                stageModel = new KamisadoStageModel("Kamisado", model);
                stageFactory = new KamisadoStageFactory(stageModel, width, height);
                stageFactory.setup();

                board = stageModel.getBoard();
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
