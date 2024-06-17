package control;

import boardifier.control.ActionFactory;
import boardifier.control.Controller;
import boardifier.control.ControllerKey;
import boardifier.control.Logger;
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
                actions.add("D8");
                actions.add("D3");
                actions.add("E2");
                actions.add("G7");
                actions.add("A7");
                actions.add("F1");

                for (String action : actions) {
                    int fromX = action.charAt(0) - 'A';
                    int fromY = Character.getNumericValue(action.charAt(1)) - 1;
                    System.out.println(fromX + ", " + fromY);

                    Pawn pawn = (Pawn) board.getElement(fromX, fromY);

                    int destX = action.charAt(0) - 'A';
                    int destY = Character.getNumericValue(action.charAt(1)) - 1;

                    ActionList actionList = ActionFactory.generateMoveWithinContainer(control, model, pawn, destX, destY);
                }
            }
        }
    }
}

