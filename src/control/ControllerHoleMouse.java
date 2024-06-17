package control;

import boardifier.control.*;
import boardifier.model.*;
import boardifier.model.action.ActionList;
import boardifier.view.View;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.HoleBoard;
import model.KamisadoStageModel;
import model.Pawn;
import view.KamisadoBoardLook;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A mouse controller that handles mouse clicks, selects pawns, and moves them.
 */
public class ControllerHoleMouse extends ControllerMouse implements EventHandler<MouseEvent> {
    private Pawn selectedPawn = null;

    public ControllerHoleMouse(Model model, View view, Controller control) {
        super(model, view, control);
    }

    @Override
    public void handle(MouseEvent event) {
        if (!model.isCaptureMouseEvent()) return; // Check if mouse event capture is enabled

        Coord2D clickPosition = new Coord2D(event.getSceneX(), event.getSceneY());
        List<GameElement> clickedElements = control.elementsAt(clickPosition);

        KamisadoStageModel stageModel = (KamisadoStageModel) model.getGameStage();
        HoleBoard board = stageModel.getBoard();

        Logger.debug("Game state: " + stageModel.getState()); // Debug game state

        if (stageModel.getState() == KamisadoStageModel.STATE_SELECTPAWN && model.getLockedColor() == null) {
            Logger.debug("Selecting pawn");
            handleSelectPawnState(clickedElements, board, stageModel);
        } else if (stageModel.getState() == KamisadoStageModel.STATE_SELECTDEST) {
            Logger.debug("Selecting destination");
            handleSelectDestState(clickedElements, board, stageModel, clickPosition);
        }
    }

    private void handleSelectPawnState(List<GameElement> clickedElements, HoleBoard board, KamisadoStageModel stageModel) {
        for (GameElement element : clickedElements) {
            if (element.getType() == ElementTypes.getType("pawn")) {
                if (selectedPawn != null) {
                    selectedPawn.toggleSelected(); // Unselect previously selected pawn
                }
                selectedPawn = (Pawn) element;
                selectedPawn.toggleSelected(); // Select new pawn
                Logger.debug("Selected pawn: " + selectedPawn + " with color: " + selectedPawn.getColor()); // Debug selected pawn
                stageModel.setState(KamisadoStageModel.STATE_SELECTDEST);
                board.setValidCells(selectedPawn);
                return;
            }
        }
    }

    public void handleSelectDestState(List<GameElement> clickedElements, HoleBoard board, KamisadoStageModel stageModel, Coord2D clickPosition) {
        for (GameElement element : clickedElements) {
            if (element.isSelected() && stageModel.getLockedColor() == null) {
                resetSelection(board);
                return;
            } else if (element.getType() == ElementTypes.getType("pawn") && stageModel.getLockedColor() == null) {
                stageModel.setState(KamisadoStageModel.STATE_SELECTPAWN);
                handleSelectPawnState(clickedElements, board, stageModel);
                return;
            }
        }

        if (selectedPawn == null) return; // If no pawn is selected, return

        KamisadoBoardLook lookBoard = (KamisadoBoardLook) control.getElementLook(board);
        int[] targetCell = lookBoard.getCellFromSceneLocation(clickPosition);

        if (targetCell != null && board.canReachCell(targetCell[0], targetCell[1])) {
            String color = lookBoard.getColor(targetCell[0], targetCell[1]);
            stageModel.setLockedColor(color);

            Logger.debug("Locked color set to: " + color); // Debug locked color
            performMoveAction(stageModel, targetCell);

            setPawnFromLockedColor(stageModel, board, model.getCurrentPlayer());
        }
    }

    private void resetSelection(HoleBoard board) {
        selectedPawn.toggleSelected(); // Unselect the pawn
        selectedPawn = null;
        board.resetReachableCells(false);
    }

    public void setPawnFromLockedColor(KamisadoStageModel stageModel, HoleBoard board, Player player) {
        Logger.debug("Setting pawn from locked color: " + stageModel.getLockedColor()); // Debug locked color
        System.out.println(stageModel.getLockedColor());
        if (stageModel.getLockedColor() == null) return;

        Pawn validPawn = stageModel.searchPawnFromLockedColor();
        board.setValidCells(validPawn);

        Pawn[] pawns;

        if (player.getName().equals(stageModel.getModel().getPlayers().get(0).getName())) {
            pawns = stageModel.getXPawns();
        } else {
            pawns = stageModel.getOPawns();
        }

        String color = stageModel.getLockedColor();

        for (Pawn pawn : pawns) {
            if (pawn.getColor().equals(color)) {
                selectedPawn = pawn;
                Logger.debug("Pawn selected: " + selectedPawn + " with color: " + color); // Debug pawn selection
                selectedPawn.toggleSelected();
                board.setValidCells(selectedPawn);
                stageModel.setState(KamisadoStageModel.STATE_SELECTDEST);
                return;
            }
        }
    }

    private void performMoveAction(KamisadoStageModel stageModel, int[] targetCell) {
        ActionList actions = ActionFactory.generateMoveWithinContainer(control, model, selectedPawn, targetCell[0], targetCell[1]);
        actions.setDoEndOfTurn(true);
        selectedPawn.setLocation(targetCell[0], targetCell[1]);

        resetSelection(stageModel.getBoard());

        ActionPlayer actionPlayer = new ActionPlayer(model, control, actions);
        actionPlayer.start();
    }
}
