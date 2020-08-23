import java.util.Scanner;

public class OthelloOnePlayerConsole {
    private CellState playerColor, cpuColor;
    private Scanner scanner = new Scanner(System.in);
    private Othello model;

    public OthelloOnePlayerConsole(Othello gameInterface){
        playerColor = getPlayerColor();
        cpuColor = playerColor.opposite();
        model = gameInterface;
    }

    private CellState getPlayerColor(){
        System.out.println("What color would you like to play as (white or black)?");
        String input = scanner.next().toUpperCase();
        while (!input.equals("BLACK") && !input.equals("WHITE")) {
            System.out.println(input + " is not a valid color, please enter 'white' or 'black'");
            input = scanner.next().toUpperCase();
        }
        return CellState.valueOf(input);
    }

    public void playGame(){
        CellState turn = CellState.BLACK;
        System.out.println(model);
        System.out.println("BLACK goes first.");
        while (!model.isGameOver()){
            if(turn.equals(playerColor)){
                System.out.println(turn.name() + ", enter a column and row to make your move (e.g. B4): ");
                String input = scanner.next();
                int asciiOfA = 'A';
                int asciiOf0 = '0';
                // gets ASCII value of row and column input and translate it to array index using modulus
                int row = (input.charAt(1) - 1) % asciiOf0;
                int col = Character.toUpperCase(input.charAt(0)) % asciiOfA;

                boolean move = model.makeMove(new OthelloModel.Cell(row, col), playerColor);
                while (!move) {
                    System.out.println("Invalid move. Please enter a new column and row (e.g. B4):");
                    input = scanner.next();
                    row = (input.charAt(1) - 1) % asciiOf0;
                    col = Character.toUpperCase(input.charAt(0)) % asciiOfA;
                    move = model.makeMove(new OthelloModel.Cell(row, col), turn);
                }
                turn = turn.opposite();
                System.out.println(model);
            }
            else if(turn.equals(cpuColor)){
                model.cpuMove(cpuColor);
                System.out.println("CPU's move:");
                System.out.println(model);
                turn = turn.opposite();
            }
        }
    }

    public static void main(String[] args) {
        OthelloModelOnePlayer newGame = new OthelloModelOnePlayer();
        OthelloOnePlayerConsole console = new OthelloOnePlayerConsole(newGame);
        console.playGame();
    }
}
