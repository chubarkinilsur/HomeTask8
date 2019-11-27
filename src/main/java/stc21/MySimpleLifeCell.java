package stc21;

import java.awt.*;
import java.util.HashMap;

/**
 * @author Чубаркин Ильсур
 * Класс реализующий ячейку игры
 */
public class MySimpleLifeCell implements Runnable {
    private boolean isALife;
    private int countOfNears;
    private final Point point;
    private final Point[] neighborhoods;

    static HashMap<Point, MySimpleLifeCell> copyCells = new HashMap<>();

    MySimpleLifeCell(boolean isALife, Point point) {
        this.isALife = isALife;
        this.point = point;
        this.neighborhoods = getNears();
    }

    Point getPoint() {
        return point;
    }

    boolean isALife() {
        return this.isALife;
    }

    void setNearsCountToDefault() {

        this.countOfNears = 0;
    }

    void setNearsCount(MySimpleLifeCell cell) {
        if (cell.isALife) {
            this.countOfNears++;
        }
    }


    MySimpleLifeCell live() {
        MySimpleLifeCell cell;
        if (!this.isALife && this.countOfNears == 3) {
            cell = new MySimpleLifeCell(true, this.point);
            copyCells.put(this.point, cell);
            return cell;
        }
        if (this.isALife && (this.countOfNears > 3 || this.countOfNears < 2)) {
            cell = new MySimpleLifeCell(false, this.point);
            copyCells.put(this.point, cell);
            return cell;
        }
        copyCells.put(this.point, this);
        return null;
    }

      Point[] getNears() {

        Point[] result = new Point[8];
        int x = this.getPoint().x;
        int y = this.getPoint().y;
        result[0] = new Point(x - 1 == 0 ? Game.MAX_RANGE : x - 1, y);
        result[1] = new Point(x - 1 == 0 ? Game.MAX_RANGE : x - 1, y - 1 == 0 ? Game.MAX_RANGE : y - 1);
        result[2] = new Point(x - 1 == 0 ? Game.MAX_RANGE : x - 1, y + 1 > Game.MAX_RANGE ? 1 : y + 1);

        result[3] = new Point(x + 1 > Game.MAX_RANGE ? 1 : x + 1, y);
        result[4] = new Point(x + 1 > Game.MAX_RANGE ? 1 : x + 1, y - 1 == 0 ? Game.MAX_RANGE : y - 1);
        result[5] = new Point(x + 1 > Game.MAX_RANGE ? 1 : x + 1, y + 1 > Game.MAX_RANGE ? 1 : y + 1);

        result[6] = new Point(x, y + 1 > Game.MAX_RANGE ? 1 : y + 1);
        result[7] = new Point(x, y - 1 == 0 ? Game.MAX_RANGE : y - 1);
        return result;
    }

    @Override
    public void run() {

        live();
    }
}
