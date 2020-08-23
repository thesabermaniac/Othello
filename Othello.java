public interface Othello {

    public boolean validMoveExists(CellState state);
    public boolean indexInRange(OthelloModel.Cell move);
    public boolean isValid(OthelloModel.Cell move, CellState state, boolean changeState);
    public CellState getWinner();
    public boolean isGameOver();
    public boolean makeMove(OthelloModel.Cell move, CellState state);
    public void cpuMove(CellState color);
    public String toString();
    public CellState[][] toArray();
    public int getBoardSize();

}
