import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OthelloGUI extends JFrame {
    private JButtonWithPoint[][] buttonGrid;
    private CellState color;
    private int showValidMoves = JOptionPane.showConfirmDialog(null, "Would you like to highlight the valid moves?", "Show valid moves", JOptionPane.YES_NO_OPTION);

    public OthelloGUI(Othello model){
        CellState[][] othelloGrid = model.toArray();
        buttonGrid = new JButtonWithPoint[othelloGrid.length][othelloGrid.length];
        color = CellState.BLACK;
        OthelloGUI instance = this;
        setTitle("Othello");
        super.setSize(model.getBoardSize()*100, model.getBoardSize()*100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new FlowLayout());
        JLabel statusBar = new JLabel();
        statusBar.setText(CellState.BLACK.name());
        statusPanel.add(statusBar);
        JPanel game = new JPanel();
        game.setLayout(new GridLayout(model.getBoardSize(), model.getBoardSize()));

        add(game);
        add(statusPanel, BorderLayout.SOUTH);

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JButtonWithPoint b = (JButtonWithPoint) actionEvent.getSource();
                boolean move = model.makeMove(new OthelloModel.Cell(b.x, b.y), color);
                if(!move){
                    JOptionPane.showMessageDialog(instance, "Invalid move. Please try again.");
                }
                else{
                    color = color.opposite();
                    statusBar.setText(color.name());
                }
                for(int row = 0; row < buttonGrid.length; row++){
                    for (int col = 0; col < buttonGrid[row].length; col++){
                        buttonGrid[row][col].setText(model.toArray()[row][col].toGUI());
                        setBackgroundColor(row, col, color, model);
                    }
                }
                if(model.isGameOver()){
                    JOptionPane.showMessageDialog(instance, "Game over. " + model.getWinner().name() + " wins!");
                    setVisible(false);
                    dispose();
                }
                else if(!model.validMoveExists(color)){
                    JOptionPane.showMessageDialog(instance, color.name() + " has no valid move. " + color.opposite().name() + " it's your turn.");
                    color = color.opposite();
                    statusBar.setText(color.name());
                }
            }
        };

        for(int row = 0; row < othelloGrid.length; row++){
            for(int col = 0; col < othelloGrid[row].length; col++) {
                JButtonWithPoint button = new JButtonWithPoint();
                button.setText(othelloGrid[row][col].toGUI());
                button.x = row;
                button.y = col;
                game.add(button);
                buttonGrid[row][col] = button;
                setBackgroundColor(row, col, CellState.BLACK, model);
                button.addActionListener(al);
            }
        }
        this.setVisible(true);

    }

    private void setBackgroundColor(int row, int col, CellState color, Othello model){
        if(showValidMoves == JOptionPane.YES_OPTION && model.isValid(new OthelloModel.Cell(row, col), color, false)){
            //Highlights buttons that are valid moves
            buttonGrid[row][col].setBackground(new Color(0, 130, 103));
        }
        else {
            buttonGrid[row][col].setBackground(new Color(0, 144, 103));
        }

    }


    private static class JButtonWithPoint extends JButton{
        int x, y;
    }
}

