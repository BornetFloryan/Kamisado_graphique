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
    private static final List<int[]> winingMoveX = new ArrayList<>();
    private static final List<int[]> winingMoveO = new ArrayList<>();


    static {
        for (int i = 0; i < 8; i++) {
            winingMoveX.add(new int[]{i, 0});
            winingMoveO.add(new int[]{i, 7});
        }
    }


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
        setLoosingMove(stage, board, fromX, fromY, tree);


        System.out.println("Tree:");
        tree.displayTree();


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

    private void setLoosingMove(KamisadoStageModel stage, HoleBoard board, int fromX, int fromY, Tree tree) {
        MinimalBoard[][] minimalBoardBase = stage.createMinimalBoard(board);
        List<Integer[]> validMoveCurrentPlayer = getValidCurrentPlayerMove(board);

        for (Integer[] move : validMoveCurrentPlayer) {
            MinimalBoard[][] minimalBoard = cloneMinimalBoard(minimalBoardBase);
            int row = move[0];
            int col = move[1];


            minimalBoard[row][col] = minimalBoardBase[fromX][fromY];
            minimalBoard[fromX][fromY] = new MinimalBoard('N', null);

            String moveColorLock = minimalBoard[row][col].getColor();
            char enemyName = stage.getCurrentPlayerName().equals(model.getPlayers().get(0).getName()) ? 'O' : 'X';
            int[] coordPawnEnemy = findPawnFrom(minimalBoardBase, moveColorLock, enemyName);


            List<Integer[]> enemyValidMove = getValidCellsMove(minimalBoard, coordPawnEnemy[1], coordPawnEnemy[0], enemyName);


            System.out.println("winingMoveX:");
            for (int[] winingMove : winingMoveX) {
                System.out.println("\t" + winingMove[0] + " " + winingMove[1]);
            }

            System.out.println("winingMoveO:");
            for (int[] winingMove : winingMoveO) {
                System.out.println("\t" + winingMove[0] + " " + winingMove[1]);
            }


            for (Integer[] enemyMove : enemyValidMove) {
                int[] to = new int[]{enemyMove[0], enemyMove[1]};
                System.out.println("to = " + to[0] + " " + to[1]);
                if (winingMoveX.contains(to) || winingMoveO.contains(to)) {
                    tree.add(-50, to);
                }
            }
        }
    }

    private List<Integer[]> getValidCellsMove(MinimalBoard[][] minimalBoard, int row, int col, char playerName) {
        List<Integer[]> lst = new ArrayList<>();
        int[][] directions = playerName == 'X' ? new int[][]{{0, -1}, {1, -1}, {-1, -1}} : new int[][]{{0, 1}, {1, 1}, {-1, 1}};

        for (int[] dir : directions) {
            int dx = dir[0], dy = dir[1];
            int x = row + dx, y = col + dy;



            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                if (minimalBoard[y][x].getSymbol() == 'N') {
                    lst.add(new Integer[]{x, y});
                } else {
                    break;
                }
                x += dx;
                y += dy;
            }
        }

        return lst;
    }

    private int[] findPawnFrom(MinimalBoard[][] minimalBoard, String color, char playerName) {
        for (int i = 0; i < minimalBoard.length; i++) {
            for (int j = 0; j < minimalBoard[i].length; j++) {


                if (minimalBoard[i][j].getSymbol() == 'N' || minimalBoard[i][j].getColor() == null) {
                    continue;
                }

//                System.out.println("------------------------------------------------------------------------------------------------");
//                System.out.println("minimalBoard[i][j].getSymbol() = " + minimalBoard[i][j].getSymbol());
//                System.out.println("minimalBoard[i][j].getColor() = " + minimalBoard[i][j].getColor());
//                System.out.println("minimalBoard[i][j].getSymbol() == playerName = " + (minimalBoard[i][j].getSymbol() == playerName));
//                System.out.println("Color = " + color);
//                System.out.println("minimalBoard[i][j].getColor().equals(color) = " + minimalBoard[i][j].getColor().equals(color));
//                System.out.println("------------------------------------------------------------------------------------------------");

                if (minimalBoard[i][j].getColor().equals(color) && minimalBoard[i][j].getSymbol() == playerName) {
                    return new int[]{j, i};
                }
            }
        }
        return null;
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
