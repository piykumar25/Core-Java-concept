public class CounterWithSyncKeyword {
    private int counter = 0;

    public synchronized void increment() {
        System.out.println("Thread Name: " + Thread.currentThread().getName());
        counter++;
        System.out.println("Counter: " + counter);
    }

    public static void main(String[] args) throws InterruptedException {
        CounterWithSyncKeyword counter = new CounterWithSyncKeyword();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t1.setName("Thread-1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t2.setName("Thread-2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Counter: " + counter.counter);
    }
}
