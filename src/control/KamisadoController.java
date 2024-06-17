package control;

import boardifier.control.*;
import boardifier.model.*;
import boardifier.view.View;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.AISelector;
import model.HoleBoard;
import model.KamisadoStageModel;
import model.Pawn;

import java.util.ArrayList;
import java.util.List;

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
        HoleBoard board = stageModel.getBoard();

        stageModel.computePartyResult();

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

        KamisadoStageModel stageModel = (KamisadoStageModel) model.getGameStage();
        HoleBoard board = stageModel.getBoard();

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

            ((ControllerHoleMouse) controlMouse).setPawnFromLockedColor(stageModel, board, model.getCurrentPlayer());
        }
    }
}
