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

public class K_HowToPlayPane extends RootPane {
    private final double width;
    private final double height;
    private Button backToHomeButton;
    private Button gameRulesButton;
    private Button diagramsButton;

    public K_HowToPlayPane(double width, double height) {
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

        Text text = new Text("Choose explanation!");
        text.setFont(new Font(50));
        text.setFill(Color.BLACK);

        StackPane textContainer = new StackPane(text);
        textContainer.setPadding(new Insets(-50, 0, 0, 0));

        gameRulesButton = new Button("Game rules");
        gameRulesButton.setPadding(new Insets(10, 0, 10, 0));
        gameRulesButton.setMaxWidth(width / 5);
        gameRulesButton.setFont(new Font(20));

        diagramsButton = new Button("Explanations of diagrams");
        diagramsButton.setPadding(new Insets(10, 0, 10, 0));
        diagramsButton.setMaxWidth(width / 5);
        diagramsButton.setFont(new Font(20));


        backToHomeButton = new Button("Back to home");
        backToHomeButton.setPadding(new Insets(10, 0, 10, 0));
        backToHomeButton.setMaxWidth(width / 5);
        backToHomeButton.setFont(new Font(20));


        frame.getChildren().addAll(textContainer, gameRulesButton, diagramsButton, backToHomeButton);


        group.getChildren().clear();
        group.getChildren().add(frame);
    }
    public Button getGameRulesButton() {
        return gameRulesButton;
    }
    public Button getDiagramsButton() {
        return diagramsButton;
    }
    public Button getBackToHomeButton() {
        return backToHomeButton;
    }

}
