package future;

import java.util.concurrent.*;

public class CompletableFutureUseDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        try {
            CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName());
                int result = ThreadLocalRandom.current().nextInt(10);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(" \t ------一秒后出结果：" + result);
                if (result > 2){
                    result = 10 /0;
                }
                return result;
            },threadPool).whenComplete((v,e) -> {
                if (e == null){
                    System.out.println("更新完成的指为：" + v);
                }
            }).exceptionally(e -> {
                e.printStackTrace();
                System.out.println("异常情况：" + e.getCause() + "\t" + e.getMessage());
                return null;
            });
            System.out.println(Thread.currentThread().getName() + "主线程继续执行其他任务");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }


        /*// 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }*/
    }

    private static void future1() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            int result = ThreadLocalRandom.current().nextInt(10);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(" \t ------一秒后出结果：" + result);
            return result;
        });
        System.out.println(Thread.currentThread().getName() + "主线程继续执行");

        System.out.println(integerCompletableFuture.get());
    }
}
