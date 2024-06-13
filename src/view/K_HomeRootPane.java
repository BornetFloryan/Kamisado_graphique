package view;

import boardifier.view.RootPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class K_HomeRootPane extends RootPane {
    private double width;
    private double height;

    public K_HomeRootPane(double width, double height) {
        super();
        this.width = width;
        this.height = height;
        resetToDefault();
    }

    @Override
    public void createDefaultGroup() {
        Rectangle frame = new Rectangle(width, height, Color.LIGHTGREY);
        Text text = new Text("Playing kamisado !");
        text.setFont(new Font(40));
        text.setFill(Color.BLACK);
        text.setX((frame.getWidth() - text.getLayoutBounds().getWidth()) / 2);
        text.setY(50);

        // put shapes in the group
        group.getChildren().clear();
        group.getChildren().addAll(frame, text);
    }
}
