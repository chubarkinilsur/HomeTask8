package stc21;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

class LifeAppThread {
    void startGameWithThreads(String[] args) throws InterruptedException {
        int numIterations = Integer.parseInt(args[1]);
        File configFile = new File(args[0]);
        Set<Point> startConditions = readStartConditions(configFile);
        LifeApp game = new LifeApp();
        HashMap<Point, MySimpleLifeCell> cells = game.inicializeCells(startConditions);
        MySimpleLifeCell.cells = cells;
        for (int i = 0; i < numIterations; i++) {
            MySimpleLifeCell.copyCells = new HashMap<>();
            for (MySimpleLifeCell cell : MySimpleLifeCell.cells.values()) {
                Thread thread = new Thread(cell);
                thread.start();
                thread.join();
            }
            MySimpleLifeCell.cells = MySimpleLifeCell.copyCells;
            game.printMyLife(MySimpleLifeCell.cells);
        }
    }

    private Set<Point> readStartConditions(File configFile) {
        Set<Point> resultSet = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
            String point = null;
            while ((point = br.readLine()) != null) {
                int x = Integer.parseInt(point.split(" ")[0]);
                int y = Integer.parseInt(point.split(" ")[1]);
                resultSet.add(new Point(x, y));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

}

