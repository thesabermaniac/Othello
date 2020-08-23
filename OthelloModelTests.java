import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class OthelloModelTests {
    private OthelloModel om = new OthelloModel();
    private CellState[][] grid = om.toArray();
    private int size = om.getBoardSize();

    @Test
    void initBoard(){
        checkCells(grid);
    }

    @Test
    void validMoveExistsTrue(){
        Assertions.assertTrue(om.validMoveExists(CellState.BLACK));
        Assertions.assertTrue(om.validMoveExists(CellState.WHITE));
    }

    @Test
    void validMoveExistsFalse(){
        OthelloModel smallModel = new OthelloModel(2);
        Assertions.assertFalse(smallModel.validMoveExists(CellState.BLACK));
        Assertions.assertFalse(smallModel.validMoveExists(CellState.WHITE));
    }

    @Test
    void indexInRangeTrue(){
        Assertions.assertTrue(om.indexInRange(new OthelloModel.Cell(0, 0)));
        Assertions.assertTrue(om.indexInRange(new OthelloModel.Cell(0, 7)));
        Assertions.assertTrue(om.indexInRange(new OthelloModel.Cell(7, 0)));
        Assertions.assertTrue(om.indexInRange(new OthelloModel.Cell(7, 7)));
        Assertions.assertTrue(om.indexInRange(new OthelloModel.Cell(3, 5)));
    }

    @Test
    void indexInRangeFalse(){
        Assertions.assertFalse(om.indexInRange(new OthelloModel.Cell(8, 7)));
        Assertions.assertFalse(om.indexInRange(new OthelloModel.Cell(-1, 0)));
        Assertions.assertFalse(om.indexInRange(new OthelloModel.Cell(0, 8)));
        Assertions.assertFalse(om.indexInRange(new OthelloModel.Cell(7, -1)));
        Assertions.assertFalse(om.indexInRange(new OthelloModel.Cell(8, -1)));
        Assertions.assertFalse(om.indexInRange(new OthelloModel.Cell(8, 8)));
        Assertions.assertFalse(om.indexInRange(new OthelloModel.Cell(-1, -1)));
        Assertions.assertFalse(om.indexInRange(new OthelloModel.Cell(-1, 8)));

    }

    @Test
    void isValidTrue(){
        Assertions.assertTrue(om.isValid(new OthelloModel.Cell(2, 3), CellState.BLACK, false));
        Assertions.assertTrue(om.isValid(new OthelloModel.Cell(2, 4), CellState.WHITE, false));
    }

    @Test
    void isValidFalse(){
        Assertions.assertFalse(om.isValid(new OthelloModel.Cell(3, 3), CellState.BLACK, false));
        Assertions.assertFalse(om.isValid(new OthelloModel.Cell(3, 3), CellState.WHITE, false));
        Assertions.assertFalse(om.isValid(new OthelloModel.Cell(4, 2), CellState.BLACK, false));
        Assertions.assertFalse(om.isValid(new OthelloModel.Cell(2, 2), CellState.WHITE, false));
    }

    @Test
    void getWinner(){
        OthelloModel smallModel = new OthelloModel(2);
        OthelloModel mediumModel = new OthelloModel(4);
        Assertions.assertEquals(CellState.NONE, smallModel.getWinner());
        play4x4(mediumModel);
        Assertions.assertEquals(CellState.BLACK, mediumModel.getWinner());
    }

    @Test
    void isGameOver(){
        OthelloModel smallModel = new OthelloModel(2);
        OthelloModel mediumModel = new OthelloModel(4);
        Assertions.assertTrue(smallModel.isGameOver());
        Assertions.assertFalse(mediumModel.isGameOver());
        play4x4(mediumModel);
        Assertions.assertTrue(mediumModel.isGameOver());
    }

    @Test
    void makeMove(){
        boolean legalMove = om.makeMove(new OthelloModel.Cell(2, 3), CellState.BLACK);
        Assertions.assertTrue(legalMove);
        boolean illegalMove = om.makeMove(new OthelloModel.Cell(2, 3), CellState.WHITE);
        Assertions.assertFalse(illegalMove);
        Assertions.assertEquals(CellState.BLACK, om.toArray()[2][3]);
        Assertions.assertEquals(CellState.BLACK, om.toArray()[3][3]);
        boolean legalMove2 = om.makeMove(new OthelloModel.Cell(2, 2), CellState.WHITE);
        Assertions.assertTrue(legalMove2);
        Assertions.assertEquals(CellState.WHITE, om.toArray()[2][2]);
        Assertions.assertEquals(CellState.WHITE, om.toArray()[3][3]);
    }

    @Test
    void getBoardSize(){
        OthelloModel smallModel = new OthelloModel(2);
        OthelloModel mediumModel = new OthelloModel(4);
        Assertions.assertEquals(2, smallModel.getBoardSize());
        Assertions.assertEquals(4, mediumModel.getBoardSize());
        Assertions.assertEquals(8, om.getBoardSize());
    }

    @Test
    void toArray(){
        CellState[][] arrayCopy = om.toArray();
        checkCells(arrayCopy);
        om.makeMove(new OthelloModel.Cell(2, 3), CellState.BLACK);
        CellState[][] arrayCopy2 = om.toArray();
        Assertions.assertEquals(CellState.BLACK, arrayCopy2[2][3]);

    }

    private void checkCells(CellState[][] board){
        int sizeOfBoard = board.length;
        for(int row = 0; row < sizeOfBoard; row++){
            for(int col = 0; col < sizeOfBoard; col++){
                if((col == sizeOfBoard/2 || col == sizeOfBoard/2 - 1) && (row == sizeOfBoard/2 || row == sizeOfBoard/2 -1)){
                    CellState expected = col == row ? CellState.WHITE : CellState.BLACK;
                    assertEquals(expected, board[row][col]);
                }
                else {
                    assertEquals(CellState.NONE, board[row][col]);
                }
            }
        }
    }


    private void play4x4(OthelloModel model){
        model.makeMove(new OthelloModel.Cell(0, 1), CellState.BLACK);
        model.makeMove(new OthelloModel.Cell(2, 0), CellState.WHITE);
        model.makeMove(new OthelloModel.Cell(3, 1), CellState.BLACK);
        model.makeMove(new OthelloModel.Cell(0, 2), CellState.WHITE);
        model.makeMove(new OthelloModel.Cell(2, 3), CellState.BLACK);
        model.makeMove(new OthelloModel.Cell(3, 2), CellState.WHITE);
        model.makeMove(new OthelloModel.Cell(0, 3), CellState.BLACK);
        model.makeMove(new OthelloModel.Cell(1, 3), CellState.WHITE);
        model.makeMove(new OthelloModel.Cell(3, 3), CellState.BLACK);
        model.makeMove(new OthelloModel.Cell(3, 0), CellState.WHITE);
        model.makeMove(new OthelloModel.Cell(0, 0), CellState.BLACK);
        model.makeMove(new OthelloModel.Cell(1, 0), CellState.WHITE);

    }

}
