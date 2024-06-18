package view;

import boardifier.model.GameStageModel;
import boardifier.view.GameStageView;
import boardifier.view.TextLook;
import model.K_StageModel;

public class K_StageView extends GameStageView {
    public K_StageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
    }

    @Override
    public void createLooks() {
        K_StageModel model = (K_StageModel) gameStageModel;

        addLook(new TextLook(24, "0x000000", model.getPlayerName()));

        addLook(new K_BoardLook(70, model.getBoard()));

        for (int i = 0; i < 8; i++) {
            addLook(new PawnLook(25, model.getOPawns()[i]));
            addLook(new PawnLook(25, model.getXPawns()[i]));
        }
    }
}
