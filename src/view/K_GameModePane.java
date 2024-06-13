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
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class K_GameModePane extends RootPane {
    private final double width;
    private final double height;

    private Button PvPButton;
    private Button PvCButton;
    private Button CvCButton;
    private Button backToHomeButton;

    public K_GameModePane(double width, double height) {
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

        Text text = new Text("Choose game mode !");
        text.setFont(new Font(50));
        text.setFill(Color.BLACK);

        StackPane textContainer = new StackPane(text);
        textContainer.setPadding(new Insets(-50, 0, 0, 0));

        PvPButton = new Button("Player vs Player");
        PvPButton.setPadding(new Insets(10, 0, 10, 0));
        PvPButton.setMaxWidth(width / 5);
        PvPButton.setFont(new Font(20));

        PvCButton = new Button("Player vs Computer");
        PvCButton.setPadding(new Insets(10, 0, 10, 0));
        PvCButton.setMaxWidth(width / 5);
        PvCButton.setFont(new Font(20));

        CvCButton = new Button("Computer vs Computer");
        CvCButton.setPadding(new Insets(10, 0, 10, 0));
        CvCButton.setMaxWidth(width / 5);
        CvCButton.setFont(new Font(20));

        backToHomeButton = new Button("Back to home");
        backToHomeButton.setPadding(new Insets(10, 0, 10, 0));
        backToHomeButton.setMaxWidth(width / 5);
        backToHomeButton.setFont(new Font(20));


        frame.getChildren().addAll(textContainer, PvPButton, PvCButton, CvCButton, backToHomeButton);


        group.getChildren().clear();
        group.getChildren().add(frame);
    }

    public Button getPvPButton() {
        return PvPButton;
    }

    public Button getPvCButton() {
        return PvCButton;
    }

    public Button getCvCButton() {
        return CvCButton;
    }

    public Button getBackToHomeButton() {
        return backToHomeButton;
    }
}
