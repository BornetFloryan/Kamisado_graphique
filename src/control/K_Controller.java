package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.control.Logger;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.view.View;
import model.AISelector;
import model.K_StageModel;

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
        K_StageModel stageModel = (K_StageModel) model.getGameStage();
        stageModel.computePartyResult();

        model.setNextPlayer();
        Player p = model.getCurrentPlayer();
        stageModel.getPlayerName().setText(p.getName());


        if (stageModel.playerCanPlay(model.getIdPlayer())) {
            play();
        }
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

            if (((K_StageModel) model.getGameStage()).getLockedColor() != null) {
                K_ControllerMouse mouse = ((K_ControllerMouse) controlMouse);
                mouse.setPawnFromLockedColor((K_StageModel) model.getGameStage(), ((K_StageModel) model.getGameStage()).getBoard(), model.getCurrentPlayer());
            }

        }
    }
}
