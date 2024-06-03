package control;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.ControllerMouse;
import boardifier.model.Coord2D;
import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.model.animation.AnimationTypes;
import boardifier.view.View;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.HoleBoard;
import model.KamisadoStageModel;
import model.Pawn;
import view.KamisadoBoardLook;

import java.util.List;

/**
 * A basic mouse controller that just grabs the mouse clicks and prints out some informations.
 * It gets the elements of the scene that are at the clicked position and prints them.
 */
public class ControllerHoleMouse extends ControllerMouse implements EventHandler<MouseEvent> {

    public ControllerHoleMouse(Model model, View view, Controller control) {
        super(model, view, control);
    }

    public void handle(MouseEvent event) {
        // if mouse event capture is disabled in the model, just return
        if (!model.isCaptureMouseEvent()) return;

        // get the clic x,y in the whole scene (this includes the menu bar if it exists)
        Coord2D clic = new Coord2D(event.getSceneX(), event.getSceneY());

        // get elements at that position
        List<GameElement> list = control.elementsAt(clic);
        Pawn pawn = null;

        if (model.getGameStage().getState() == KamisadoStageModel.STATE_SELECTPAWN) {
            for (GameElement element : list) {
                if (element.getType() == ElementTypes.getType("pawn")) {
                    pawn = (Pawn) element;
                    element.toggleSelected();
                    model.getGameStage().setState(KamisadoStageModel.STATE_SELECTDEST);
                }
            }
        } else if (model.getGameStage().getState() == KamisadoStageModel.STATE_SELECTDEST) {
            for (GameElement element : list) {
                if (element.isSelected()) {
                    pawn = null;
                    element.toggleSelected();
                    model.getGameStage().setState(KamisadoStageModel.STATE_SELECTPAWN);
                }
            }
        }

        // Check if pawn is still null
        if (pawn == null) return;

        KamisadoStageModel stageModel = (KamisadoStageModel) model.getGameStage();
        HoleBoard board = (HoleBoard) stageModel.getBoard();

        // thirdly, get the clicked cell in the 3x3 board
        KamisadoBoardLook lookBoard = (KamisadoBoardLook) control.getElementLook(board);

        int[] to = lookBoard.getCellFromSceneLocation(clic);

        // get the cell in the pot that owns the selected pawn
        int[] from = board.getElementCell(pawn);

        System.out.println("from: " + from[0] + " " + from[1]);
        System.out.println("to: " + to[0] + " " + to[1]);

        // Uncomment and adjust the following block if needed
         if (board.canReachCell(to[0], to[1])) {
             ActionList actions = ActionFactory.generatePutInContainer(control, model, pawn, "holeboard", to[0], to[1], AnimationTypes.MOVE_LINEARPROP, 10);
             actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.
             stageModel.unselectAll();
             stageModel.setState(KamisadoStageModel.STATE_SELECTPAWN);
             ActionPlayer play = new ActionPlayer(model, control, actions);
             play.start();
         }
    }

}

