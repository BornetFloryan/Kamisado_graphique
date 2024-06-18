package control;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.ControllerKey;
import boardifier.model.GameElement;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.view.RootPane;
import boardifier.view.View;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.K_Board;
import model.K_StageModel;
import model.Pawn;
import view.K_GameModePane;
import view.K_HomeRootPane;
import view.K_BoardLook;
import view.K_View;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

/**
 * A basic keystrokes handler.
 * Generally useless for board games, but it can still be used if needed
 */
public class K_ControllerKey extends ControllerKey implements EventHandler<KeyEvent> {
    // Create a stack of key events
    private final Stack<KeyEvent> keyEvents = new Stack<>();
    private K_StageModel stageModel;
    private K_Board board;
    private K_HomeRootPane rootPane;
    private Stage stage;


    public K_ControllerKey(Model model, View view, Controller control) {
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
                stageModel = (K_StageModel) model.getGameStage();
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

                    K_BoardLook boardLook = (K_BoardLook) control.getElementLook(board);
                    String color = boardLook.getColor(destY, destX);
                    stageModel.setLockedColor(color);


                    ActionList actionList = ActionFactory.generateMoveWithinContainer(control, model, pawn, destY, destX);

                    ActionPlayer action = new ActionPlayer(model, control, actionList);
                    actionList.setDoEndOfTurn(true);
                    action.start();

                    model.setNextPlayer();
                    pawn = stageModel.searchPawnFromLockedColor();
                }
            }

            //O_Win
            if (event.isControlDown() && event.getCode() == KeyCode.O) {
                stageModel = (K_StageModel) model.getGameStage();
                board = stageModel.getBoard();


                List<String> actions = new ArrayList<>();
                actions.add("D5");
                actions.add("H7");
                actions.add("C5");
                actions.add("G8");

                String fromDefault = "G8";
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

                    K_BoardLook boardLook = (K_BoardLook) control.getElementLook(board);
                    String color = boardLook.getColor(destY, destX);
                    stageModel.setLockedColor(color);


                    ActionList actionList = ActionFactory.generateMoveWithinContainer(control, model, pawn, destY, destX);

                    ActionPlayer action = new ActionPlayer(model, control, actionList);
                    actionList.setDoEndOfTurn(true);
                    action.start();

                    model.setNextPlayer();
                    pawn = stageModel.searchPawnFromLockedColor();
                }
            }

            //Draw
            if (event.isControlDown() && event.getCode() == KeyCode.D) {
                stageModel = (K_StageModel) model.getGameStage();
                board = stageModel.getBoard();

                List<String> actions = new ArrayList<>();
                actions.add("A2");
                actions.add("D3");
                actions.add("D7");
                actions.add("B6");
                actions.add("E7");
                actions.add("D4");
                actions.add("H7");
                actions.add("B5");
                actions.add("G4");
                actions.add("A6");
                actions.add("B3");
                actions.add("C2");
                actions.add("C6");
                actions.add("H4");
                actions.add("D6");
                actions.add("F4");
                actions.add("A7");
                actions.add("E5");
                actions.add("H6");
                actions.add("C5");

                String fromDefault = "A8";
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

                    K_BoardLook boardLook = (K_BoardLook) control.getElementLook(board);
                    String color = boardLook.getColor(destY, destX);
                    stageModel.setLockedColor(color);


                    ActionList actionList = ActionFactory.generateMoveWithinContainer(control, model, pawn, destY, destX);

                    ActionPlayer action = new ActionPlayer(model, control, actionList);
                    actionList.setDoEndOfTurn(true);
                    action.start();

                    model.setNextPlayer();
                    pawn = stageModel.searchPawnFromLockedColor();
                }
            }
            if (event.getCode() == KeyCode.BACK_SPACE) {
                model.pauseGame();
                String message = "Game paused. What do you want to do?";
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initStyle(StageStyle.UNDECORATED);
                alert.initOwner(view.getStage());
                alert.setHeaderText(message);
                ButtonType resume = new ButtonType("Resume");
                ButtonType quit = new ButtonType("Select game mode");
                alert.getButtonTypes().clear();
                alert.getButtonTypes().addAll(resume, quit);
                Optional<ButtonType> option = alert.showAndWait();
                if (option.get() == resume) {
                    model.resumeGame();
                } else if (option.get() == quit) {
                    RootPane root = (RootPane) view.getRootPane();
                    Stage K_stage = view.getStage();

                    view = new K_View(model, K_stage, new K_GameModePane(root.getWidth(), root.getHeight()));
                    control.setControlAction(new K_ControllerGameModeAction(model, view, (K_Controller) control, K_stage));

//                        System.exit(0);

                } else {
                    System.err.println("Abnormal case: dialog closed with not choice");
                    System.exit(1);
                }
            }
        }
    }
}

