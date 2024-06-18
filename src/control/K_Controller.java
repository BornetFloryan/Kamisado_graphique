package control;

import boardifier.control.*;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.view.View;
import model.AISelector;
import model.KamisadoStageModel;

public class K_Controller extends Controller {
    private Thread play;

    public K_Controller(Model model, View view) {
        super(model, view);
        setControlKey(new K_ControllerKey(model, view, this));
        setControlMouse(new K_ControllerMouse(model, view, this));
    }

    @Override
    public void startGame() throws GameException {
        super.startGame();
        play();
    }

    @Override
    public void endOfTurn() {
        KamisadoStageModel stageModel = (KamisadoStageModel) model.getGameStage();
        stageModel.computePartyResult();

        if (stageModel.playerCanPlay(model.getIdPlayer())) {
            model.setNextPlayer();
            Player p = model.getCurrentPlayer();
            stageModel.getPlayerName().setText(p.getName());
        }

        play();
    }

    public void play() {
        Player p = model.getCurrentPlayer();

        if (model.isEndGame()) {
            return;
        }

        if (p.getType() == Player.COMPUTER) {
            Logger.debug("COMPUTER PLAYS");

            Decider decider;
            if (AISelector.getDecider(model.getIdPlayer()).equals("Hard")) {
                decider = new K_SmartDecider(model, this);
            } else {
                decider = new K_NaiveDecider(model, this);
            }

            play = new ActionPlayer(model, this, decider, null);
            play.start();
        } else {
            Logger.debug("PLAYER PLAYS");

            K_ControllerMouse mouse = ((K_ControllerMouse) controlMouse);
            mouse.setPawnFromLockedColor((KamisadoStageModel) model.getGameStage(), ((KamisadoStageModel) model.getGameStage()).getBoard(), model.getCurrentPlayer());
        }
    }
}
