package control;

import boardifier.control.Controller;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.HoleBoard;
import model.KamisadoStageModel;
import model.Pawn;
import org.junit.jupiter.api.Test;
import view.KamisadoBoardLook;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UnitTestKNaiveDecider {

    @Test
    public void testDecide() {
        // Create mock objects
        Model model = mock(Model.class);
        Controller controller = mock(Controller.class);
        KamisadoStageModel stage = mock(KamisadoStageModel.class);
        HoleBoard board = mock(HoleBoard.class);
        Pawn pawn = mock(Pawn.class);
        KamisadoBoardLook lookBoard = mock(KamisadoBoardLook.class);

        // Define behavior of mock objects
        when(model.getGameStage()).thenReturn(stage);
        when(stage.getBoard()).thenReturn(board);
        when(stage.getXPawns()).thenReturn(new Pawn[]{pawn});
        when(board.getPawnGridCoordinate(anyInt(), anyInt())).thenReturn(0);
        when(controller.getElementLook(board)).thenReturn(lookBoard);
        when(lookBoard.getColor(anyInt(), anyInt())).thenReturn("color");

        // Define behavior of mock Pawn
        when(pawn.getX()).thenReturn(0.0);
        when(pawn.getY()).thenReturn(0.0);
        when(pawn.getSymbol()).thenReturn('X');

        boolean[][] reachableCells = new boolean[8][8];
        for (int i = 0; i < reachableCells.length; i++) {
            Arrays.fill(reachableCells[i], true);
        }
        when(board.getReachableCells()).thenReturn(reachableCells);
        K_NaiveDecider decider = new K_NaiveDecider(model, controller);

        ActionList result = decider.decide();

        verify(model, times(1)).getGameStage();
        verify(stage, times(1)).getBoard();
        verify(board, times(1)).setValidCells(pawn);
        verify(controller, times(1)).getElementLook(board);
        verify(lookBoard, times(1)).getColor(anyInt(), anyInt());

        // Assert that result is as expected
        assertNotNull(result);
        // Replace the following line with the correct assertion
        assertTrue(result.mustDoEndOfTurn());
    }
}