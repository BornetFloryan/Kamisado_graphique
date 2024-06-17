package model;

import boardifier.model.Model;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.K_Color;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTestHoleBoard {
    private Model model;
    private KamisadoStageModel stageModel;
    private KamisadoStageFactory stageFactory;
    private HoleBoard board;
    private double width;
    private double height;
    private Pawn pawn;
    private List<Point> valid;

    @BeforeEach
    void setUp(){
        final CountDownLatch latch = new CountDownLatch(1);

        new JFXPanel();

        Platform.runLater(() -> {
            try {
                width = Screen.getPrimary().getBounds().getWidth();
                height = Screen.getPrimary().getBounds().getHeight();

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
    void testSetValidCells(){
        Pawn[] XPawns = stageModel.getXPawns();
        assertNotNull(XPawns);
        assertEquals(8, XPawns.length);
        for (int i = 0; i < 8; i++) {
            pawn = XPawns[i];
            board.setValidCells(pawn);
            valid = board.computeValidCells(pawn);
            assertNotNull(valid);
        }

        Pawn[] OPawns = stageModel.getOPawns();
        assertNotNull(OPawns);
        assertEquals(8, OPawns.length);
        for (int i = 0; i < 8; i++) {
            pawn = OPawns[i];
            board.setValidCells(pawn);
            valid = board.computeValidCells(pawn);
            assertNotNull(valid);
        }

        pawn = new Pawn(K_Color.BLACK, 'T', stageModel);
        pawn.setLocation(100, 5400);
        valid = board.computeValidCells(pawn);
        assertTrue(valid.isEmpty());
    }
}
