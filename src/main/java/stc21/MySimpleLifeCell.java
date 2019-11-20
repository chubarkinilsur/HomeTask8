package stc21;

import java.awt.*;
import java.util.HashMap;

public class MySimpleLifeCell implements Runnable {
    private boolean isALife;
    private int countOfNears;
    private final Point point;
    private final Point[] neiborhoods;
    public static HashMap<Point, MySimpleLifeCell> cells;
    public static HashMap<Point, MySimpleLifeCell> copyCells = new HashMap<Point, MySimpleLifeCell>();

    public MySimpleLifeCell(boolean isALife, Point point) {
        this.isALife = isALife;
        this.point = point;
        this.neiborhoods = getNears();
    }

    public Point getPoint() {
        return point;
    }

    public boolean isALife() {
        return this.isALife;
    }

    public void setNearsCountToDefault() {

        this.countOfNears = 0;
    }

    public void setNearsCount(MySimpleLifeCell cell) {
        if (cell.isALife) {
            this.countOfNears++;
        }
    }


    public MySimpleLifeCell live() {
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

    private Point[] getNears() {
        Point[] result = new Point[8];
        int x = this.getPoint().x;
        int y = this.getPoint().y;
        result[0] = new Point(x - 1 == 0 ? LifeApp.MAX_RANGE : x - 1, y);
        result[1] = new Point(x - 1 == 0 ? LifeApp.MAX_RANGE : x - 1, y - 1 == 0 ? LifeApp.MAX_RANGE : y - 1);
        result[2] = new Point(x - 1 == 0 ? LifeApp.MAX_RANGE : x - 1, y + 1 > LifeApp.MAX_RANGE ? 1 : y + 1);

        result[3] = new Point(x + 1 > LifeApp.MAX_RANGE ? 1 : x + 1, y);
        result[4] = new Point(x + 1 > LifeApp.MAX_RANGE ? 1 : x + 1, y - 1 == 0 ? LifeApp.MAX_RANGE : y - 1);
        result[5] = new Point(x + 1 > LifeApp.MAX_RANGE ? 1 : x + 1, y + 1 > LifeApp.MAX_RANGE ? 1 : y + 1);

        result[6] = new Point(x, y + 1 > LifeApp.MAX_RANGE ? 1 : y + 1);
        result[7] = new Point(x, y - 1 == 0 ? LifeApp.MAX_RANGE : y - 1);
        return result;
    }

    @Override
    public void run() {
        for (Point point : neiborhoods) {
            this.setNearsCount(cells.get(point));
        }
        live();
    }
}
