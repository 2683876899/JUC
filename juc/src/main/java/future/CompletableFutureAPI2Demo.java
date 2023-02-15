package future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 2.对计算结果进行处理
 * thenApply（try/catch）
 * 计算结果存在依赖关系，这两个线程串行化
 * 异常处理：由于存在依赖关系有异常直接结束
 * handle（try/catch/finally）
 * 计算结果存在依赖关系，这两个线程串行化
 * 异常处理：有异常可以带着异常往下继续处理
 *
 */
public class CompletableFutureAPI2Demo {
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("111");
            return 1;
        },threadPool).handle( (v,e) -> {
            System.out.println("222");
            return v + 1;
        }).handle( (v,e) -> {
            System.out.println("333");
            return v + 1;
        }).whenComplete((v,e) -> {
            if (e == null){
                System.out.println("计算的结果是：" + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });

        System.out.println(Thread.currentThread().getName() + "主线程继续执行！");
    }

    private static void thenApply() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("111");
            return 1;
        },threadPool).thenApply( v -> {
            System.out.println("222");
            return v + 1;
        }).thenApply( v -> {
            System.out.println("333");
            return v + 1;
        }).whenComplete((v,e) -> {
            if (e == null){
                System.out.println("计算的结果是：" + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });

        System.out.println(Thread.currentThread().getName() + "主线程继续执行！");
    }
}
