package control;

import boardifier.control.Controller;
import boardifier.control.ControllerKey;
import boardifier.control.Logger;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.*;
import javafx.scene.input.*;

import java.util.Stack;

/**
 * A basic keystrokes handler.
 * Generally useless for board games, but it can still be used if needed
 */
public class ControllerHoleKey extends ControllerKey implements EventHandler<KeyEvent> {
    // Create a stack of key events
    private Stack<KeyEvent> keyEvents = new Stack<>();

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
        }
    }
}

