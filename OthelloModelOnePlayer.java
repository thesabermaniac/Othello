import java.util.ArrayList;
import java.lang.Math;

public class OthelloModelOnePlayer extends OthelloModel {
    private CellState[][] grid = toArray();

    public void cpuMove(CellState color){
        int maxFlips = 0;
        Cell bestMove = null;
        ArrayList<Cell> validMoves = getValidMoves(color);

        for(Cell move : validMoves){
            move.flips = getFlipCount(move, color);
        }

        for(Cell move : validMoves){
            if(move.flips > maxFlips){
                maxFlips = move.flips;
                bestMove = move;
            }
        }
        if(bestMove != null) {
            makeMove(bestMove, color);
        }
    }

    public int getFlipCount(Cell move, CellState state){
        int flipCount = 0;
        ArrayList<Cell> flipCells = getFlipCells(new Cell(move.row, move.column), state, false);
        for(Cell flipCell : flipCells){
            flipCount += flipCell.row - move.row != 0 ? Math.abs(flipCell.row - move.row):  Math.abs(flipCell.column - move.column);
        }
        flipCount -= flipCells.size();
        return flipCount;
    }
}
