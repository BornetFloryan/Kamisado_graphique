package view;

import boardifier.view.RootPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class K_HomeRootPane extends RootPane {
    private final double width;
    private final double height;

    private Button startButton;
    private Button howToPlayButton;
    private Button quitButton;

    public K_HomeRootPane(double width, double height) {
        super();
        this.width = width;
        this.height = height;
        resetToDefault();
    }

    @Override
    public void createDefaultGroup() {
        VBox frame = new VBox();
        frame.setPrefSize(width, height);
        frame.setAlignment(Pos.CENTER);
        frame.setSpacing(50);
        frame.setBackground(new Background(new BackgroundFill(Color.rgb(230, 230, 230), null, null)));

        Text text = new Text("Playing kamisado !");
        text.setFont(new Font("SansSerif", 50));
        text.setFill(Color.BLACK);

        StackPane textContainer = new StackPane(text);
        textContainer.setPadding(new Insets(-50, 0, 0, 0));

        startButton = new Button("Start");
        startButton.setPadding(new Insets(10, 0, 10, 0));
        startButton.setMaxWidth(width / 5);
        startButton.setFont(new Font("SansSerif", 20));

        howToPlayButton = new Button("How to play");
        howToPlayButton.setPadding(new Insets(10, 0, 10, 0));
        howToPlayButton.setMaxWidth(width / 5);
        howToPlayButton.setFont(new Font("SansSerif", 20));

        quitButton = new Button("Quit");
        quitButton.setPadding(new Insets(10, 0, 10, 0));
        quitButton.setMaxWidth(width / 5);
        quitButton.setFont(new Font("SansSerif", 20));


        frame.getChildren().addAll(textContainer, startButton, howToPlayButton, quitButton);


        group.getChildren().clear();
        group.getChildren().add(frame);
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getHowToPlayButton() {
        return howToPlayButton;
    }

    public Button getQuitButton() {
        return quitButton;
    }
}
