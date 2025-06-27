import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        int width = 600;
        int height = 600;

        JFrame gameFrame = new JFrame("Snake Game");
        gameFrame.setSize(width, height);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setResizable(false);
        gameFrame.setVisible(true);

        SnakeGame snakeGame = new SnakeGame(width, height);
        gameFrame.add(snakeGame);
        gameFrame.pack();
        snakeGame.requestFocus();
    }
}