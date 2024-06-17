package view;

import boardifier.view.RootPane;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.List;

public class K_DiagramsPane extends RootPane {
    private final double width;
    private final double height;
    private Button backToHowToPlayButton;
    private ImageView imageView;
    private List<Image> images;

    public K_DiagramsPane(double width, double height) {
        super();
        this.width = width;
        this.height = height;
        resetToDefault();
    }

    @Override
    public void createDefaultGroup() {
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(width, height);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setBackground(new Background(new BackgroundFill(Color.rgb(230, 230, 230), null, null)));

        Text text = new Text("Explanations of diagrams!");
        text.setFont(new Font(40));
        text.setFill(Color.BLACK);

        StackPane textContainer = new StackPane(text);
        textContainer.setPadding(new Insets(-50, 0, 0, 0));
        GridPane.setMargin(textContainer, new Insets(20, 0, 0, 0));


        backToHowToPlayButton = new Button("Back to how to play");
        backToHowToPlayButton.setPadding(new Insets(5, 0, 5, 0));
        backToHowToPlayButton.setMaxWidth(width / 5);
        backToHowToPlayButton.setFont(new Font(20));
        GridPane.setHalignment(backToHowToPlayButton, HPos.CENTER);

        images = Arrays.asList(
                new Image(getClass().getResource("/illustration/illustration-1.png").toString()),
                new Image(getClass().getResource("/illustration/illustration-2.png").toString()),
                new Image(getClass().getResource("/illustration/illustration-3.png").toString()),
                new Image(getClass().getResource("/illustration/illustration-4.png").toString()),
                new Image(getClass().getResource("/illustration/illustration-5.png").toString()),
                new Image(getClass().getResource("/illustration/illustration-6.png").toString()),
                new Image(getClass().getResource("/illustration/illustration-7.png").toString()),
                new Image(getClass().getResource("/illustration/illustration-8.png").toString())
        );

        imageView = new ImageView(images.get(0));
        imageView.setFitWidth(width * 0.8);
        imageView.setFitHeight(height * 0.8);
        imageView.setPreserveRatio(true);

        GridPane.setHalignment(imageView, HPos.CENTER);
        GridPane.setValignment(imageView, VPos.CENTER);

        Button nextButton = new Button("Next");
        Button prevButton = new Button("Previous");

        HBox hboxButtons = new HBox();
        hboxButtons.getChildren().addAll(prevButton, nextButton);
        hboxButtons.setAlignment(Pos.CENTER);

        int[] imageIndex = {0};

        nextButton.setOnAction(event -> {
            imageIndex[0] = (imageIndex[0] + 1) % images.size();
            imageView.setImage(images.get(imageIndex[0]));
        });

        prevButton.setOnAction(event -> {
            imageIndex[0] = (imageIndex[0] - 1 + images.size()) % images.size();
            imageView.setImage(images.get(imageIndex[0]));
        });



        gridPane.add(textContainer, 0, 0);
        gridPane.add(backToHowToPlayButton, 0, 1);
        gridPane.add(imageView, 0, 2);
        gridPane.add(hboxButtons, 0, 3);

        group.getChildren().clear();
        group.getChildren().add(gridPane);
    }

    public Button getBackToHowToPlayButton() {
        return backToHowToPlayButton;
    }
}