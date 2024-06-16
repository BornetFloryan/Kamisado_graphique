package model;

import boardifier.model.Model;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UnitTestHoleBoard {
    private Model model;
    private KamisadoStageModel stageModel;
    private KamisadoStageFactory stageFactory;
    private HoleBoard board;
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
    void testSetValidCells(){
        Pawn[] XPawns = stageModel.getXPawns();
        assertNotNull(XPawns);
        assertEquals(8, XPawns.length);
        for (int i = 0; i < 8; i++) {
            Pawn pawn = XPawns[i];
            board.setValidCells(pawn);
            List<Point> valid = board.computeValidCells(pawn);
            assertNotNull(valid);
        }

        Pawn[] OPawns = stageModel.getOPawns();
        assertNotNull(OPawns);
        assertEquals(8, OPawns.length);
        for (int i = 0; i < 8; i++) {
            Pawn pawn = OPawns[i];
            board.setValidCells(pawn);
            List<Point> valid = board.computeValidCells(pawn);
            assertNotNull(valid);
        }
    }
}
