package stc21;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class LifeApp {

    public final static int MAX_RANGE = 15;
    public HashMap<Point, MySimpleLifeCell> cells = new HashMap<Point, MySimpleLifeCell>();

    public static void startOneThreadGame(String[] args) throws FileNotFoundException {

        int iterations = Integer.parseInt(args[1]);
        File configFile = new File(args[0]);
        Set<Point> startConditions = readStartConditions(configFile);
        LifeApp game = new LifeApp();
        game.cells = game.inicializeCells(startConditions);
        game.printMyLife(game.cells);
        System.out.println();
        for (int i = 0; i < iterations; i++) {

            runMyLife(game);

            System.out.println();
            System.out.println();

        }

        writeToFile(game, args[2]);

    }

    private static void writeToFile(LifeApp game, String arg) throws FileNotFoundException {
        try (PrintStream out = new PrintStream(new FileOutputStream(arg))) {
            for (MySimpleLifeCell cell : game.cells.values()) {
                if (cell.isALife())
                    out.println(cell.getPoint().x + " " + cell.getPoint().y);
            }
        }

    }

    static Set<Point> readStartConditions(File configFile) {
        Set<Point> result =new  HashSet();
        try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
            String point = null;
            while ((point = br.readLine()) != null) {
                int x = Integer.parseInt(point.split(" ")[0]);
                int y = Integer.parseInt(point.split(" ")[1]);
                result.add(new Point(x, y));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void runMyLife(LifeApp game) {

        for (MySimpleLifeCell cell : game.cells.values()) {
            game.setNearsPoints(cell);
        }

        HashMap<Point, MySimpleLifeCell> cellsCopy = new HashMap<Point, MySimpleLifeCell>();
        for (MySimpleLifeCell cell : game.cells.values()) {
            if (cell.live() != null) {
                cellsCopy.put(cell.getPoint(), cell.live());
            } else cellsCopy.put(cell.getPoint(), cell);
        }
        game.cells = cellsCopy;
        game.printMyLife(game.cells);
    }

    public HashMap<Point, MySimpleLifeCell> inicializeCells(Set<Point> start) {
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

        Point[] nears = getNears(cell);

        for (Point point : nears) {
            cell.setNearsCount(cells.get(point));
        }
    }

    private Point[] getNears(MySimpleLifeCell cell) {
        Point[] result = new Point[8];
        int x = cell.getPoint().x;
        int y = cell.getPoint().y;
        result[0] = new Point(x - 1 == 0 ? MAX_RANGE : x - 1, y);
        result[1] = new Point(x - 1 == 0 ? MAX_RANGE : x - 1, y - 1 == 0 ? MAX_RANGE : y - 1);
        result[2] = new Point(x - 1 == 0 ? MAX_RANGE : x - 1, y + 1 > MAX_RANGE ? 1 : y + 1);

        result[3] = new Point(x + 1 > MAX_RANGE ? 1 : x + 1, y);
        result[4] = new Point(x + 1 > MAX_RANGE ? 1 : x + 1, y - 1 == 0 ? MAX_RANGE : y - 1);
        result[5] = new Point(x + 1 > MAX_RANGE ? 1 : x + 1, y + 1 > MAX_RANGE ? 1 : y + 1);

        result[6] = new Point(x, y + 1 > MAX_RANGE ? 1 : y + 1);
        result[7] = new Point(x, y - 1 == 0 ? MAX_RANGE : y - 1);

        return result;
    }

    void printMyLife(HashMap<Point, MySimpleLifeCell> map) {
        for (int i = 1; i <= MAX_RANGE; i++) {
            for (int j = 1; j <= MAX_RANGE; j++) {
                MySimpleLifeCell cell = map.get(new Point(i, j));
                cell.setNearsCountToDefault();
                if (cell.isALife()) {
                    System.out.print("O ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();

        }
    }
}
