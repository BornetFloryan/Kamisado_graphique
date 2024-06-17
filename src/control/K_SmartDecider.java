package control;

import boardifier.control.ActionFactory;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.HoleBoard;
import model.KamisadoStageModel;
import model.Pawn;
import model.Tree;
import view.KamisadoBoardLook;

import java.util.Calendar;
import java.util.Random;

public class K_SmartDecider extends Decider {
    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());

    public K_SmartDecider(Model model, Controller controller) {
        super(model, controller);
    }

    public ActionList decide() {
        KamisadoStageModel stage = (KamisadoStageModel) model.getGameStage();
        HoleBoard board = stage.getBoard();
        Tree tree = new Tree();
        Pawn pawn;


        if (stage.getLockedColor() == null) {
            Pawn[] pawns = stage.getXPawns();
            pawn = pawns[loto.nextInt(pawns.length)];
        } else {
            pawn = stage.searchPawnFromLockedColor();
        }

        board.setValidCells(pawn);
        boolean[][] validCells = board.getReachableCells();

        for (int i = 0; i < validCells.length; i++) {
            for (int j = 0; j < validCells[i].length; j++) {
                if (validCells[i][j]) {
                    int[] to = new int[] {i, j};
                    tree.add(loto.nextInt(-10, 10), to);
                }
            }
        }

        int[] to = tree.getMaxTo();

        if (to == null) {
            return null;
        }


        KamisadoBoardLook lookBoard = (KamisadoBoardLook) control.getElementLook(board);
        String color = lookBoard.getColor(to[0], to[1]);
        stage.setLockedColor(color);


        ActionList action = ActionFactory.generateMoveWithinContainer(control, model, pawn, to[0], to[1]);

        action.setDoEndOfTurn(true);


        return action;
    }

    public String toString() {
        return "Smart AI";
    }
}
