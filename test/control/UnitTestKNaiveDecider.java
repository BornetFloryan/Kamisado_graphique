package control;

import boardifier.control.Controller;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.HoleBoard;
import model.K_StageModel;
import model.Pawn;
import org.junit.jupiter.api.Test;
import view.K_BoardLook;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UnitTestKNaiveDecider {

    @Test
    public void testDecide() {
        // Create mock objects
        Model model = mock(Model.class);
        Controller controller = mock(Controller.class);
        K_StageModel stage = mock(K_StageModel.class);
        HoleBoard board = mock(HoleBoard.class);
        Pawn pawn = mock(Pawn.class);
        K_BoardLook lookBoard = mock(K_BoardLook.class);

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