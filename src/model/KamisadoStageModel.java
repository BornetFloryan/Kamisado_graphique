package model;

import boardifier.model.*;
import javafx.stage.Screen;
import view.KamisadoBoardLook;

import java.awt.*;
import java.util.List;

public class KamisadoStageModel extends GameStageModel {

    // states
    public static final int STATE_SELECTPAWN = 1; // the player must select a pawn
    public static final int STATE_SELECTDEST = 2; // the player must select a destination

    // size
    private final double width;
    private final double height;

    // stage elements
    private HoleBoard board;
    private Pawn[] XPawns;
    private Pawn[] OPawns;
    private TextElement playerName;
    private String lockedColor = null;

    public KamisadoStageModel(String name, Model model) {
        super(name, model);

        setState(STATE_SELECTPAWN);

        this.width = Screen.getPrimary().getBounds().getWidth();
        this.height = Screen.getPrimary().getBounds().getHeight();
    }

    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new KamisadoStageFactory(this, width, height);
    }

    public TextElement getPlayerName() {
        return playerName;
    }

    public void setPlayerName(TextElement text) {
        playerName = text;
    }

    public Pawn[] getOPawns() {
        return OPawns;
    }

    public void setOPawns(Pawn[] oPawns) {
        this.OPawns = oPawns;
        for (Pawn pawn : oPawns) {
            addElement(pawn);
        }
    }

    public Pawn[] getXPawns() {
        return XPawns;
    }

    public void setXPawns(Pawn[] xPawns) {
        this.XPawns = xPawns;
        for (Pawn pawn : xPawns) {
            addElement(pawn);
        }
    }

    public HoleBoard getBoard() {
        return board;
    }

    public void setBoard(HoleBoard board) {
        this.board = board;
    }

    public String getLockedColor() {
        return lockedColor;
    }

    public void setLockedColor(String color) {
        lockedColor = color;
    }

    public boolean isWin() {
        for (int i = 0; i < 8; i++) {
            Pawn pawnXWin = (Pawn) board.getElement(0, i);
            Pawn pawnOWin = (Pawn) board.getElement(7, i);
            if (pawnXWin != null && pawnXWin.getSymbol() == 'X') {
                model.setIdWinner(0);
                model.stopGame();
                return true;
            } else if (pawnOWin != null && pawnOWin.getSymbol() == 'O') {
                model.setIdWinner(1);
                model.stopGame();
                return true;
            }
        }

        return false;
    }

    public boolean isDraw() {
        boolean player0CanPlay = playerCanPlay(0);
        boolean player1CanPlay = playerCanPlay(1);
        return !player0CanPlay && !player1CanPlay;
    }

    public boolean playerCanPlay(int idPlayer) {

        Pawn pawn = ((KamisadoStageModel) model.getGameStage()).searchPawnFromLockedColor();

        if (idPlayer == 0) {
            for (Pawn p : XPawns) {
                if (p == pawn) {
                    List<Point> valid = board.computeValidCells(pawn);
                    if (!valid.isEmpty()) {
                        return true;
                    }
                }
            }
        } else {
            for (Pawn p : OPawns) {
                if (p == pawn) {
                    List<Point> valid = board.computeValidCells(pawn);
                    if (!valid.isEmpty()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public Pawn searchPawnFromLockedColor() {
        Pawn[] pawns;

        if (getCurrentPlayerName().equals(model.getPlayers().get(0).getName())) {
            pawns = XPawns;
        } else {
            pawns = OPawns;
        }

        for (Pawn pawn : pawns) {
            if (pawn.getColor().equals(lockedColor)) {
                return pawn;
            }
        }

        return null;
    }

    public void computePartyResult() {
        if (isWin()) {
            board.resetReachableCells(false);
        }

//        if (isDraw()) {
//            board.resetReachableCells(false);
//            model.setIdWinner(-1);
//            model.stopGame();
//        }
    }


    public MinimalBoard[][] createMinimalBoard(HoleBoard board, KamisadoBoardLook boardLook) {
        MinimalBoard[][] minimalBoardBase = new MinimalBoard[board.getNbCols()][board.getNbRows()];
        for (int i = 0; i < board.getNbCols(); i++) {
            for (int j = 0; j < board.getNbRows(); j++) {
                GameElement element = board.getElement(i, j);

                if (element instanceof Pawn) {
                    Pawn pawn = (Pawn) element;
                    minimalBoardBase[i][j] = new MinimalBoard(pawn.getSymbol(), pawn.getColor());
                } else {
                    String color = boardLook.getColor(i, j);
                    minimalBoardBase[i][j] = new MinimalBoard('.', color);
                }
            }
        }
        return minimalBoardBase;
    }
}
