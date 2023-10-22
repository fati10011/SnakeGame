import java.awt.*;
import java.util.Random;

public class Fruit {

    private int x;
    private int y;

    public Fruit(Snake player){
        this.spawnPosition(player);
    }

    public void spawnPosition(Snake player) {
        Random random = new Random();
        x = random.nextInt(Game.WIDTH_UNITS - 1);
        y = random.nextInt(Game.HEIGHT_UNITS - 1);
        while(checkOnSnake(player)) {
            x = random.nextInt(Game.WIDTH_UNITS - 1);
            y = random.nextInt(Game.HEIGHT_UNITS - 1);
        }
    }

    public boolean checkOnSnake(Snake player){
        boolean onSnake = false;
        for(Rectangle r : player.getBody()){
            if(r.x == x && r.y == y) {
                onSnake = true;
                break;
            }
        }
        return onSnake;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
