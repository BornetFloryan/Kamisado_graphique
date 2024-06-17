package control;

import boardifier.control.ActionFactory;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.control.Logger;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.*;
import view.KamisadoBoardLook;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class K_SmartDecider extends Decider {
    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());

    public K_SmartDecider(Model model, Controller controller) {
        super(model, controller);
    }

    public ActionList decide() {
        Logger.debug("Smart AI is deciding");

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

        int fromX = board.getPawnGridCoordinate(pawn.getY(), board.getNbCols());
        int fromY = board.getPawnGridCoordinate(pawn.getX(), board.getNbRows());

        addToTreeAllValidMoves(board.getReachableCells(), tree);
        setLoosingMove(stage, board, fromX, fromY);

        Node nodeTo = tree.getMaxTo();

        int[] to;

        if (nodeTo.getValue() == 0) {
            to = getRandomMove(board.getReachableCells());
        } else {
            to = nodeTo.getTo();
        }


        if (to == null) {
            return null;
        } else {
            Logger.debug("Move from " + fromX + " " + fromY + " to " + to[0] + " " + to[1]);
        }


        KamisadoBoardLook lookBoard = (KamisadoBoardLook) control.getElementLook(board);
        String color = lookBoard.getColor(to[0], to[1]);
        stage.setLockedColor(color);

        ActionList action = ActionFactory.generateMoveWithinContainer(control, model, pawn, to[0], to[1]);

        action.setDoEndOfTurn(true);

        return action;
    }

    private int[] getRandomMove(boolean[][] reachableCells) {
        List<int[]> validMoves = new ArrayList<>();

        for (int i = 0; i < reachableCells.length; i++) {
            for (int j = 0; j < reachableCells[i].length; j++) {
                if (reachableCells[i][j]) {
                    validMoves.add(new int[]{i, j});
                }
            }
        }

        return validMoves.get(loto.nextInt(validMoves.size()));
    }


    private void addToTreeAllValidMoves(boolean[][] reachableCells, Tree tree) {
        for (int i = 0; i < reachableCells.length; i++) {
            for (int j = 0; j < reachableCells[i].length; j++) {
                if (reachableCells[i][j]) {
                    tree.add(0, new int[]{i, j});
                }
            }
        }
    }

    private void setLoosingMove(KamisadoStageModel stage, HoleBoard board, int fromX, int fromY) {
        MinimalBoard[][] minimalBoardBase = stage.createMinimalBoard(board);
        List<Integer[]> validMoveCurrentPlayer = getValidCurrentPlayerMove(board);

        System.out.println("From: " + fromX + " " + fromY);

        for (Integer[] move : validMoveCurrentPlayer) {
            MinimalBoard[][] minimalBoard = cloneMinimalBoard(minimalBoardBase);
            int row = move[0];
            int col = move[1];

            minimalBoard[col][row] = minimalBoardBase[fromY][fromX];
            minimalBoard[fromY][fromX] = new MinimalBoard('N', null);

            String moveColorLock = minimalBoard[col][row].getColor();
            String enemyName = stage.getCurrentPlayerName().equals(model.getPlayers().get(0).getName()) ? model.getPlayers().get(1).getName() : model.getPlayers().get(0).getName();
//            int[] coordPawnEnemy = findPawnFrom(minimalBoardBase, moveColorLock, enemyName);
        }
    }

    private List<Integer[]> getValidCurrentPlayerMove(HoleBoard board) {
        boolean[][] reachableCells = board.getReachableCells();
        List<Integer[]> validMoveCurrentPlayer = new ArrayList<>();
        for (int i = 0; i < board.getNbCols(); i++) {
            for (int j = 0; j < board.getNbRows(); j++) {
                if (reachableCells[i][j]) {
                    validMoveCurrentPlayer.add(new Integer[]{i, j});
                }
            }
        }
        return validMoveCurrentPlayer;
    }

    private MinimalBoard[][] cloneMinimalBoard(MinimalBoard[][] minimalBoardBase) {
        MinimalBoard[][] minimalBoard = new MinimalBoard[minimalBoardBase.length][minimalBoardBase[0].length];
        for (int i = 0; i < minimalBoardBase.length; i++) {
            for (int j = 0; j < minimalBoardBase[i].length; j++) {
                minimalBoard[i][j] = minimalBoardBase[i][j];
            }
        }
        return minimalBoard;
    }







    private void displayBoard(MinimalBoard[][] minimalBoard) {
        System.out.println("[");
        for (int i = 0; i < minimalBoard.length; i++) {
            System.out.print("\t[");
            for (int j = 0; j < minimalBoard[i].length; j++) {
                if (j == minimalBoard[i].length - 1) {
                    System.out.print(minimalBoard[i][j].getSymbol());
                } else {
                    System.out.print(minimalBoard[i][j].getSymbol() + ", ");
                }
            }
            System.out.println("]");
        }
        System.out.println("]");
    }


//    private void setLoosingMove(HoleStageModel stage, HoleBoard board, String from) {
//        MinimalBoard[][] minimalBoardBase = stage.createMinimalBoard(board);
//        List<String> validMoveCurrentPlayer = getValidCurrentPlayerMove(board);
//        String enemyName = stage.getCurrentPlayerName().contains("X") ? "Player O" : "Player X";
//
//        int rowFrom = from.charAt(0) - 'A';
//        int colFrom = from.charAt(1) - '1';
//
//        for (String move : validMoveCurrentPlayer) {
//            MinimalBoard[][] minimalBoard = cloneMinimalBoard(minimalBoardBase);
//            int row = move.charAt(0) - 'A';
//            int col = move.charAt(1) - '1';
//
//            minimalBoard[col][row] = minimalBoardBase[colFrom][rowFrom];
//            minimalBoard[colFrom][rowFrom] = new MinimalBoard('N', -1);
//
//            String boardColorLock = stage.getBoardColor(stage, view, row, col);
//            int[] coordPawnEnemy = findPawnFrom(minimalBoardBase, boardColorLock, enemyName);
//            List<String> validMoveEnemyPlayer = stage.getValidCellsMove(minimalBoard, coordPawnEnemy[1], coordPawnEnemy[0], enemyName);
//
//            for (String enemyMove : validMoveEnemyPlayer) {
//                if (winingMoveX.contains(enemyMove) || winingMoveO.contains(enemyMove)) {
//                    tree.add(-50, move);
//                }
//            }
//        }
//    }
}
