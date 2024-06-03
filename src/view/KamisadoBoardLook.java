package view;

import boardifier.model.ContainerElement;
import boardifier.view.ClassicBoardLook;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class KamisadoBoardLook extends ClassicBoardLook {
    private final String[][] board_color = {
            {K_Color.ORANGE, K_Color.BLUE, K_Color.PURPLE, K_Color.PINK, K_Color.YELLOW, K_Color.RED, K_Color.GREEN, K_Color.BROWN},
            {K_Color.RED, K_Color.ORANGE, K_Color.PINK, K_Color.GREEN, K_Color.BLUE, K_Color.YELLOW, K_Color.BROWN, K_Color.PURPLE},
            {K_Color.GREEN, K_Color.PINK, K_Color.ORANGE, K_Color.RED, K_Color.PURPLE, K_Color.BROWN, K_Color.YELLOW, K_Color.BLUE},
            {K_Color.PINK, K_Color.PURPLE, K_Color.BLUE, K_Color.ORANGE, K_Color.BROWN, K_Color.GREEN, K_Color.RED, K_Color.YELLOW},
            {K_Color.YELLOW, K_Color.RED, K_Color.GREEN, K_Color.BROWN, K_Color.ORANGE, K_Color.BLUE, K_Color.PURPLE, K_Color.PINK},
            {K_Color.BLUE, K_Color.YELLOW, K_Color.BROWN, K_Color.PURPLE, K_Color.RED, K_Color.ORANGE, K_Color.PINK, K_Color.GREEN},
            {K_Color.PURPLE, K_Color.BROWN, K_Color.YELLOW, K_Color.BLUE, K_Color.GREEN, K_Color.PINK, K_Color.ORANGE, K_Color.RED},
            {K_Color.BROWN, K_Color.GREEN, K_Color.RED, K_Color.YELLOW, K_Color.PINK, K_Color.PURPLE, K_Color.BLUE, K_Color.ORANGE},
    };

    public KamisadoBoardLook(int cellSize, ContainerElement element) {
        super(cellSize, element, -1, null, null, 0, Color.BLACK, 2, Color.BLACK, true);
    }

    @Override
    protected void render() {
        super.render();

        cells = new Rectangle[nbRows][nbCols];

        for (int i = 0; i < nbRows; i++) {
            for (int j = 0; j < nbCols; j++) {
                cells[i][j] = new Rectangle(colWidth, rowHeight, Color.valueOf(board_color[i][j]));
                cells[i][j].setSmooth(false);
                cells[i][j].setStroke(Color.valueOf(board_color[i][j]));
                cells[i][j].setStrokeWidth(0);
                cells[i][j].setStrokeMiterLimit(10);
                cells[i][j].setStrokeType(StrokeType.INSIDE);
                cells[i][j].setX(j * colWidth + gapXToCells);
                cells[i][j].setY(i * rowHeight + gapYToCells);
                addShape(cells[i][j]);
            }
        }
    }
}
