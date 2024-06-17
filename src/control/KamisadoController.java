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
import model.KamisadoStageModel;
import model.Pawn;

public class KamisadoController extends Controller {
    private Thread play;

    public KamisadoController(Model model, View view) {
        super(model, view);
        setControlKey(new ControllerHoleKey(model, view, this));
        setControlMouse(new ControllerHoleMouse(model, view, this));
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

//        if (play != null) {
//            try {
//                play.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        if (stageModel.playerCanPlay(model.getIdPlayer())) {
            // Use the default method to compute next player
            model.setNextPlayer();

            // Get the new player
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

            KamisadoStageModel stageModel = (KamisadoStageModel) model.getGameStage();
            Pawn pawn = stageModel.searchPawnFromLockedColor();

            if (pawn != null) {
                ((ControllerHoleMouse) controlMouse).setPawnFromLockedColor(pawn);

            }
        }
    }
}
