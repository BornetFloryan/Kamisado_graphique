package control;

import boardifier.control.ActionFactory;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.model.animation.AnimationTypes;
import model.HoleBoard;
import model.KamisadoStageModel;
import model.Pawn;
import model.Tree;
import view.KamisadoBoardLook;

import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.awt.Point;

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

        System.out.println("Pawn before decide:");
        for (Pawn p : stage.getXPawns()) {
            System.out.println("\tPawn symbol: " + p.getSymbol() + ", Pawn color: #" + p.getColor().substring(2) + ", Pawn from: (" + board.getPawnGridCoordinate(p.getX(), board.getNbRows()) + ", " + board.getPawnGridCoordinate(p.getY(), board.getNbCols()) + ")");
        }
        for (Pawn p : stage.getOPawns()) {
            System.out.println("\tPawn symbol: " + p.getSymbol() + ", Pawn color: #" + p.getColor().substring(2) + ", Pawn from: (" + board.getPawnGridCoordinate(p.getX(), board.getNbRows()) + ", " + board.getPawnGridCoordinate(p.getY(), board.getNbCols()) + ")");
        }






        if (stage.getLockedColor() == null) {
            Pawn[] pawns = stage.getXPawns();
            pawn = pawns[loto.nextInt(pawns.length)];
        } else {
            pawn = stage.searchPawnFromLockedColor();
        }

        List<Point> valid = board.computeValidCells(pawn);

        for (Point point : valid) {
            int[] to = {point.x, point.y};

            tree.add(loto.nextInt(-5, 20), to);
        }

        int[] to = tree.getMaxTo();

        if (to == null) {
            return null;
        }

//        System.out.println("\tPawn symbol: " + pawn.getSymbol() + ", Pawn color: " + pawn.getColor() + ", Pawn from: (" + board.getPawnGridCoordinate(pawn.getX(), board.getNbRows()) + ", " + board.getPawnGridCoordinate(pawn.getY(), board.getNbCols()) + "), to: (" + to[0] + ", " + to[1] + ") color locked: " + stage.getLockedColor());

        KamisadoBoardLook lookBoard = (KamisadoBoardLook) control.getElementLook(board);
        String color = lookBoard.getColor(to[0], to[1]);
        stage.setLockedColor(color);

        System.out.println("Locked color to destination: " + color);


        ActionList action = ActionFactory.generateMoveWithinContainer(control, model, pawn, to[1], to[0]);

        action.setDoEndOfTurn(true);


//        System.out.println("Pawn after decide:");
//        for (Pawn p : stage.getXPawns()) {
//            System.out.println("\tPawn symbol: " + p.getSymbol() + ", Pawn color: " + p.getColor() + ", Pawn from: (" + board.getPawnGridCoordinate(p.getX(), board.getNbRows()) + ", " + board.getPawnGridCoordinate(p.getY(), board.getNbCols()) + ")");
//        }
//        for (Pawn p : stage.getOPawns()) {
//            System.out.println("\tPawn symbol: " + p.getSymbol() + ", Pawn color: " + p.getColor() + ", Pawn from: (" + board.getPawnGridCoordinate(p.getX(), board.getNbRows()) + ", " + board.getPawnGridCoordinate(p.getY(), board.getNbCols()) + ")");
//        }


        return action;
    }

    public String toString() {
        return "Smart AI";
    }
}
