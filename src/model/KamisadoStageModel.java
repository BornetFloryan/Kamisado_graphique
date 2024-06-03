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

    public HoleBoard getHoleBoard() {
        return board;
    }
}
