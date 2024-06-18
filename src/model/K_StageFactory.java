package model;

import boardifier.model.GameStageModel;
import boardifier.model.StageElementsFactory;
import boardifier.model.TextElement;
import view.K_Color;

public class K_StageFactory extends StageElementsFactory {
    private final String[] defaultXpawnsColor = {K_Color.BROWN, K_Color.GREEN, K_Color.RED, K_Color.YELLOW, K_Color.PINK, K_Color.PURPLE, K_Color.BLUE, K_Color.ORANGE};
    private final String[] defaultOpawnsColor = {K_Color.ORANGE, K_Color.BLUE, K_Color.PURPLE, K_Color.PINK, K_Color.YELLOW, K_Color.RED, K_Color.GREEN, K_Color.BROWN};
    private final K_StageModel stageModel;
    private final double width;
    private final double height;

    public K_StageFactory(GameStageModel gameStageModel, double width, double height) {
        super(gameStageModel);
        stageModel = (K_StageModel) gameStageModel;
        this.width = width;
        this.height = height;
    }

    @Override
    public void setup() {
        // Board of Kamisado game
        double boardWidth = width * 0.8;
        double boardHeight = height * 0.9;
        double boardX = (width - boardWidth) / 2;
        double boardY = (height - boardHeight) / 2;

        TextElement text = new TextElement(stageModel.getCurrentPlayerName(), stageModel);
        text.setLocation(boardX + 50, boardY + 100);
        stageModel.setPlayerName(text);

        K_Board board = new K_Board((int) boardY, (int) boardX, stageModel);
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
