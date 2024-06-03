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
    // Uncomment next line if the example with a main container is used. see end of HoleStageFactory and HoleStageView
    //private ContainerElement mainContainer;

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

    public void setBoard(HoleBoard board) {
        this.board = board;
    }

    public void setOPawns(Pawn[] oPawns) {
        this.OPawns = oPawns;
    }

    public Pawn[] getXPawns() {
        return XPawns;
    }

    public Pawn[] getOPawns() {
        return OPawns;
    }

    public void setXPawns(Pawn[] xPawns) {
        this.XPawns = xPawns;
    }

    public ContainerElement getBoard() {
        return board;
    }
}
