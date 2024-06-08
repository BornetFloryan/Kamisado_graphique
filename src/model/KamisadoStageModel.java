package model;

import boardifier.model.*;

public class KamisadoStageModel extends GameStageModel {

    // states
    public final static int STATE_SELECTPAWN = 1; // the player must select a pawn
    public final static int STATE_SELECTDEST = 2; // the player must select a destination

    // stage elements
    private HoleBoard board;
    private Pawn[] XPawns;
    private Pawn[] OPawns;
    private TextElement playerName;
    private String lockedColor = null;

    public KamisadoStageModel(String name, Model model) {
        super(name, model);
        state = STATE_SELECTPAWN;
    }

    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new KamisadoStageFactory(this);
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

    public ContainerElement getBoard() {
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
}
