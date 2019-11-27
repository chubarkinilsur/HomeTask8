package stc21;

import java.awt.Point;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class App {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {

        Set<Point> config = readStartConditions(new File(args[0]));
        int iterations = Integer.parseInt(args[1]);
        long start = System.currentTimeMillis();
        HashMap<Point, MySimpleLifeCell> cells = new Game().startGameWithThreads(config, iterations);
        System.out.println("Many thread game time: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        new Game().startOneThreadGame(config, iterations);
        System.out.println("One thread game time: " + (System.currentTimeMillis() - start));
        writeToFile(args[2], cells);
    }

    private static void writeToFile(String file, HashMap<Point, MySimpleLifeCell> cells) throws FileNotFoundException {
        try (PrintStream out = new PrintStream(new FileOutputStream(file))) {
            for (MySimpleLifeCell cell : cells.values()) {
                if (cell.isALife())
                    out.println(cell.getPoint().x + " " + cell.getPoint().y);
            }
        }
    }

    private static HashSet<Point> readStartConditions(File configFile) {
        HashSet<Point> result = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
            String point;
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
}
