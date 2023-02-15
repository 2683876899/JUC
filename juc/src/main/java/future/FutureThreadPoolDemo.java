package future;

import java.util.concurrent.*;

/**
 * 1.t1执行三个任务的总耗时
 * 2.使用线程池调用异步任务同步执行任务
 */
public class FutureThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        long startTime = System.currentTimeMillis();
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "task1");
        threadPool.submit(futureTask);
        FutureTask<String> futureTask1 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "task2");
        threadPool.submit(futureTask1);

        System.out.println(futureTask.get());
        System.out.println(futureTask1.get());
        FutureTask<String> futureTask2 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "task3");
        threadPool.submit(futureTask2);

        long endTime = System.currentTimeMillis();
        System.out.println("程序运行完成耗时：" + (endTime - startTime));
        threadPool.shutdown();
    }

    private static void t1() {
        long startTime = System.currentTimeMillis();
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        long endTime = System.currentTimeMillis();
        System.out.println("程序运行完成耗时：" + (endTime - startTime));
    }
}
