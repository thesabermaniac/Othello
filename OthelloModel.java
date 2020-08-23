import java.util.ArrayList;
import java.util.Arrays;

enum CellState {
    NONE("_"), BLACK("\u25CF"), WHITE("\u25CB");
    private String state;

    CellState(String cellState) {state = cellState;}

    public CellState opposite(){
        if(this.equals(CellState.BLACK)){
            return CellState.WHITE;
        }
        else if(this.equals(CellState.WHITE)){
            return CellState.BLACK;
        }
        else return CellState.NONE;
    }

    public String toGUI(){ return this.equals(CellState.NONE) ? "" : state; }

    @Override
    public String toString(){
        return state;
    }
}

public class OthelloModel implements Othello{
    private CellState [][]grid;
    private final int BOARD_SIZE;

    public OthelloModel(){
        BOARD_SIZE = 8;
        grid = new CellState[BOARD_SIZE][BOARD_SIZE];
        initBoard();
    }

    public OthelloModel(int size){
        BOARD_SIZE = size;
        grid = new CellState[BOARD_SIZE][BOARD_SIZE];
        initBoard();
    }

    public OthelloModel(CellState[][] startingGrid){
        BOARD_SIZE = startingGrid.length;
        grid = startingGrid;
    }

    private void initBoard(){
        int boardCenter = BOARD_SIZE/2;
        for(CellState[] state : grid){
            Arrays.fill(state, CellState.NONE);
        }
        grid[boardCenter - 1][boardCenter - 1] = grid[boardCenter][boardCenter] = CellState.WHITE;
        grid[boardCenter - 1][boardCenter] = grid[boardCenter][boardCenter - 1] = CellState.BLACK;
    }

    public boolean validMoveExists(CellState state){
        for(int row = 0; row < grid.length; row++){
            for(int col = 0; col < grid[row].length; col++){
                if(grid[row][col] == CellState.NONE && isValid(new Cell(row, col), state, false)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean indexInRange(Cell move){
        return move.row >= 0 && move.row < BOARD_SIZE && move.column >= 0 && move.column < BOARD_SIZE;
    }

    public ArrayList<Cell> getFlipCells(Cell move, CellState state, boolean changeState){
        ArrayList<Cell> flipCellList = new ArrayList<>();
        if(grid[move.row][move.column] != CellState.NONE){
            return flipCellList;
        }
        for(int offSetRows = -1; offSetRows <= 1; offSetRows++){
            for(int offSetCols = -1; offSetCols <= 1; offSetCols++){
                int neighboringRow = move.row + offSetRows;
                int neighboringCol = move.column + offSetCols;
                if(indexInRange(new Cell(neighboringRow, neighboringCol))) {
                    if (grid[neighboringRow][neighboringCol] == state.opposite()) {
                        for (int multiplier = 2; multiplier <= grid[move.row].length; multiplier++) {
                            int nextRow = move.row + (offSetRows * multiplier);
                            int nextCol = move.column + (offSetCols * multiplier);
                            if (indexInRange(new Cell(nextRow, nextCol))) {
                                if (grid[nextRow][nextCol] == CellState.NONE) {
                                    break;
                                }
                                else if (grid[nextRow][nextCol] == state) {
                                    Cell flipCell = new Cell(nextRow, nextCol);
                                    flipCellList.add(flipCell);
                                    if(changeState){
                                        flipCells(move, flipCell, new Cell(offSetRows, offSetCols), state);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return flipCellList;
    }

    public boolean isValid(Cell move, CellState state, boolean changeState){
        return (getFlipCells(move, state, changeState).size() > 0);
    }

    private void flipCells(Cell move, Cell flipCell, Cell offSetCell, CellState state){
        while (flipCell.column != move.column || flipCell.row != move.row) {
            flipCell.column -= offSetCell.column;
            flipCell.row -= offSetCell.row;
            grid[flipCell.row][flipCell.column] = state;
        }
    }

    public CellState getWinner(){
        int numCells = BOARD_SIZE * BOARD_SIZE;
        int black = 0, white = 0;

        for(CellState[] row : grid){
            for(CellState cell: row){
                if(cell.equals(CellState.WHITE)){
                    white++;
                }
                else if(cell.equals(CellState.BLACK)){
                    black++;
                }
            }
            if(black > numCells/2 + 1 || white > numCells/2 + 1){
                break;
            }
        }

        if(black == white)
            return CellState.NONE;
        return black > white?CellState.BLACK:CellState.WHITE;
    }

    public ArrayList<Cell> getValidMoves(CellState color){
        ArrayList<Cell> validCells = new ArrayList<>();
        for(int row = 0; row < BOARD_SIZE; row++){
            for(int col = 0; col < BOARD_SIZE; col++){
                if(isValid(new Cell(row, col), color, false)){
                    validCells.add(new Cell(row, col));
                }
            }
        }
        return validCells;
    }

    public boolean isGameOver(){
        if(!validMoveExists(CellState.WHITE) && !validMoveExists(CellState.BLACK)){
            return true;
        }
        for(int row = 0; row < grid.length; row++){
            if(Arrays.asList(grid[row]).contains(CellState.NONE)){
                return false;
            }
        }
        return true;
    }

    public boolean makeMove(Cell move, CellState state){
        if(isValid(move, state, true)) {
            grid[move.row][move.column] = state;
            return true;
        }
        return false;
    }

    @Override
    public void cpuMove(CellState color) {}

    public int getBoardSize(){return BOARD_SIZE;}

    public CellState[][] toArray(){
        return Arrays.copyOf(grid, BOARD_SIZE);
    }

    @Override
    public String toString(){
        StringBuilder board = new StringBuilder();
        board.append("  _");
        for(int col = 0; col < grid[0].length; col++){
            board.append((char)('A' + col));
            board.append("_|_");
        }
        board.append('\n');
        for (int row = 0; row < grid.length; row++) {
            board.append(row + 1);
            board.append("|_");
            for (int col = 0; col < grid[row].length; col++) {
                board.append(grid[row][col]);
                board.append("_|_");
            }
            board.append('\n');
        }
        return board.toString();
    }

    public static class Cell{
        int row, column, flips;

        public Cell(){
            flips = 0;
        }

        public Cell(int r, int c){
            row = r;
            column = c;
            flips = 0;
        }
    }
}
