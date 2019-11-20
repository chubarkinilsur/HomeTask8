package stc21;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

public class App {
    public static void main(String[] args) throws ExecutionException, InterruptedException, FileNotFoundException {
        LifeAppThread app = new LifeAppThread();
        long start = System.currentTimeMillis();
        app.startGameWithThreads(args);
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        LifeApp.startOneThreadGame(args);
        System.out.println(System.currentTimeMillis() - start);
    }
}
