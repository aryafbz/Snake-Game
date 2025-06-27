import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener , KeyListener {

    private class Unit {
        int x;
        int y;

        Unit (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int width;
    int height;
    static int unitSize = 25;

    Unit snakeHead;
    ArrayList<Unit> snakeBody;

    Unit food;
    Random rand;

    Timer timer;

    int vx;
    int vy;

    boolean gameOver = false;


    SnakeGame(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Unit(5 , 5);
        snakeBody = new ArrayList<>();

        food = new Unit(10 , 10);
        rand = new Random();
        placeFood();

        vx = 0;
        vy = 0;

        timer = new Timer(120 , this);
        timer.start();


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        //grid
        for(int i = 0 ; i < width/unitSize ; i ++) {
            g.drawLine(i * unitSize , 0 , i * unitSize , height);
            g.drawLine(0 , i * unitSize , width , i * unitSize);
        }

        //food
        g.setColor(Color.RED);
        g.fillRect(food.x * unitSize , food.y * unitSize , unitSize , unitSize);

        //snake head
        g.setColor(Color.GREEN);
        g.fillRect(snakeHead.x * unitSize, snakeHead.y * unitSize, unitSize, unitSize);

        //snake body
        for(int i = 0 ; i < snakeBody.size() ; i ++) {
            Unit snakePart = snakeBody.get(i);
            g.fillRect(snakePart.x * unitSize , snakePart.y * unitSize , unitSize, unitSize);
        }

        //score
        g.setFont(new Font("Times New Roman", Font.BOLD, 16));
        if(gameOver) {

            g.setColor(Color.RED);
            g.drawString("Game Over", unitSize - 16, unitSize);

        }
        else{
            g.drawString("Score: " + snakeBody.size(), unitSize - 16, unitSize);
        }
    }

    public void placeFood() {
        food.x = rand.nextInt(width/unitSize);
        food.y = rand.nextInt(height/unitSize);
    }

    public void move() {
        //eat food
        if(collision(snakeHead , food)) {
            snakeBody.add(new Unit(food.x , food.y));
            placeFood();
        }

        // snake body
        for(int i = snakeBody.size() - 1 ; i >= 0 ; i --) {
            Unit snakePart = snakeBody.get(i);
            if(i == 0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else{
                Unit prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //snake head
        snakeHead.x += vx;
        snakeHead.y += vy;

        //game over
        for(int i = 0; i < snakeBody.size() ; i ++) {
            Unit snakePart = snakeBody.get(i);

            if(collision(snakeHead , snakePart)) {
                gameOver = true;
            }
        }
    }

    public boolean collision(Unit unit1 , Unit unit2) {
        return unit1.x == unit2.x && unit1.y == unit2.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver)
            timer.stop();

        if(snakeHead.x * unitSize < 0 || snakeHead.x * unitSize > width || snakeHead.y * unitSize < 0 || snakeHead.y * unitSize > height) {
            gameOver = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && vy != 1) {
            vx = 0;
            vy = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && vy != -1) {
            vx = 0;
            vy = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && vx != 1) {
            vx = -1;
            vy = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && vx != -1) {
            vx = 1;
            vy = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


}
