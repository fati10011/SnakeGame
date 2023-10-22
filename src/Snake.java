import java.awt.*;
import java.util.ArrayList;

public class Snake {
    private ArrayList<Rectangle> body;
    private final int w = Game.WIDTH_UNITS;
    private final int h = Game.HEIGHT_UNITS;
    private final int d = Game.DIMENSION;

    public String move;

    public Snake() {
        createBody();
    }


    public void createBody (){
        body = new ArrayList<>();

        Rectangle temp = new Rectangle(d, d);
        temp.setLocation(w/2 * d, h/2 *d);
        body.add(temp);

        temp = new Rectangle(d, d);
        temp.setLocation((w/2 - 1) * d, (h/2) * d);
        body.add(temp);

        temp = new Rectangle(d, d);
        temp.setLocation((w/2 - 2) * d, (h/2) * d);
        body.add(temp);

    }

    public void move() {
            Rectangle head = body.get(0);

            Rectangle temp = new Rectangle(d, d);

        switch (move) {
            case "UP":
                temp.setLocation(head.x, head.y - d);
                break;
            case "DOWN":
                temp.setLocation(head.x, head.y + d);
                break;
            case "LEFT":
                temp.setLocation(head.x - d, head.y);
                break;
            default:
                temp.setLocation(head.x + d, head.y);
                break;
        }

            body.add(0, temp);
            body.remove(body.size()-1);
    }

    public void grow() {
        Rectangle first = body.get(0);

        Rectangle temp = new Rectangle(d, d);

        switch (move) {
            case "UP":
                temp.setLocation(first.x, first.y - d);
                break;
            case "DOWN":
                temp.setLocation(first.x, first.y + d);
                break;
            case "LEFT":
                temp.setLocation(first.x - d, first.y);
                break;
            default:
                temp.setLocation(first.x + d, first.y);
                break;
        }

        body.add(0, temp);
    }

    public ArrayList<Rectangle> getBody() {
        return body;
    }

    public int getX(){
        return body.get(0).x;
    }
    public int getY(){
        return body.get(0).y;
    }

}
