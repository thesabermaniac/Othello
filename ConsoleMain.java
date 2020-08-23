import java.util.Arrays;
import java.util.Scanner;

public class ConsoleMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OthelloModel othello = new OthelloModel();
        System.out.println(othello);
        while (!othello.isGameOver()) {
            CellState color = CellState.BLACK;
            for(int i = 0; i < 2; i++) {
                if(!othello.validMoveExists(color)){
                    System.out.println(color.name() + " does not have a valid move.");
                    color = color.opposite();
                    continue;
                }
                System.out.println(color.name() + ", enter a column and row to make your move (e.g. B4): ");
                String input = scanner.next();
                int asciiOfA = 'A';
                int asciiOf0 = '0';
                // gets ASCII value of row and column input and translate it to array index using modulus
                int row = (input.charAt(1) - 1) % asciiOf0;
                int col = Character.toUpperCase(input.charAt(0)) % asciiOfA;

                boolean move = othello.makeMove(new OthelloModel.Cell(row, col), color);
                while (!move) {
                    System.out.println("Invalid move. Please enter a new column and row (e.g. B4):");
                    input = scanner.next();
                    row = (input.charAt(1) - 1) % asciiOf0;
                    col = Character.toUpperCase(input.charAt(0)) % asciiOfA;
                    move = othello.makeMove(new OthelloModel.Cell(row, col), color);
                }
                System.out.println(othello);
                color = color.opposite();
            }
        }
        if(!(othello.getWinner() == null)) {
            System.out.println(othello.getWinner().name() + " wins!");
        }
        else {
            System.out.println("The game ended in a tie.");
        }
    }

}
