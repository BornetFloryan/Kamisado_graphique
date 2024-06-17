package control;

import boardifier.control.*;
import boardifier.model.GameElement;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.view.View;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.stage.Screen;
import model.HoleBoard;
import model.KamisadoStageModel;
import model.Pawn;
import view.KamisadoBoardLook;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * A basic keystrokes handler.
 * Generally useless for board games, but it can still be used if needed
 */
public class ControllerHoleKey extends ControllerKey implements EventHandler<KeyEvent> {
    // Create a stack of key events
    private Stack<KeyEvent> keyEvents = new Stack<>();
    private KamisadoStageModel stageModel;
    private HoleBoard board;

    public ControllerHoleKey(Model model, View view, Controller control) {
        super(model, view, control);
    }

    public void handle(KeyEvent event) {
        if (!model.isCaptureKeyEvent()) return;

        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            System.out.println("Key pressed: " + event.getCode().toString());

            if (event.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }

            if (event.isControlDown() && event.getCode() == KeyCode.R) {
                try {
                    control.startGame();
                    view.getStage().setFullScreen(true);
                } catch (GameException e) {
                    throw new RuntimeException(e);
                }
            }

            //X_Win
            if (event.isControlDown() && event.getCode() == KeyCode.X) {
                stageModel = (KamisadoStageModel) model.getGameStage();
                board = stageModel.getBoard();


                List<String> actions = new ArrayList<>();
                actions.add("D3");
                actions.add("E2");
                actions.add("G7");
                actions.add("A7");
                actions.add("F1");

                String fromDefault = "D8";
                Pawn pawn = null;

                for (int i = 0; i < actions.size(); i++) {
                    if (fromDefault != null) {
                        int fromX = fromDefault.charAt(0) - 'A';
                        int fromY = fromDefault.charAt(1) - '1';

                        GameElement element = board.getElement(fromY, fromX);

                        if (element instanceof Pawn) {
                            pawn = (Pawn) element;
                        } else {
                            throw new RuntimeException("No pawn found at " + fromDefault);
                        }
                    }

                    fromDefault = null;
                    int destX = actions.get(i).charAt(0) - 'A';
                    int destY = actions.get(i).charAt(1) - '1';

                    KamisadoBoardLook boardLook = (KamisadoBoardLook) control.getElementLook(board);
                    String color = boardLook.getColor(destY, destX);
                    stageModel.setLockedColor(color);


                    ActionList actionList = ActionFactory.generateMoveWithinContainer(control, model, pawn, destY, destX);

                    ActionPlayer action = new ActionPlayer(model, control, actionList);
                    actionList.setDoEndOfTurn(true);
                    action.start();

                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    model.setNextPlayer();
                    pawn = stageModel.searchPawnFromLockedColor();
                    System.out.println("Pawn: " + pawn);
                }
            }
        }
    }
}

