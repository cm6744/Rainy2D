package rwt.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    static ExecutorService ec = Executors.newCachedThreadPool();

    public static void submit(ThreadTask r) {

        ec.submit(r::update);

    }

    public static void dispose() {

        ec.shutdown();

    }

}
