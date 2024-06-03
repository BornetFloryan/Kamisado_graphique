package view;

import boardifier.view.RootPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class KamisadoRootPane extends RootPane {

        public KamisadoRootPane() {
            super();
        }

        @Override
        public void createDefaultGroup() {
            Rectangle frame = new Rectangle(800, 600, Color.LIGHTGREY);
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
