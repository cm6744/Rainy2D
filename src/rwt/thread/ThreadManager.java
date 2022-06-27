package rwt.thread;

import rutil.container.Array;

public class ThreadManager {

    static Array<RThread> threads = new Array<>();

    //set up a new thread to run ThreadTask
    public static void join(ThreadTask r) {

        r.id = threads.size();
        threads.add(new RThread(r));

    }

    //return start() is invoked
    public static boolean isStarted(ThreadTask r) {

        return threads.get(r.id).isStarted;

    }

    //start the thread
    public static void start(ThreadTask r) {

        threads.get(r.id).start();

    }

    //stop the thread
    //if ThreadTask.run is a while-loop task
    //use while(ThreadManager.notDisposed) {...}
    public static void dispose(ThreadTask r) {

        threads.get(r.id).dispose();

    }

    public static boolean notDisposed(ThreadTask r) {

        return !threads.get(r.id).isInterrupted();

    }

    public static void sleep(ThreadTask r, int ms) {

        threads.get(r.id).sleep(ms);

    }

    private static class RThread extends Thread {

        ThreadTask task;
        boolean isStarted;

        public RThread(ThreadTask r) {

            task = r;

        }

        public void start() {

            isStarted = true;

            super.start();

        }

        public void dispose() {

            interrupt();

        }

        public void run() {

            task.update();

        }

        public void sleep(int ms) {

            try {
                Thread.sleep(ms);
            } catch(InterruptedException ignored) {}

        }

    }

}
