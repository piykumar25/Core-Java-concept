import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,
                4, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<>(2),
                new CustomThreadPoolExecutor(), new CustomRejectHandler());
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executor.submit(() -> {
                try {
                    Thread.sleep(5000);
                    Thread.currentThread().setName("Thread-" + finalI);
                } catch (InterruptedException e) {
                    // Handle the Exception
                }
                System.out.println("Task processed: " + Thread.currentThread().getName());
            });
        }


    }
}

class CustomThreadPoolExecutor implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread th = new Thread(r);
        th.setDaemon(false);
        th.setName("CustomThreadPoolExecutor" + r.toString());
        th.setPriority(Thread.NORM_PRIORITY);
        return th;
    }
}

class CustomRejectHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("Task rejected: " + r.toString());
    }
}