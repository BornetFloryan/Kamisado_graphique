package view;

import boardifier.model.GameStageModel;
import boardifier.view.GameStageView;
import boardifier.view.TextLook;
import model.KamisadoStageModel;

public class KamisadoStageView extends GameStageView {
    public KamisadoStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }

    @Override
    public void createLooks() {
        KamisadoStageModel model = (KamisadoStageModel) gameStageModel;

        addLook(new TextLook(24, "0x000000", model.getPlayerName()));

        addLook(new KamisadoBoardLook(70, model.getBoard()));

//        addLook(new BlackPawnPotLook(model.getBlackPot()));
//        addLook(new RedPawnPotLook(320, 80, model.getRedPot()));

        for (int i = 0; i < 8; i++) {
            addLook(new PawnLook(25, model.getOPawns()[i]));
            addLook(new PawnLook(25, model.getXPawns()[i]));
        }
    }
}
