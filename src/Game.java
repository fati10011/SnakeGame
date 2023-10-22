import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Game
        implements KeyListener {

    private final Snake player;
    private final Fruit fruit;
    private final Graphics graphics;
    public JFrame window;

    public static final int WIDTH_UNITS = 30;
    public static final int HEIGHT_UNITS = 30;
    public static final int DIMENSION = 20;

    public int highScore;
    public int eatenFruit;

    public boolean collision;

    public Game() throws FileNotFoundException {
        window = new JFrame();

        player = new Snake();

        fruit = new Fruit(player);

        graphics = new Graphics(this);

        readHighScore();

        window.setContentPane(graphics);
        window.pack();

        window.setTitle("SnakeV1.Snake");
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public void update() throws IOException {
        if (graphics.state.equals("RUNNING")) {
            if (checkEatenFruit()) {
                player.grow();
                fruit.spawnPosition(player);
            } else {
                checkCollision();
            }
            if (collision) {
                if (eatenFruit > highScore) {
                    graphics.state = "CONGRATS";
                    highScore = eatenFruit;
                    writeHighScore();
                } else graphics.state = "END";
            } else {
                player.move();
            }
        }
    }

    public void run() {
        try {
            readHighScore();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        player.createBody();
        collision = false;
        eatenFruit = 0;
        player.move = "RIGHT";
        graphics.state = "RUNNING";
    }

    private void checkCollision() {
        if (player.getX() < 0 || player.getX() == WIDTH_UNITS * DIMENSION || player.getY() < 0 || player.getY() == HEIGHT_UNITS * DIMENSION) {
            collision = true;
        }
        for (int i = 2; i < player.getBody().size(); i++) {
            if (player.getX() == player.getBody().get(i).x && player.getY() == player.getBody().get(i).y) {
                collision = true;
            }
        }
    }

    private boolean checkEatenFruit() {
        if (player.getX() == fruit.getX() * DIMENSION && player.getY() == fruit.getY() * DIMENSION) {
            eatenFruit++;
            return true;
        } else return false;
    }

    public void readHighScore() throws FileNotFoundException {
        File highScoreTxt = new File("highScore.txt");

        if (highScoreTxt.exists()) {
            Scanner sc = new Scanner(highScoreTxt);
            if (sc.hasNext()) highScore = Integer.parseInt(sc.next());
            else highScore = 0;
        }
    }

    public void writeHighScore() throws IOException {
        FileWriter FW = new FileWriter("highScore.txt");
        FW.write(Integer.toString(eatenFruit));
        FW.close();
    }

    public Snake getPlayer() {
        return player;
    }

    public Fruit getFood() {
        return fruit;
    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (graphics.state.equals("RUNNING")) {
            if (keyCode == KeyEvent.VK_UP && !player.move.equals("DOWN")) {
                player.move = "UP";
            } else if (keyCode == KeyEvent.VK_DOWN && !player.move.equals("UP")) {
                player.move = "DOWN";
            } else if (keyCode == KeyEvent.VK_RIGHT && !player.move.equals("LEFT")) {
                player.move = "RIGHT";
            } else if (keyCode == KeyEvent.VK_LEFT && !player.move.equals("RIGHT")) {
                player.move = "LEFT";
            } else if (keyCode == KeyEvent.VK_P) {
                graphics.state = "PAUSE";
            } else if (keyCode == KeyEvent.VK_X) {
                window.dispose();
                System.exit(0);
            }
        } else if (graphics.state.equals("PAUSE")) {
            if (keyCode == keyEvent.VK_R) {
                graphics.t.start();
                graphics.state = "RUNNING";
            } else if (keyCode == KeyEvent.VK_H) {
                graphics.state = "HOME";
            }
        } else if (graphics.state.equals("CONGRATS")) {
            if (keyCode == keyEvent.VK_ENTER) {
                this.run();
                graphics.state = "RUNNING";
            } else if (keyCode == keyEvent.VK_X) {
                window.dispose();
                System.exit(0);
            } else if (keyCode == KeyEvent.VK_H) {
                graphics.state = "HOME";
            }
        } else if (graphics.state.equals("END")) {
            if (keyCode == keyEvent.VK_ENTER) {
                this.run();
                graphics.state = "RUNNING";
            } else if (keyCode == keyEvent.VK_X) {
                window.dispose();
                System.exit(0);
            } else if (keyCode == KeyEvent.VK_H) {
                graphics.state = "HOME";
            }
        } else if (graphics.state.equals("HOME")) {
            if (keyCode == KeyEvent.VK_N) {
                this.run();
            } else if (keyCode == KeyEvent.VK_C) {
                graphics.state = "CTRLS";
            } else if (keyCode == KeyEvent.VK_X) {
                window.dispose();
                System.exit(0);
            }
        } else if (graphics.state.equals("CTRLS")) {
            if (keyCode == KeyEvent.VK_H) {
                graphics.state = "HOME";
            } else if (keyCode == KeyEvent.VK_X) {
                window.dispose();
                System.exit(0);
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }
}

