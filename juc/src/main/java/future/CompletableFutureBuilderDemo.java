package future;

import java.util.concurrent.*;

public class CompletableFutureBuilderDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        /*CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        },threadPool);
        System.out.println(voidCompletableFuture.get());*/

        CompletableFuture<String> uCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return "hello supplyAsync";
        },threadPool);
        System.out.println(uCompletableFuture.get());
        threadPool.shutdown();
    }
}
