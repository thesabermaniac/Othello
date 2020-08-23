import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class OnePlayerTests {
    private OthelloModelOnePlayer model = new OthelloModelOnePlayer();
    private CellState[][] grid = model.toArray();
    private int size = model.getBoardSize();

    @Test
    void getValidMoves(){
        ArrayList<OthelloModel.Cell> validMoves = model.getValidMoves(CellState.BLACK);
        assertEquals(4, validMoves.size());
        model.makeMove(new OthelloModel.Cell(2,3), CellState.BLACK);
        validMoves = model.getValidMoves(CellState.WHITE);
        assertEquals(3, validMoves.size());
    }

    @Test
    void getFlipCell(){
        ArrayList<OthelloModel.Cell> flipCells = model.getFlipCells(new OthelloModel.Cell(2, 3), CellState.BLACK, false);
        assertEquals(4, flipCells.get(0).row);
        assertEquals(3, flipCells.get(0).column);
        model.makeMove(new OthelloModel.Cell(2,3), CellState.BLACK);
        flipCells = model.getFlipCells(new OthelloModel.Cell(2, 2), CellState.WHITE, false);
        assertEquals(4, flipCells.get(0).row);
        assertEquals(4, flipCells.get(0).column);
    }

    @Test
    void getFlipCount(){
        assertEquals(1, model.getFlipCount(new OthelloModel.Cell(2, 3), CellState.BLACK));
        model.makeMove(new OthelloModel.Cell(2,3), CellState.BLACK);
        model.cpuMove(CellState.WHITE);
        model.makeMove(new OthelloModel.Cell(3, 2), CellState.BLACK);
        assertEquals(2, model.getFlipCount(new OthelloModel.Cell(2, 4), CellState.WHITE));
    }

    @Test
    void cpuMove(){
        model.makeMove(new OthelloModel.Cell(2,3), CellState.BLACK);
        model.cpuMove(CellState.WHITE);
        assertEquals(model.toArray()[2][2], CellState.WHITE);
        model.makeMove(new OthelloModel.Cell(3,2), CellState.BLACK);
        model.cpuMove(CellState.WHITE);
        assertEquals(model.toArray()[2][4], CellState.WHITE);
        assertEquals(model.toArray()[2][3], CellState.WHITE);
        assertEquals(model.toArray()[3][4], CellState.WHITE);
        model.makeMove(new OthelloModel.Cell(1,2), CellState.BLACK);
        model.cpuMove(CellState.WHITE);
        assertEquals(model.toArray()[1][1], CellState.WHITE);
        assertEquals(model.toArray()[2][2], CellState.WHITE);
        assertEquals(model.toArray()[3][3], CellState.WHITE);
    }

    @Test
    void isValid(){
        assertTrue(model.isValid(new OthelloModel.Cell(2, 3), CellState.BLACK, false));
        assertTrue(model.isValid(new OthelloModel.Cell(5, 3), CellState.WHITE, false));
        assertFalse(model.isValid(new OthelloModel.Cell(2, 3), CellState.WHITE, false));
        assertFalse(model.isValid(new OthelloModel.Cell(5, 3), CellState.BLACK, false));
    }

}
