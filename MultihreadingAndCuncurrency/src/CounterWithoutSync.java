public class CounterWithoutSync {
    int counter = 0;

    public void increment() {
        System.out.println("Thread Name: " + Thread.currentThread().getName());
        counter++;
        System.out.println("Counter: " + counter);
    }

    public static void main(String[] args) throws InterruptedException {
        CounterWithoutSync counter = new CounterWithoutSync();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                counter.increment();
            }
        });
        t1.setName("Thread-1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                counter.increment();
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
