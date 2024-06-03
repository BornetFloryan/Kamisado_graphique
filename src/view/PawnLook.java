package view;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.scene.shape.Circle;
import model.Pawn;

public class PawnLook extends ElementLook {
    private Circle circle;
    private int radius;

    public PawnLook(int radius, GameElement element) {
        super(element);

        this.radius = radius;
        render();
    }

    /**
     * render() is used to create the visual shape of the element
     * It is normally called only once, when the look is added to the GameStageView
     */
    @Override
    protected void render() {
        Pawn pawn = (Pawn)element;
        circle = new Circle();
        circle.setRadius(radius);

        
    }
}
