package model;

import boardifier.model.GameStageModel;
import boardifier.model.StageElementsFactory;
import boardifier.model.TextElement;
import view.K_Color;

public class KamisadoStageFactory extends StageElementsFactory {
    private final String[] defaultXpawnsColor = {K_Color.BROWN, K_Color.GREEN, K_Color.RED, K_Color.YELLOW, K_Color.PINK, K_Color.PURPLE, K_Color.BLUE, K_Color.ORANGE};
    private final String[] defaultOpawnsColor = {K_Color.ORANGE, K_Color.BLUE, K_Color.PURPLE, K_Color.PINK, K_Color.YELLOW, K_Color.RED, K_Color.GREEN, K_Color.BROWN};
    private final KamisadoStageModel stageModel;

    public KamisadoStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (KamisadoStageModel) gameStageModel;
    }

    @Override
    public void setup() {


        TextElement text = new TextElement(stageModel.getCurrentPlayerName(), stageModel);
        text.setLocation(10, 20);
        stageModel.setPlayerName(text);

        // Board of Kamisado game
        HoleBoard board = new HoleBoard(10, 10, stageModel);
        stageModel.setBoard(board);
        stageModel.addElement(board);

        // Pawns of Kamisado game
        Pawn[] XPawns = new Pawn[8];
        for (int i = 0; i < 8; i++) {
            Pawn pawn = new Pawn(defaultXpawnsColor[i], 'X', stageModel);
            XPawns[i] = pawn;
            board.addElement(pawn, 7, i);
        }
        stageModel.setXPawns(XPawns);

        Pawn[] OPawns = new Pawn[8];
        for (int i = 0; i < 8; i++) {
            Pawn pawn = new Pawn(defaultOpawnsColor[i], 'O', stageModel);
            OPawns[i] = pawn;
            board.addElement(pawn, 0, i);
        }
        stageModel.setOPawns(OPawns);
    }
}
