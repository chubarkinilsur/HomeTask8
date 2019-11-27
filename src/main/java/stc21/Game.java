package stc21;

import java.awt.Point;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Chubarkin Ilsur
 */
class Game {
    /**
     * MAX_RANGE ширина игрового поля
     */
    final static int MAX_RANGE = 15;
    private HashMap<Point, MySimpleLifeCell> cells = new HashMap<>();

    /**
     * @param config начальные условия в виде списка Pointов, координаты живых клеток
     * @param iterations количество итераций задается параметрами командной строки
     * @return возвращает результирующую мапу, ключ координата ячейки, значение характеристики ячейки
     */
    HashMap<Point, MySimpleLifeCell> startOneThreadGame(Set<Point> config, int iterations) {
        cells = initializeCells(config);
        printMyLife();
        System.out.println();
        for (int i = 0; i < iterations; i++) {
            runMyLife();
        }

        return cells;
    }

    /**
     * @param config начальные условия в виде списка Pointов, координаты живых клеток
     * @param iterations количество итераций задается параметрами командной строки
     * @return возвращает результирующую мапу, ключ координата ячейки, значение характеристики ячейки
     * @throws InterruptedException
     */
    HashMap<Point, MySimpleLifeCell> startGameWithThreads(Set<Point> config, int iterations) throws InterruptedException {

        cells = initializeCells(config);
        printMyLife();
        for (int i = 0; i < iterations; i++) {
            MySimpleLifeCell.copyCells = new HashMap<>();
            for (MySimpleLifeCell cell : cells.values()) {

                Thread thread = new Thread(cell);
                setNearsPoints(cell);
                thread.start();
                thread.join();
            }
           cells = MySimpleLifeCell.copyCells;

            printMyLife();
        }
        return cells;
    }

    private void runMyLife() {

        for (MySimpleLifeCell cell : cells.values()) {
            setNearsPoints(cell);
        }

        HashMap<Point, MySimpleLifeCell> cellsCopy = new HashMap<>();
        for (MySimpleLifeCell cell : cells.values()) {
            if (cell.live() != null) {
                cellsCopy.put(cell.getPoint(), cell.live());
            } else {
                cellsCopy.put(cell.getPoint(), cell);
            }
        }
        cells = cellsCopy;
        printMyLife();
    }

    private HashMap<Point, MySimpleLifeCell> initializeCells(Set<Point> start) {
        HashMap<Point, MySimpleLifeCell> result = new HashMap<>();
        for (int i = 1; i <= MAX_RANGE; i++) {
            for (int j = 1; j <= MAX_RANGE; j++) {
                Point point = new Point(i, j);
                result.put(point, new MySimpleLifeCell(start.contains(point), point));
            }
        }
        return result;
    }

    private void setNearsPoints(MySimpleLifeCell cell) {

        Point[] nears = cell.getNears();

        for (Point point : nears) {
            cell.setNearsCount(cells.get(point));
        }
    }

    private void printMyLife() {
        for (int i = 1; i <= MAX_RANGE; i++) {
            for (int j = 1; j <= MAX_RANGE; j++) {
                MySimpleLifeCell cell = cells.get(new Point(i, j));
                cell.setNearsCountToDefault();
                if (cell.isALife()) {
                    System.out.print("O ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }
}
