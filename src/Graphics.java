import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class Graphics
        extends JPanel
        implements ActionListener {
    public Timer t = new Timer(70, this);
    public String state;

    private final Snake s;
    private final Fruit f;
    private final Game game;

    public Graphics(Game g) {
        t.start();
        state = "HOME";

        game = g;
        s = g.getPlayer();
        f = g.getFood();


        this.setSize(Game.WIDTH_UNITS * Game.DIMENSION, Game.HEIGHT_UNITS * Game.DIMENSION);
        this.addKeyListener(g);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Game.WIDTH_UNITS * Game.DIMENSION, Game.HEIGHT_UNITS * Game.DIMENSION);
    }


    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, Game.WIDTH_UNITS * Game.DIMENSION + 5, Game.HEIGHT_UNITS * Game.DIMENSION + 5);

        switch (state) {
            case "HOME":
                paintHomeGraphics(g2d);
                break;

            case "CTRLS":
                paintControlsGraphics(g2d);
                break;

            case "RUNNING":
                paintRunningGraphics(g2d);
                break;

            case "PAUSE":
                paintPauseGraphics(g2d);
                break;

            case "END":
                paintEndGraphics(g2d);
                break;

            case "CONGRATS":
                paintEndGraphics(g2d);
                paintCongratsGraphics(g2d);
                break;
        }
    }

    private void paintEndGraphics(Graphics2D g2d){
        g2d.setColor(Color.GREEN);
        g2d.drawString("Your Score: " + game.eatenFruit, Game.WIDTH_UNITS / 2 * Game.DIMENSION - 40, Game.HEIGHT_UNITS / 2 * Game.DIMENSION - 40);
        g2d.setColor(Color.white);
        g2d.drawString("Press Enter to restart or X to exit. Press H to go back to the Home page", 60, Game.HEIGHT_UNITS / 2 * Game.DIMENSION);
    }

    private void paintPauseGraphics(Graphics2D g2d){
        t.stop();
        g2d.setColor(Color.red);
        g2d.fillRect(f.getX() * Game.DIMENSION, f.getY() * Game.DIMENSION, Game.DIMENSION, Game.DIMENSION);

        g2d.setColor(Color.green);
        for (Rectangle r : s.getBody()) {
            g2d.fill(r);
        }
        Font font = new Font("Courier New", 1, 40);
        g2d.setColor(Color.white);
        g2d.setFont(font);
        g2d.drawString("Game paused", Game.WIDTH_UNITS / 2 * Game.DIMENSION - 155, Game.HEIGHT_UNITS / 2 * Game.DIMENSION - 20);
    }

    private void paintControlsGraphics(Graphics2D g2d){
        g2d.setColor(Color.white);
        g2d.drawString("Keyboard arrows : move the snake", Game.WIDTH_UNITS / 2 * Game.DIMENSION - 100, Game.HEIGHT_UNITS / 2 * Game.DIMENSION - 50);
        g2d.drawString("P : pause the game", Game.WIDTH_UNITS / 2 * Game.DIMENSION - 100, Game.HEIGHT_UNITS / 2 * Game.DIMENSION - 30);
        g2d.drawString("R : resume the game", Game.WIDTH_UNITS / 2 * Game.DIMENSION - 100, Game.HEIGHT_UNITS / 2 * Game.DIMENSION - 10);
        g2d.setColor(Color.GREEN);
        g2d.drawString("Press H to go back to the Home page", Game.WIDTH_UNITS / 2 * Game.DIMENSION - 100, Game.HEIGHT_UNITS / 2 * Game.DIMENSION + 100);
    }

    private void paintRunningGraphics(Graphics2D g2d){
        g2d.setColor(Color.red);
        g2d.fillRect(f.getX() * Game.DIMENSION, f.getY() * Game.DIMENSION, Game.DIMENSION, Game.DIMENSION);

        g2d.setColor(Color.green);
        for (Rectangle r : s.getBody()) {
            g2d.fill(r);
        }
    }

    private void paintHomeGraphics(Graphics2D g2d){
        g2d.setColor(Color.white);
        g2d.drawString("Press N to start a new Game", Game.WIDTH_UNITS / 2 * Game.DIMENSION - 100, Game.HEIGHT_UNITS / 2 * Game.DIMENSION - 50);
        g2d.drawString("Press X to exit", Game.WIDTH_UNITS / 2 * Game.DIMENSION - 100, Game.HEIGHT_UNITS / 2 * Game.DIMENSION - 30);
        g2d.drawString("Press C to see controls", Game.WIDTH_UNITS / 2 * Game.DIMENSION - 100, Game.HEIGHT_UNITS / 2 * Game.DIMENSION - 10);

        Font font = new Font("Courier New", 1, 20);
        g2d.setColor(Color.GREEN);
        g2d.setFont(font);
        g2d.drawString("Can you beat the " + game.highScore + " points high score?", 80, 150);
    }

    private void paintCongratsGraphics(Graphics2D g2d){
        Font font = new Font("Courier New", 1, 20);
        g2d.setColor(Color.GREEN);
        g2d.setFont(font);
        g2d.drawString("Congrats, you've BEATEN the high score!", 70, 150);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
        try {
            game.update();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }



}


