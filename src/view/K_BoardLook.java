package view;

import boardifier.model.ContainerElement;
import boardifier.view.ClassicBoardLook;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class K_BoardLook extends ClassicBoardLook {
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

    private final String[][] board_color_low = {
            {K_Color.ORANGE_LOW, K_Color.BLUE_LOW, K_Color.PURPLE_LOW, K_Color.PINK_LOW, K_Color.YELLOW_LOW, K_Color.RED_LOW, K_Color.GREEN_LOW, K_Color.BROWN_LOW},
            {K_Color.RED_LOW, K_Color.ORANGE_LOW, K_Color.PINK_LOW, K_Color.GREEN_LOW, K_Color.BLUE_LOW, K_Color.YELLOW_LOW, K_Color.BROWN_LOW, K_Color.PURPLE_LOW},
            {K_Color.GREEN_LOW, K_Color.PINK_LOW, K_Color.ORANGE_LOW, K_Color.RED_LOW, K_Color.PURPLE_LOW, K_Color.BROWN_LOW, K_Color.YELLOW_LOW, K_Color.BLUE_LOW},
            {K_Color.PINK_LOW, K_Color.PURPLE_LOW, K_Color.BLUE_LOW, K_Color.ORANGE_LOW, K_Color.BROWN_LOW, K_Color.GREEN_LOW, K_Color.RED_LOW, K_Color.YELLOW_LOW},
            {K_Color.YELLOW_LOW, K_Color.RED_LOW, K_Color.GREEN_LOW, K_Color.BROWN_LOW, K_Color.ORANGE_LOW, K_Color.BLUE_LOW, K_Color.PURPLE_LOW, K_Color.PINK_LOW},
            {K_Color.BLUE_LOW, K_Color.YELLOW_LOW, K_Color.BROWN_LOW, K_Color.PURPLE_LOW, K_Color.RED_LOW, K_Color.ORANGE_LOW, K_Color.PINK_LOW, K_Color.GREEN_LOW},
            {K_Color.PURPLE_LOW, K_Color.BROWN_LOW, K_Color.YELLOW_LOW, K_Color.BLUE_LOW, K_Color.GREEN_LOW, K_Color.PINK_LOW, K_Color.ORANGE_LOW, K_Color.RED_LOW},
            {K_Color.BROWN_LOW, K_Color.GREEN_LOW, K_Color.RED_LOW, K_Color.YELLOW_LOW, K_Color.PINK_LOW, K_Color.PURPLE_LOW, K_Color.BLUE_LOW, K_Color.ORANGE_LOW},
    };

    public K_BoardLook(int cellSize, ContainerElement element) {
        super(cellSize, element, -1, null, null, 0, Color.BLACK, 3, Color.BLACK, true);
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
                cells[i][j].setStrokeType(StrokeType.INSIDE);
                cells[i][j].setX(j * colWidth + gapXToCells);
                cells[i][j].setY(i * rowHeight + gapYToCells);
                addShape(cells[i][j]);
            }
        }
    }

    @Override
    public void onFaceChange() {
        ContainerElement board = (ContainerElement) element;
        boolean[][] reach = board.getReachableCells();

        for (int i = 0; i < nbRows; i++) {
            for (int j = 0; j < nbCols; j++) {
                if (reach[i][j]) {
                    cells[i][j].setStrokeWidth(2);
                    cells[i][j].setStrokeMiterLimit(10);
                    cells[i][j].setStrokeType(StrokeType.CENTERED);
                    cells[i][j].setStroke(Color.valueOf(Color.WHITE.toString()));
                    cells[i][j].setFill(Color.valueOf(board_color[i][j]));
                } else {
                    cells[i][j].setFill(Color.valueOf(board_color_low[i][j]));
                    cells[i][j].setStrokeWidth(0);
                }
            }
        }
    }

    public String getColor(int row, int col) {
//        System.out.println("Getting color at row: " + row + ", col: " + col + " -> " + board_color[row][col].replace("0x", "#"));
        return board_color[row][col];
    }
}
